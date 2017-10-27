package organization.service;

import java.util.Collection;

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
	
	public Collection<Product> getProductsOfOrganization(String organizationId){
		return productRepository.findByOrganization(organizationId);
	}


	public Product getProductById(String productId) {
		return productRepository.findById(productId).orElse(null);
	}


	public void updateProduct(Product updatedProduct) {
		Product product = productRepository.findById(updatedProduct.getId()).orElseThrow(
				() -> new ItemNotFoundException(updatedProduct.getId()));
		
		product.setName(updatedProduct.getName());
		product.setQuantity(updatedProduct.getQuantity());
		product.setPrice(updatedProduct.getPrice());
		productRepository.save(product);
	}
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}

	public Collection<Product> getProductsWithPriceGreaterThen(int condition, String organizationId) {
		return productRepository.priceGreaterThen(condition, organizationId);
	}

	public Collection<Product> getProductsWithPriceLowerThen(int condition, String organizationId) {
		return productRepository.priceLowerThen(condition, organizationId);
	}

	public Collection<Product> getProductsWithQuantityGreaterThen(int condition, String organizationId) {
		return productRepository.quantityGreaterThen(condition, organizationId);
	}

	public Collection<Product> getProductsWithQuantityLowerThen(int condition, String organizationId) {
		return productRepository.quantityLowerThen(condition, organizationId);
	}
}
