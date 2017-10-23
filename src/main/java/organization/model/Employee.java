package organization.model;

import org.springframework.data.annotation.Id;

public class Employee {

	@Id 
	private String id;
	private String firstName;
	private String lastName;
	private String organization;
	private int internalAccessLevel;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getInternalAccessLevel() {
		return internalAccessLevel;
	}

	public void setInternalAccessLevel(int internalAccessLevel) {
		this.internalAccessLevel = internalAccessLevel;
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

	public Employee(String id, String firstName, String lastName, String organization,
			int internalAccessLevel) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.organization = organization;
		this.internalAccessLevel = internalAccessLevel;
	}

	public Employee() {
		
	}
}
