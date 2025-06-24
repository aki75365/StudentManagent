package raisetech.Student.Management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import raisetech.Student.Management.repository.StudentRepository2;
import raisetech.Student.Management.data.Student;  // ここを追加

@SpringBootApplication
@RestController
public class Application2 {

	@Autowired
	private StudentRepository2 repository;

	public static void main(String[] args) {
		SpringApplication.run(Application2.class, args);
	}

	// 全受講生リストを取得するエンドポイント
	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.findAllStudents();
	}

	// 全受講生リストを取得するエンドポイント（例）
	@GetMapping("/student_course")
	public List<Student> getStudentCourse() {
		return repository.findAllStudents();
	}
}
