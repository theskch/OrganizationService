package organization.dto;

import java.util.Set;
import organization.Operation;

public class ProductDTO {

	private String name;	
	private int quantity;	
	private Double price;	
	private Set<Operation> allowedOperations;

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

	public Set<Operation> getAllowedOperations() {
		return allowedOperations;
	}

	public void setAllowedOperations(Set<Operation> allowedOperations) {
		this.allowedOperations = allowedOperations;
	}

	public ProductDTO(String name, int quantity, Double price, Set<Operation> allowedOperations) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.allowedOperations = allowedOperations;
	}
	
	
	
}
