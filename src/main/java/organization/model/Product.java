package organization.model;

import org.springframework.data.annotation.Id;

public class Product {

	@Id
	private String id;
	private String name;
	private int quantity;
	private Double price;
	private String organization;
	private int accessLevel;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	public Product() {
		
	}

	public Product(String id,String name, int quantity, Double price, String organization, int accessLevel) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.organization = organization;
		this.accessLevel = accessLevel;
	}
	
	
}
