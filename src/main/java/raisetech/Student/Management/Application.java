package raisetech.Student.Management;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		//localhost:8080
		SpringApplication.run(Application.class, args);
	}
 @GetMapping("/hello")
 public String hello() {
	 StringUtils.
		return "great, World!";

 }
}
