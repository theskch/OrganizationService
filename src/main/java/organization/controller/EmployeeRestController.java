package organization.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import organization.service.EmployeeService;
import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;
import organization.model.Product;

@RestController
@RequestMapping("employees")
public class EmployeeRestController {

	private final EmployeeService employeeService;
	
	EmployeeRestController(EmployeeService employeeService){
		this.employeeService = employeeService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "")
	List<EmployeeDTO> readEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{organizationId}")
	List<EmployeeDTO> readOrganizationEmployees(@PathVariable String organizationId){
		return employeeService.getEmployeesOfOrganization(organizationId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}/products")
	List<ProductDTO> readProducts(@PathVariable String employeeId){
		return employeeService.getProductsForEmployee(employeeId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}/products/{productId}")
	ProductDTO readProduct(@PathVariable String employeeId, @PathVariable String productId){
		return employeeService.getProductForEmployee(employeeId, productId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{employeeId}")
	Boolean addProduct(@PathVariable String employeeId, @RequestBody Product newProduct) {
		return employeeService.addProductByEmployee(employeeId, newProduct);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{employeeId}/products/{productId}")
	Boolean deleteProduct(@PathVariable String employeeId, @PathVariable String productId) {
		return employeeService.deleteProductByEmployee(employeeId, productId);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{employeeId}/products/{productId}")
	Boolean updateProduct(@PathVariable String employeeId, @PathVariable String productId, @RequestBody Map<String, Object> values) {
		return employeeService.updateProductByEmployee(employeeId, productId, values);
	}
}
