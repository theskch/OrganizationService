package organization.model;

public interface OrganizationRepositoryCustom {

	String getOrganizationOperationPolicy(String organizationId, String operation);
	
	void updateOrganizationOperationPolicy(String organizationId, String operation, String value);
	
	void changeSharePolicy(String organizationId, boolean newValue);
}
