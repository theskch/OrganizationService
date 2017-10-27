package organization.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;
import organization.exceptions.ItemNotFoundException;
import organization.model.Employee;
import organization.model.EmployeeRepository;

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
			if(operations.contains("create")) {
				operations.remove("create");
			}
			
			retVal.add(new ProductDTO(product.getName(), product.getQuantity(), product.getPrice(), operations));
		});
		
		organizationService.findAllExcept(employee.getOrganization()).forEach(organization -> {
			if(organization.isShareAllowed()) {
				ExpressionParser parser = new SpelExpressionParser();
				Expression exp = parser.parseExpression(organization.getReadPolicy());
				productService.getProductsOfOrganization(organization.getId()).forEach(product -> {
					if(exp.getValue(product, Boolean.class)) {
						Set<String> operations = new HashSet<String>();
						operations.add("read");
						Expression additionalExp = parser.parseExpression(organization.getUpdatePolicy());
						
						if(additionalExp.getValue(product, Boolean.class)){
							operations.add("update");
						}
						
						additionalExp = parser.parseExpression(organization.getDeletePolicy());
						if(additionalExp.getValue(product, Boolean.class)){
							operations.add("delete");
						}
						
						retVal.add(new ProductDTO(product.getName(), product.getQuantity(), product.getPrice(), operations));
					}
				});
			}
		});
		
		return retVal;
		
	}
	
	
}
