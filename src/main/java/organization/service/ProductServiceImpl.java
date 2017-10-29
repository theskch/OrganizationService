package organization.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import organization.model.ProductRepository;
import organization.exceptions.ItemNotFoundException;
import organization.model.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Collection<Product> getProductsOfOrganization(String organizationId){
		return productRepository.findByOrganization(organizationId);
	}

	@Override
	public Product getProductById(String productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ItemNotFoundException(productId));
	}

	@Override
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	@Override
	public Boolean deleteProduct(String productId) {
		return productRepository.deleteById(productId) > 0;
	}

	@Override
	public Boolean updateProduct(String productId, Map<String, Object> values) {
		return productRepository.updateProduct(productId, values);
	}
}
