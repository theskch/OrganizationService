package organization.model;
import java.util.Collection;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface OrganizationRepository extends MongoRepository<Organization, String>, OrganizationRepositoryCustom {
	
	@ExistsQuery("{ 'id' : ?0 , 'shareAllowed' : true }")
	Boolean isShareAllowed(String organizationId);
	
	@Query("{ id : { $ne : ?0 }}")
	Collection<Organization> findAllExcept(String organizationId);
}
