package organization.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

	Optional<Product> findById(String id);
	
	List<Product> findByName(String name);
	
	List<Product> findByOrganization(String organization);
	
	@Query("{ 'Price' : { $gt: ?0 }, 'organization' : ?1}")
	List<Product> priceGreaterThen(int condition, String organizationId);
	
	@Query("{ 'Price' : { $lt: ?0 }, 'organization' : ?1}")
	List<Product> priceLowerThen(int condition, String organizationId);
	
	@Query("{ 'quantity' : { $gt: ?0 }, 'organization' : ?1}")
	List<Product> quantityGreaterThen(int condition, String organizationId);
	
	@Query("{ 'quantity' : { $lt: ?0 }, 'organization' : ?1}")
	List<Product> quantityLowerThen(int condition, String organizationId);
	
}
