package organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import organization.exceptions.AccessDeniedException;
import organization.exceptions.ItemNotFoundException;
import organization.model.Employee;
import organization.model.EmployeeRepository;
import organization.model.Organization;
import organization.model.OrganizationRepository;
import organization.model.Product;
import organization.model.ProductRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
public class OrganizationRestCotroller {
	
	private final EmployeeRepository employeeRepository;	
	private final ProductRepository productRepository;
	private final OrganizationRepository organizationRepository;
	
	@Autowired
	OrganizationRestCotroller(
			EmployeeRepository employeeRepository,
			ProductRepository productRepository,
			OrganizationRepository organizationRepository){
		
		this.employeeRepository = employeeRepository;
		this.productRepository = productRepository;
		this.organizationRepository = organizationRepository;
	}
	
	void startPagePost() {
		//empty so that db is not visible on the start page
	}
	
	@RequestMapping(method = RequestMethod.GET)
	void startPageGet() {
		//empty so that db is not visible on the start page
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{organizationId}/employees")
	Collection<Employee> readOrganizationEmployees(@PathVariable String organizationId){
		return employeeRepository.findByOrganization(organizationId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}/products")
	Collection<Product> readEmployeeProducts(@PathVariable String employeeId){
		Collection<Product> retVal = new ArrayList<>(10);
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ItemNotFoundException(employeeId));
		
		productRepository.findAll().forEach(prod ->{			
			int accessLevel = CalculateAccessLevel(employee, prod);
			if(accessLevel > 0) {
				prod.setAccessLevel(accessLevel);
				retVal.add(prod);
			}
		});
		
		return retVal;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}/{productId}")
	Product readEmployeeProduct(@PathVariable String employeeId, @PathVariable String productId) {
		
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ItemNotFoundException(employeeId));
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ItemNotFoundException(productId));
		
		int accessLevel = CalculateAccessLevel(employee, product);
		if(accessLevel == 0) {
			throw new AccessDeniedException(employeeId, 1, accessLevel);
		}
		
		product.setAccessLevel(accessLevel);
		return product;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}")
	Employee readEmployee(@PathVariable String employeeId) {
		return employeeRepository.findById(employeeId).orElseThrow(
				() -> new ItemNotFoundException(employeeId));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{employeeId}/{productId}")
	ResponseEntity<?> updateProduct(@PathVariable String employeeId, 
			@PathVariable String productId,
			@RequestBody Product updatedProduct){
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ItemNotFoundException(employeeId));
		
		int requiredMinLevel = 2;
		Product product = null;
		Optional<Product> productOpt = productRepository.findById(productId);
		if(productOpt.isPresent()) {
			product = productOpt.get();
		}else {
			product = updatedProduct;
			requiredMinLevel = 3;
		}
	
		int accessLevel = CalculateAccessLevel(employee, product);
		if(accessLevel >= requiredMinLevel) {
			product.setName(updatedProduct.getName());
			product.setPrice(updatedProduct.getPrice());
			product.setQuantity(updatedProduct.getQuantity());
			productRepository.save(product);
			return ResponseEntity.noContent().build();
		}else {
			throw new AccessDeniedException(employeeId, requiredMinLevel, accessLevel);
		}	
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{employeeId}")
	ResponseEntity<?> addProduct(@PathVariable String employeeId,
			@RequestBody Product newProduct){
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ItemNotFoundException(employeeId));
		
		int accessLevel = CalculateAccessLevel(employee, newProduct);
		if(accessLevel >= 3) {
			productRepository.save(newProduct);
			return ResponseEntity.noContent().build();
		}else {
			throw new AccessDeniedException(employeeId, 3, accessLevel);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{employeeId}/{productId}")
	ResponseEntity<?> removeProduct(@PathVariable String employeeId,
			@PathVariable String productId){
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ItemNotFoundException(employeeId));
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ItemNotFoundException(productId));
		
		int accessLevel = CalculateAccessLevel(employee, product);
		if(accessLevel == 4) {
			productRepository.delete(product);
			return ResponseEntity.noContent().build();
		}else {
			throw new AccessDeniedException(employeeId, 3, accessLevel);
		}	
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{organizationId}")
	ResponseEntity<?> changeShareStatus(@PathVariable String organizationId,
			@RequestBody Map<String, Boolean> json){
		
		Organization org = organizationRepository.findById(organizationId).orElseThrow(
				() -> new ItemNotFoundException(organizationId));
		if(json.containsKey("shareStatus")) {
			org.setShareAllowed(json.get("shareStatus"));
			organizationRepository.save(org);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	private int CalculateAccessLevel(
			Employee employee,
			Product product) {
		if(employee.getOrganization().equals(product.getOrganization()))
		{
			return employee.getInternalAccessLevel();
		}
		else
		{
			Organization organization = organizationRepository.findById(product.getOrganization()).orElseThrow(
					() -> new ItemNotFoundException(product.getOrganization()));
			
			if(organization.IsSharingAllowed()) {
				if(product.getQuantity() > 10){
					return employee.getInternalAccessLevel();
				}
				else {
					return 1;
				}
			}
		}
		
		return 0;
	}
	
	
}
