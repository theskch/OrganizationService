package organization.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "employees", path= "employees")
public interface EmployeeRepository extends MongoRepository<Employee, String>{
	
	List<Employee> findByFirstName(@Param("firstName") String firstName);
	List<Employee> findByLastName(@Param("lastName") String lastName);
	List<Employee> findByOrganization(@Param("organization") String organization);
	Optional<Employee> findById(@Param("id") String id);
}
