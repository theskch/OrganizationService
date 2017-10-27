package organization.service;

import java.util.Collection;

import organization.model.Product;

public interface ProductService {

	Collection<Product> getProductsOfOrganization(String organizationId);
	
	Collection<Product> getProductsWithPriceGreaterThen(int condition, String organizationId);
	
	Collection<Product> getProductsWithPriceLowerThen(int condition, String organizationId);
	
	Collection<Product> getProductsWithQuantityGreaterThen(int condition, String organizationId);
	
	Collection<Product> getProductsWithQuantityLowerThen(int condition, String organizationId);
	
	Product getProductById(String productId);
	
	void updateProduct(Product product);
	
	void addProduct(Product product);
	
	void deleteProduct(Product product);

}