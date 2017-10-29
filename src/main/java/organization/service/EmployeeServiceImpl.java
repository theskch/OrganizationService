package organization.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;
import organization.exceptions.AccessDeniedException;
import organization.exceptions.ItemNotFoundException;
import organization.model.Employee;
import organization.model.EmployeeRepository;
import organization.model.Product;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

	private final EmployeeRepository employeeRepository;
	private final ProductService productService;
	private final OrganizationService organizationService;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository,
			ProductService productService,
			OrganizationService organizationService) {
		this.employeeRepository = employeeRepository;
		this.productService = productService;
		this.organizationService = organizationService;
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		return employeeRepository
				.findAll()
				.stream()
				.map(employee -> {
					return new EmployeeDTO(
							employee.getFirstName(), 
							employee.getLastName());
					})
				.collect(Collectors.toList());
	}

	@Override
	public List<EmployeeDTO> getEmployeesOfOrganization(String organizationId) {
		return employeeRepository.findByOrganization(organizationId)
				.stream()
				.map(employee -> {
					return new EmployeeDTO(
							employee.getFirstName(), 
							employee.getLastName());
					})
				.collect(Collectors.toList());
	}
	
	public List<ProductDTO> getProductsForEmployee(String employeeId){
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ItemNotFoundException(employeeId));
		List<ProductDTO> retVal = new ArrayList<ProductDTO>();
		
		if(!employee.getAllowedOperations().contains("read")) {
			return retVal;
		}
		
		productService.getProductsOfOrganization(employee.getOrganization()).forEach(product -> {
			Set<String> operations = employee.getAllowedOperations();
			operations.remove("create");		
			retVal.add(new ProductDTO(product.getName(), product.getQuantity(), product.getPrice(), operations));
		});
		
		organizationService.findAllExcept(employee.getOrganization()).forEach(organization -> {
			if(organization.isShareAllowed()) {
				
				productService.getProductsOfOrganization(organization.getId()).forEach(product -> {
					if(checkPolicy(product,organization.getReadPolicy())){
						Set<String> operations = new HashSet<String>();
						operations.add("read");
						
						if(employee.getAllowedOperations().contains("update")
								&& checkPolicy(product,organization.getUpdatePolicy())) {
								operations.add("update");
							
						}
						
						if(employee.getAllowedOperations().contains("delete")
								&& checkPolicy(product,organization.getDeletePolicy())) {
								operations.add("delete");
							
						}
						
						retVal.add(new ProductDTO(
								product.getName(), 
								product.getQuantity(), 
								product.getPrice(), 
								operations));
					}
				});
			}
		});
		
		return retVal;
	}
	
	public ProductDTO getProductForEmployee(String employeeId, String productId) {
		Employee employee = employeeRepository.findOne(employeeId);
		if(!employee.getAllowedOperations().contains("read")) {
			throw new AccessDeniedException(employeeId, " Employee not allowed to read products!");
		}
		
		Product product = productService.getProductById(productId);
		
		if(product.getOrganization() == employee.getOrganization()) {
			employee.getAllowedOperations().remove("create");
			return new ProductDTO(
					product.getName(), 
					product.getQuantity(), 
					product.getPrice(), 
					employee.getAllowedOperations());
		}
		else if(organizationService.isShareAllowedForOrganization(product.getOrganization())){
			
			if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), "read"))) {
				Set<String> operations = new HashSet<String>();
				operations.add("read");

				if(employee.getAllowedOperations().contains("update")) {
					if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), "update"))) {
						operations.add("update");
					}
				}
				
				if(employee.getAllowedOperations().contains("delete")) {
					if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), "delete"))) {
						operations.add("delete");
					}
				}
				
				return new ProductDTO(
						product.getName(), 
						product.getQuantity(), 
						product.getPrice(), 
						operations);
			}
		}
		else
			throw new AccessDeniedException(productId, " Product doesn't satisfy organization read policy!");
		
		return null; 
	}

	@Override
	public Boolean addProductByEmployee(String employeeId, Product product) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
			new ItemNotFoundException(employeeId));
		
		if(!employee.getAllowedOperations().contains("create")) {
			throw new AccessDeniedException(employeeId, " Employee doesn't have create privilages.");
		}
		
		product.setOrganization(employee.getOrganization());
		productService.addProduct(product);
		return true;
	}

	@Override
	public Boolean deleteProductByEmployee(String employeeId, String productId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
			new ItemNotFoundException(employeeId));
		
		if(!employee.getAllowedOperations().contains("delete")) {
			throw new AccessDeniedException(employeeId, " Employee doesn't have delete privilages.");
		}
		
		Product product = productService.getProductById(productId);
		if(product.getOrganization() == employee.getOrganization()) {
			productService.deleteProduct(productId);
			return true;
		}
		else if(organizationService.isShareAllowedForOrganization(product.getOrganization())){
			if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), "delete"))) {
				productService.deleteProduct(productId);
				return true;
			}
			else {
				throw new AccessDeniedException(employeeId, " Product doesn't satisfy organization delete policy!");
			}
		}
		else {
			throw new AccessDeniedException(employeeId, " Organization doesn't allow sharing of data!");
		}
	}

	@Override
	public Boolean updateProductByEmployee(String employeeId, String productId, Map<String, Object> values) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
			new ItemNotFoundException(employeeId));
	
		if(!employee.getAllowedOperations().contains("update")) {
			throw new AccessDeniedException(employeeId, " Employee doesn't have delete privilages.");
		}
	
		Product product = productService.getProductById(productId);
		if(product.getOrganization() == employee.getOrganization()) {
			return productService.updateProduct(productId, values);
		}
		else if(organizationService.isShareAllowedForOrganization(product.getOrganization())){
			if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), "update"))) {
				return productService.updateProduct(productId, values);
			}
			else {
				throw new AccessDeniedException(employeeId, " Product doesn't satisfy organization update policy!");
			}
		}
		else {
			throw new AccessDeniedException(employeeId, " Organization doesn't allow sharing of data!");
		}
	}
	
	private boolean checkPolicy(Product product, String policy) {
		if(Boolean.valueOf(policy)) {
			return true;
		}
		else {
			try {
				ExpressionParser parser = new SpelExpressionParser();
				Expression exp = parser.parseExpression(policy);
				return exp.getValue(product, Boolean.class);
			}
			catch(SpelParseException exp) {
				return false;
			}
			
		}
	}
}
