package organization.service;

import java.util.Collection;

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
	
	public boolean isShareAllowedForOrganization(String organizationId) {
		return organizationRepository.isShareAllowed(organizationId);
	}

	public String getAccessPolicyForOperation(String organizationId, Operation operation) {
		return organizationRepository.getOrganizationOperationPolicy(organizationId, operation);
	}

	public boolean setAccessPolicyForOperation(String organizationId, Operation operation, String value) {
		return organizationRepository.updateOrganizationOperationPolicy(organizationId, operation, value);	
	}

	public boolean changeSharePolicy(String organizationId, boolean newValue) {
		return organizationRepository.changeSharePolicy(organizationId, newValue);
	}

	public Collection<Organization> findAllExcept(String organizationId) {
		return organizationRepository.findAllExcept(organizationId);
	}

	
}
