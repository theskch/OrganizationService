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

import organization.Operation;
import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;
import organization.exceptions.AccessDeniedException;
import organization.exceptions.ItemNotFoundException;
import organization.model.Employee;
import organization.model.EmployeeRepository;
import organization.model.Product;

@Service("employeeService")
public class EmployeeService{

	private final EmployeeRepository employeeRepository;
	private final ProductService productService;
	private final OrganizationService organizationService;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository,
			ProductService productService,
			OrganizationService organizationService) {
		this.employeeRepository = employeeRepository;
		this.productService = productService;
		this.organizationService = organizationService;
	}

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
		
		if(!employee.getAllowedOperations().contains(Operation.READ)) {
			return retVal;
		}
		
		productService.getProductsOfOrganization(employee.getOrganization()).forEach(product -> {
			Set<Operation> operations = employee.getAllowedOperations();
			operations.remove(Operation.CREATE);		
			retVal.add(new ProductDTO(product.getName(), product.getQuantity(), product.getPrice(), operations));
		});
		
		organizationService.findAllExcept(employee.getOrganization()).forEach(organization -> {
			if(organization.isShareAllowed()) {
				
				productService.getProductsOfOrganization(organization.getId()).forEach(product -> {
					if(checkPolicy(product,organization.getReadPolicy())){
						Set<Operation> operations = new HashSet<Operation>();
						operations.add(Operation.READ);
						
						if(employee.getAllowedOperations().contains(Operation.UPDATE)
								&& checkPolicy(product,organization.getUpdatePolicy())) {
								operations.add(Operation.UPDATE);
							
						}
						
						if(employee.getAllowedOperations().contains(Operation.DELETE)
								&& checkPolicy(product,organization.getDeletePolicy())) {
								operations.add(Operation.DELETE);
							
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
		if(!employee.getAllowedOperations().contains(Operation.READ)) {
			throw new AccessDeniedException(employeeId, " Employee not allowed to read products!");
		}
		
		Product product = productService.getProductById(productId);
		
		if(product.getOrganization() == employee.getOrganization()) {
			employee.getAllowedOperations().remove(Operation.CREATE);
			return new ProductDTO(
					product.getName(), 
					product.getQuantity(), 
					product.getPrice(), 
					employee.getAllowedOperations());
		}
		else if(organizationService.isShareAllowedForOrganization(product.getOrganization())){
			
			if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), Operation.READ))) {
				Set<Operation> operations = new HashSet<Operation>();
				operations.add(Operation.READ);

				if(employee.getAllowedOperations().contains(Operation.UPDATE)) {
					if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), Operation.UPDATE))) {
						operations.add(Operation.UPDATE);
					}
				}
				
				if(employee.getAllowedOperations().contains(Operation.DELETE)) {
					if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), Operation.DELETE))) {
						operations.add(Operation.DELETE);
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

	public Boolean addProductByEmployee(String employeeId, Product product) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
			new ItemNotFoundException(employeeId));
		
		if(!employee.getAllowedOperations().contains(Operation.CREATE)) {
			throw new AccessDeniedException(employeeId, " Employee doesn't have create privilages.");
		}
		
		product.setOrganization(employee.getOrganization());
		productService.addProduct(product);
		return true;
	}

	public Boolean deleteProductByEmployee(String employeeId, String productId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
			new ItemNotFoundException(employeeId));
		
		if(!employee.getAllowedOperations().contains(Operation.DELETE)) {
			throw new AccessDeniedException(employeeId, " Employee doesn't have delete privilages.");
		}
		
		Product product = productService.getProductById(productId);
		if(product.getOrganization() == employee.getOrganization()) {
			productService.deleteProduct(productId);
			return true;
		}
		else if(organizationService.isShareAllowedForOrganization(product.getOrganization())){
			if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), Operation.DELETE))) {
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

	public Boolean updateProductByEmployee(String employeeId, String productId, Map<String, Object> values) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
			new ItemNotFoundException(employeeId));
	
		if(!employee.getAllowedOperations().contains(Operation.UPDATE)) {
			throw new AccessDeniedException(employeeId, " Employee doesn't have update privilages.");
		}
	
		Product product = productService.getProductById(productId);
		if(product.getOrganization() == employee.getOrganization()) {
			return productService.updateProduct(productId, values);
		}
		else if(organizationService.isShareAllowedForOrganization(product.getOrganization())){
			if(checkPolicy(product, organizationService.getAccessPolicyForOperation(product.getOrganization(), Operation.UPDATE))) {
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
