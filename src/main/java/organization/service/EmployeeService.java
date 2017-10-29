package organization.service;

import java.util.List;
import java.util.Map;

import organization.dto.EmployeeDTO;
import organization.dto.ProductDTO;
import organization.model.Product;

public interface EmployeeService {

	List<EmployeeDTO> getAllEmployees();
	
	List<EmployeeDTO> getEmployeesOfOrganization(String organizationId);
	
	List<ProductDTO> getProductsForEmployee(String employeeId);
	
	ProductDTO getProductForEmployee(String employeeId, String productId);
	
	Boolean addProductByEmployee(String employeeId, Product product);
	
	Boolean deleteProductByEmployee(String employeeId, String productId);
	
	Boolean updateProductByEmployee(String employeeId, String productId, Map<String, Object> values);
}
