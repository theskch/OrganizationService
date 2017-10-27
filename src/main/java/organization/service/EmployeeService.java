package organization.service;

import java.util.List;

import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;

public interface EmployeeService {

	List<EmployeeDTO> getAllEmployees();
	
	List<EmployeeDTO> getEmployeesOfOrganization(String organizationId);
	
	List<ProductDTO> getProductsForEmployee(String employeeId);
}
