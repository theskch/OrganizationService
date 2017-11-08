package organization.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import organization.Operation;
import organization.model.Organization;
import organization.model.OrganizationRepository;

@Service("organizationService")
public class OrganizationService{

	private final OrganizationRepository organizationRepository;
	
	@Autowired
	public OrganizationService(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}
	
	public boolean isShareAllowedForOrganization(String organizationId, String requestingOrganization) {
		return organizationRepository.isShareAllowed(organizationId, requestingOrganization);
	}

	public String getAccessPolicyForOperation(String organizationId, Operation operation) {
		return organizationRepository.getOrganizationOperationPolicy(organizationId, operation);
	}

	public boolean setAccessPolicyForOperation(String organizationId, Operation operation, String value) {
		return organizationRepository.updateOrganizationOperationPolicy(organizationId, operation, value);	
	}

	public boolean updateSharePolicy(String organizationId, Map<String, String> values) {
		return organizationRepository.updateSharePolicy(organizationId, values);
	}

	public Collection<Organization> findAllExcept(String organizationId) {
		return organizationRepository.findAllExcept(organizationId);
	}

	
}
