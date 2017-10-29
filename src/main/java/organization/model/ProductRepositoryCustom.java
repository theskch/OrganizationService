package organization.model;

import java.util.Map;

public interface ProductRepositoryCustom {

	Boolean updateProduct(String productId, Map<String, Object> value);
}
