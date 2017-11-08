package organization.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import organization.Operation;
import organization.service.OrganizationService;


@RestController
@RequestMapping("organization")
public class OrganizationRestCotroller {
	
	private final OrganizationService organizationService;
	
	OrganizationRestCotroller(OrganizationService organizationService){
		this.organizationService = organizationService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{organizationId}/{policy}")
	String readOrganizationEmployees(@PathVariable String organizationId, @PathVariable String policy){
		return organizationService.getAccessPolicyForOperation(organizationId, Operation.getEnum(policy));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{organizationId}/{policy}")
	boolean updateOrganizationPolicy(@PathVariable String organizationId, 
			@PathVariable String policy,
			@RequestBody Map<String, String> json){
		return organizationService.setAccessPolicyForOperation(organizationId, Operation.getEnum(policy), json.get("newValue"));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{organizationId}")
	boolean changeSharePolicy(@PathVariable String organizationId,
			@RequestBody Map<String, String> json) {
		return organizationService.updateSharePolicy(organizationId, json);
	}
	
}
