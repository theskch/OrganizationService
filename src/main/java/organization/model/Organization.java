package organization.model;

import org.springframework.data.annotation.Id;

public class Organization {

	@Id
	private String id;
	private String name;
	private boolean sharable;
	private boolean shareAllowed;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSharable() {
		return sharable;
	}

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	public boolean isShareAllowed() {
		return shareAllowed;
	}

	public void setShareAllowed(boolean shareAllowed) {
		this.shareAllowed = shareAllowed;
	}

	public Organization(String id, String name, boolean sharable, boolean shareAllowed) {
		super();
		this.id = id;
		this.name = name;
		this.sharable = sharable;
		this.shareAllowed = shareAllowed;
	}
	
	public boolean IsSharingAllowed()
	{
		return sharable && shareAllowed;
	}
	
}
