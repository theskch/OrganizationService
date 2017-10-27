package organization.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import organization.service.OrganizationService;


@RestController
@RequestMapping("organization")
public class OrganizationRestCotroller {
		
	private final OrganizationService organizationService;
	
	@Autowired
	OrganizationRestCotroller(OrganizationService organizationService){
		this.organizationService = organizationService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{organizationId}/{policy}")
	String readOrganizationEmployees(@PathVariable String organizationId, @PathVariable String policy){
		return organizationService.getAccessPolicyForOperation(organizationId, policy);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{organizationId}/{policy}")
	void updateOrganizationPolicy(@PathVariable String organizationId, 
			@PathVariable String policy,
			@RequestBody Map<String, String> json){
		organizationService.setAccessPolicyForOperation(organizationId, policy, json.get("newValue"));
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organizationId")
	void changeSharePolicy(@PathVariable String organizationId,
			@RequestBody Map<String, Boolean> json) {
		organizationService.changeSharePolicy(organizationId, json.get("newValue"));
	}
	
}
