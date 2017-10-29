package organization.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String>{
	
	List<Employee> findByOrganization(@Param("organization") String organization);
	Optional<Employee> findById(@Param("id") String id);
}
