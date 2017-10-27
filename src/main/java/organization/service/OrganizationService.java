package organization.service;

import java.util.Collection;

import organization.model.Organization;

public interface OrganizationService {

	boolean isShareAllowedForOrganization(String organizationId);
	
	String getAccessPolicyForOperation(String organizationId, String operation);
	
	void setAccessPolicyForOperation(String organizationId, String operation, String value);
	
	void changeSharePolicy(String organizationId, boolean newValue);
	
	Collection<Organization> findAllExcept(String organizationId);
}
