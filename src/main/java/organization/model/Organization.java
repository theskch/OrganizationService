package organization.model;

import org.springframework.data.annotation.Id;

public class Organization {

	@Id
	private String id;
	private String name;
	private String readPolicy;
	private String updatePolicy;
	private String deletePolicy;
	private boolean shareAllowed;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isShareAllowed() {
		return shareAllowed;
	}

	public void setShareAllowed(boolean shareAllowed) {
		this.shareAllowed = shareAllowed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReadPolicy() {
		return readPolicy;
	}

	public void setReadPolicy(String readPolicy) {
		this.readPolicy = readPolicy;
	}

	public String getUpdatePolicy() {
		return updatePolicy;
	}

	public void setUpdatePolicy(String updatePolicy) {
		this.updatePolicy = updatePolicy;
	}

	public String getDeletePolicy() {
		return deletePolicy;
	}

	public void setDeletePolicy(String deletePolicy) {
		this.deletePolicy = deletePolicy;
	}

	public Organization(String id, String name, String readPolicy, String updatePolicy, String deletePolicy,
			boolean shareAllowed) {
		super();
		this.id = id;
		this.name = name;
		this.readPolicy = readPolicy;
		this.updatePolicy = updatePolicy;
		this.deletePolicy = deletePolicy;
		this.shareAllowed = shareAllowed;
	}
	
	public Organization() {
		
	}
	
}
