package organization.model;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public Boolean updateProduct(String productId, Map<String, Object> values) {
		Query query = new Query(Criteria.where("id").is(productId));
		Update update = new Update();
		
		boolean updateSuccessfull = true;
		for(Map.Entry<String, Object> entry : values.entrySet()) {
			
			String k = entry.getKey();
			Object v = entry.getValue();
			
			switch(entry.getKey()) {
				case "name": 
					update.set(k, v.toString());
					break;
				case "quantity":
					try {
						update.set(k, Integer.parseInt(v.toString()));			
					}catch(NumberFormatException nfe) {
						updateSuccessfull = false;
					}
					break;
				case "price":
					try {
						update.set(k, Double.parseDouble(v.toString()));			
					}catch(NumberFormatException nfe) {
						updateSuccessfull = false;
					}
			}
		}
		
		mongoTemplate.updateFirst(query, update, Product.class);
		return updateSuccessfull;
	}

}
