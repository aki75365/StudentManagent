package raisetech.Student.Management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.Management.repository.StudentRepository2;

@SpringBootApplication
@RestController
public class Application2 {

	@Autowired
	private StudentRepository2 repository;

	public static void main(String[] args) {
		SpringApplication.run(Application2.class, args);
	}




}
