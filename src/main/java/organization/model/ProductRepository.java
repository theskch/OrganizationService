package organization.model;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

	Optional<Product> findById(String id);
	
	List<Product> findByOrganization(String organization);
	
	@DeleteQuery("{id : ?0}")
	Long deleteById(String id);
}
