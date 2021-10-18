package liss.nvms;

import liss.nvms.manage.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class LissAPP implements CommandLineRunner {

	@Autowired
	UserRepository userRep;

	@Autowired private PasswordEncoder bcryptEncoder;
	
	public static void main (String[] args) {       
		//ConfigurableApplicationContext context = 
				SpringApplication.run(LissAPP.class, args);       
    }

	public void run(String... args) throws Exception {
		System.err.println("Application run ...");
		
	}

	public UserEntity savedUser(UserEntity dto) {
		UserEntity userEnt = dto;
		userEnt.setEmail("jeanedouga@gmail.com");
		userEnt.setName("django");
		userEnt.setPhone("676344842");
		userEnt.setCfp("admin");
		userEnt.setPassword(bcryptEncoder.encode("admin"));
		userEnt = userRep.save(userEnt);
		return userEnt;
	}
	
	 
}