package organization.model;

import java.util.Map;

import organization.Operation;

public interface OrganizationRepositoryCustom {

	String getOrganizationOperationPolicy(String organizationId, Operation operation);
	
	boolean updateOrganizationOperationPolicy(String organizationId, Operation operation, String value);
	
	boolean updateSharePolicy(String organizationId, Map<String, String> values);
}
