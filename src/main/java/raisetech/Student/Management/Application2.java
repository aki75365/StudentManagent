package raisetech.Student.Management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application2 {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application2.class, args);
	}

	// 全受講生リストを取得するエンドポイント
	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.findAllStudents();
	}

	// 全受講生リストを取得するエンドポイント
	@GetMapping("/student_course")
	public List<Student> getStudent_course() {
		return repository.findAllStudents();
	}
}
