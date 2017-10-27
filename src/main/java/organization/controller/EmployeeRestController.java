package organization.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import organization.service.EmployeeService;
import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;

@RestController
@RequestMapping("employees")
public class EmployeeRestController {

	private final EmployeeService employeeService;
	
	EmployeeRestController(EmployeeService employeeService){
		this.employeeService = employeeService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "")
	List<EmployeeDTO> readOrganizationEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{organizationId}")
	List<EmployeeDTO> readOrganizationEmployees(@PathVariable String organizationId){
		return employeeService.getEmployeesOfOrganization(organizationId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}/products")
	List<ProductDTO> readProductsForEmployee(@PathVariable String employeeId){
		return employeeService.getProductsForEmployee(employeeId);
	}
}
