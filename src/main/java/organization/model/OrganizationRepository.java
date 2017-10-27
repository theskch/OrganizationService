package organization.model;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface OrganizationRepository extends MongoRepository<Organization, String>, OrganizationRepositoryCustom {
	
	Optional<Organization> findById(String id);
	
	@CountQuery("{ 'id' : ?0 , 'shareAllowed' : true }")
	Long isShareAllowed(String organizationId);
	
	@Query("{ id : { $ne : ?0 }}")
	Collection<Organization> findAllExcept(String organizationId);
}
