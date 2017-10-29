package organization.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import organization.model.Organization;
import organization.model.OrganizationRepository;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService{

	private final OrganizationRepository organizationRepository;
	
	@Autowired
	public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}
	
	@Override
	public boolean isShareAllowedForOrganization(String organizationId) {
		return organizationRepository.isShareAllowed(organizationId);
	}

	@Override
	public String getAccessPolicyForOperation(String organizationId, String operation) {
		return organizationRepository.getOrganizationOperationPolicy(organizationId, operation);
	}

	@Override
	public void setAccessPolicyForOperation(String organizationId, String operation, String value) {
		organizationRepository.updateOrganizationOperationPolicy(organizationId, operation, value);	
	}

	@Override
	public void changeSharePolicy(String organizationId, boolean newValue) {
		organizationRepository.changeSharePolicy(organizationId, newValue);
	}

	@Override
	public Collection<Organization> findAllExcept(String organizationId) {
		return organizationRepository.findAllExcept(organizationId);
	}

	
}
