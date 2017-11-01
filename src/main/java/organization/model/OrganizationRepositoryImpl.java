package organization.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import organization.Operation;

public class OrganizationRepositoryImpl implements OrganizationRepositoryCustom{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public String getOrganizationOperationPolicy(String organizationId, Operation operation) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		query.fields().include(operation + "Policy");
		Organization org = mongoTemplate.findOne(query, Organization.class);
		if(org != null) {
			switch(operation) {
				case READ:
					return org.getReadPolicy();
				case UPDATE:
					return org.getUpdatePolicy();
				case DELETE:
					return org.getDeletePolicy();
				default:
					return "false";
			}
		}
		else 
			return "false";
	}

	@Override
	public boolean updateOrganizationOperationPolicy(String organizationId, Operation operation, String value) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		return mongoTemplate.updateFirst(query, Update.update(operation.toString() + "Policy", value), Organization.class).isUpdateOfExisting();
	}

	@Override
	public boolean changeSharePolicy(String organizationId, boolean newValue) {
		Query query = new Query(Criteria.where("id").is(organizationId));
		return mongoTemplate.updateFirst(query, Update.update("shareAllowed", newValue), Organization.class).isUpdateOfExisting();
	}
	
	
}
