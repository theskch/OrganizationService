package organization;

import java.util.HashSet;
import java.util.Set;

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
			EmployeeRepository employeeRepository,
			ProductRepository productRepository,
			OrganizationRepository organizationRepository) {
		
		return (evt) -> {
			
			Employee employee1 = new Employee(
					"peraperic", 
					"Pera", 
					"Peric", 
					"orgB", 
					new HashSet<Operation>() 
					{
						{ 
							add(Operation.CREATE);
							add(Operation.READ);
							add(Operation.UPDATE);
							add(Operation.DELETE);
						}
					});
			Employee employee2 = new Employee(
					"mikamikic", 
					"Mika", 
					"Mikic", 
					"orgB", 
					new HashSet<Operation>() 
					{
						{ 
							add(Operation.READ);
						}
					});
			Employee employee3 = new Employee(
					"zorazoric", 
					"Zora", 
					"Zoric", 
					"orgB", 
					new HashSet<Operation>() 
					{
						{ 
							add(Operation.READ);
						}
					});
			Employee employee4 = new Employee(
					"slobastankovic", 
					"Sloba", 
					"Stankovic", 
					"orgC", 
					new HashSet<Operation>() 
					{
						{ 
							add(Operation.CREATE);
							add(Operation.READ);
							add(Operation.UPDATE);
							add(Operation.DELETE);
						}
					});
			Employee employee5 = new Employee(
					"jovanajovanovic", 
					"Jovana", 
					"Jovanovic", 
					"orgC", 
					new HashSet<Operation>() 
					{
						{ 
							add(Operation.CREATE);
							add(Operation.READ);
							add(Operation.UPDATE);
						}
					});
			Employee employee6 = new Employee(
					"nemanjanemanjic", 
					"Nemanja", 
					"Nemanjic", 
					"orgC", 
					new HashSet<Operation>() 
					{
						{ 
							add(Operation.READ);
						}
					});
			
			employeeRepository.save(employee1);
			employeeRepository.save(employee2);
			employeeRepository.save(employee3);
			employeeRepository.save(employee4);
			employeeRepository.save(employee5);
			employeeRepository.save(employee6);
			
			Product product1 = new Product("elektromotor", "Elektromotor", 5, 550.0, "orgB");
			Product product2 = new Product("energetskikabel", "Energetski kabel", 8, 100.0, "orgB");
			Product product3 = new Product("osigurac", "Osigurac", 120, 20.0, "orgB");
			Product product4 = new Product("kuciste", "Kuciste", 20, 200.0, "orgB");
			Product product5 = new Product("vesmasina", "Ves masina", 5, 2000.0, "orgC");
			Product product6 = new Product("bojler", "Bojler", 4, 500.0, "orgC");
			Product product7 = new Product("sporet", "Sporet", 2, 1200.0, "orgC");
			Product product8 = new Product("sudomasina", "Sudo masina", 7, 800.0, "orgC");
			
			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);
			productRepository.save(product8);
			
			Organization organizationB = new Organization("orgB", "B", "quantity > 0", "quantity > 10", "quantity > 10", new HashSet<String>(){{ add("orgC");}});
			Organization organizationC = new Organization("orgC", "C", "false", "false", "false", new HashSet<String>());
			
			organizationRepository.save(organizationB);
			organizationRepository.save(organizationC);
		};
	}
	
}
