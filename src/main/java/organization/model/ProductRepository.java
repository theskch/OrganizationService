package organization.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends MongoRepository<Product, String> {

	Optional<Product> findById(@Param("id") String id);
	List<Product> findByName(@Param("name") String name);
	List<Product> findByOrganization(@Param("organization") String organization);
	
}
