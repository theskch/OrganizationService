package organization.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import organization.model.ProductRepository;
import organization.exceptions.ItemNotFoundException;
import organization.model.Product;

@Service("productService")
public class ProductService{

	private final ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Collection<Product> getProductsOfOrganization(String organizationId){
		return productRepository.findByOrganization(organizationId);
	}

	public Product getProductById(String productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ItemNotFoundException(productId));
	}

	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public Boolean deleteProduct(String productId) {
		return productRepository.deleteById(productId) > 0;
	}

	public Boolean updateProduct(String productId, Map<String, Object> values) {
		return productRepository.updateProduct(productId, values);
	}
}
