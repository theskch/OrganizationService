package organization;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import organization.model.Employee;
import organization.model.EmployeeRepository;
import organization.model.Organization;
import organization.model.OrganizationRepository;
import organization.model.Product;
import organization.model.ProductRepository;


@ComponentScan
@SpringBootApplication
public class OrganizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(
			OrganizationRepository organizationRepository,
			ProductRepository productRepository) {
		return (evt) -> {
			/*Organization organization = new Organization("C", "C", "false", "false", "false", false);
			organizationRepository.save(organization);*/
			
			
			Product product = new Product("elektromotor", "Elektromotor", 5, 550.0, "B");
			Product product1 = new Product("energetskikabel", "Energetski kabel", 8, 100.0, "B");
			Product product2 = new Product("osigurac", "Osigurac", 120, 20.0, "B");
			Product product3 = new Product("kuciste", "Kuciste", 20, 200.0, "B");
			Product product4 = new Product("vesmasina", "Ves Masina", 5, 2000.0, "C");
			Product product5 = new Product("bojler", "Bojler", 4, 500.0, "C");
			Product product6 = new Product("sporet", "Sporet", 2, 1200.0, "C");
			Product product7 = new Product("sudomasina", "Sudo masina", 7, 800.0, "C");
			
			productRepository.save(product);
			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);
			
		};
	}
	
	/*@Bean
	CommandLineRunner init(
			EmployeeRepository employeeRepository,
			ProductRepository productRepository,
			OrganizationRepository organizationRepository) {
		
		return (evt) -> {
			Employee employee1 = new Employee(
					"peraperic", "Pera", "Peric", "B", 4);
			Employee employee2 = new Employee(
					"mikamikic", "Mika", "Mikic", "B", 1);
			Employee employee3 = new Employee(
					"zorazoric", "Zora", "Zoric", "B", 1);
			Employee employee4 = new Employee(
					"slobastankovic", "Sloba", "Stankovic", "C", 4);
			Employee employee5 = new Employee(
					"jovanajovanovic", "Jovana", "Jovanovic", "C", 3);
			Employee employee6 = new Employee(
					"nemanjanemanjic", "Nemanja", "Nemanjic", "C", 1);
			
			employeeRepository.save(employee1);
			employeeRepository.save(employee2);
			employeeRepository.save(employee3);
			employeeRepository.save(employee4);
			employeeRepository.save(employee5);
			employeeRepository.save(employee6);
			
			Product product1 = new Product(
					"elektromotor", "Elektromotor", 5, 550.0, "B", 0);
			Product product2 = new Product(
					"energetskikabel", "Energetski kabel", 8, 100.0, "B", 0);
			Product product3 = new Product(
					"osigurac", "Osigurac", 20, 20.0, "B", 0);
			Product product4 = new Product(
					"kuciste", "Kuciste", 20, 200.0, "B", 0);
			Product product5 = new Product(
					"vesmasina", "Ves masina", 5, 2000.0, "C", 0);
			Product product6 = new Product(
					"bojler", "Bojler", 4, 500.0, "C", 0);
			Product product7 = new Product(
					"sporet", "Sporet", 2, 1200.0, "C", 0);
			Product product8 = new Product(
					"sudomasina", "Sudo masina", 7, 800.0, "C", 0);
			
			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);
			productRepository.save(product8);
			
			Organization organization1 = new Organization("B", "B", true, false);
			Organization organization2 = new Organization("C", "C", false, false);
			
			organizationRepository.save(organization1);
			organizationRepository.save(organization2);
		};
	}*/
	
	
}
