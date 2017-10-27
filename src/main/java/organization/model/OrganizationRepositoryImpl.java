package organization.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class OrganizationRepositoryImpl implements OrganizationRepositoryCustom{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public String getOrganizationOperationPolicy(String organizationId, String operation) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		query.fields().include(operation);
		Organization org = mongoTemplate.findOne(query, Organization.class);
		if(org != null)
			return org.getReadPolicy();
		else return "failed";
	}

	@Override
	public void updateOrganizationOperationPolicy(String organizationId, String operation, String value) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		mongoTemplate.updateFirst(query, Update.update(operation, value), Organization.class);
	}

	@Override
	public void changeSharePolicy(String organizationId, boolean newValue) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		mongoTemplate.updateFirst(query, Update.update("shareAllowed", newValue), Organization.class);
	}
	
	
}
