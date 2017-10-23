package organization.model;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


public interface OrganizationRepository extends MongoRepository<Organization, String> {
	Optional<Organization> findById(@Param("id") String id);
}
