package organization.service;

import java.util.Collection;
import java.util.Map;

import organization.model.Product;

public interface ProductService {

	Collection<Product> getProductsOfOrganization(String organizationId);
	
	Product getProductById(String productId);

	void addProduct(Product product);
	
	Boolean deleteProduct(String productId);

	Boolean updateProduct(String productId, Map<String, Object> values);
}