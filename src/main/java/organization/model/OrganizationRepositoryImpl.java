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
		query.fields().include(operation + "Policy");
		Organization org = mongoTemplate.findOne(query, Organization.class);
		if(org != null) {
			switch(operation) {
				case "read":
					return org.getReadPolicy();
				case "update":
					return org.getUpdatePolicy();
				case "delete":
					return org.getDeletePolicy();
				default:
					return "false";
			}
		}
		else 
			return "false";
	}

	@Override
	public void updateOrganizationOperationPolicy(String organizationId, String operation, String value) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		mongoTemplate.updateFirst(query, Update.update(operation + "Policy", value), Organization.class);
	}

	@Override
	public void changeSharePolicy(String organizationId, boolean newValue) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		mongoTemplate.updateFirst(query, Update.update("shareAllowed", newValue), Organization.class);
	}
	
	
}
