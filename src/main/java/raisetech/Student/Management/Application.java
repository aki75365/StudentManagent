package raisetech.Student.Management;

import java.util.List; // ✅ List をインポート
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/student")  // 共通のURLプレフィックス
public class Application {

	@Autowired
	private  StudentRepository repository;

	public Application(StudentRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/info")
	public String getStudentInfo(@RequestParam String name) {
		Student student = repository.searchByName(name);
		if (student != null) {
			return student.getName() + " is " + student.getAge() + " years old.";
		} else {
			return "Student not found.";
		}
	}

	@PostMapping("/register")
	public void registerStudent(@RequestParam String name, @RequestParam int age) {
		repository.registerStudent(name, age);
	}


	@PatchMapping("/update")
	public void updateStudent(@RequestParam String name, @RequestParam int age) {
		repository.updateStudent(name, age);
	}

	@DeleteMapping("/delete")
	public void deleteStudent(@RequestParam String name) {
		repository.deleteStudent(name);
	}
	@GetMapping("/studentList")
	public List<Student> getAllStudents() {
		return repository.getAllStudents();
	}

}