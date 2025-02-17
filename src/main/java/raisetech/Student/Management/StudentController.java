package raisetech.Student.Management;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class StudentController {

  private final StudentRepository repository;
  private String name = "Enami Kouji";
  private String age = "37";

  @Autowired
  public StudentController(StudentRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/studentInfo")
  public String getStudentInfo() {
    // Studentオブジェクトを検索
    Student student = repository.searchByName("TanakaTarou");

    if (student != null) {
      return student.getName() + " " + student.getAge() + " years old";
    } else {
      return "Student not found";
      //修正
    }
  }

  @PostMapping("/student")
  public void registerStudent(@RequestParam String name, @RequestParam int age) {
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudentName(@RequestParam String name, @RequestParam int age) {
    repository.updateStudent(name, age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(@RequestParam String name) {
    repository.deleteStudent(name); // メソッド名の修正
  }
}
