package organization.model;

import java.util.Set;
import org.springframework.data.annotation.Id;
import organization.Operation;

public class Employee {

	@Id 
	private String id;
	private String firstName;
	private String lastName;
	private String organization;
	private Set<Operation> allowedOperations;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public Set<Operation> getAllowedOperations() {
		return allowedOperations;
	}

	public void setAllowedOperations(Set<Operation> allowedOperations) {
		this.allowedOperations = allowedOperations;
	}

	public Employee(String id, String firstName, String lastName, String organization, Set<Operation> allowedOperations) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.organization = organization;
		this.allowedOperations = allowedOperations;
	}

	public Employee() {
		
	}
}
