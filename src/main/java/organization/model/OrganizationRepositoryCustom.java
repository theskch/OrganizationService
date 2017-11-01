package organization.model;

import organization.Operation;

public interface OrganizationRepositoryCustom {

	String getOrganizationOperationPolicy(String organizationId, Operation operation);
	
	boolean updateOrganizationOperationPolicy(String organizationId, Operation operation, String value);
	
	boolean changeSharePolicy(String organizationId, boolean newValue);
}
