package raisetech.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.service.StudentService;

@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  // 全受講生リストを取得するエンドポイント
  @GetMapping("/studentList")
  public List<Student> searchgetStudentList() {
    return service.searchgetStudentList();
  }

  // 全受講生リストを取得するエンドポイント
  @GetMapping("/student_course")
  public List<Student> getStudent_course() {
    return service.searchgetStudentList();
  }

  // 30代の受講生を取得するエンドポイント
  @GetMapping("/students/30s")
  public List<Student> getStudentsInTheir30s() {
    return service.findStudentsInTheir30s();
  }

  // JAVAコースの受講生を取得するエンドポイント
  @GetMapping("/students/java-courses")
  public List<StudentCourse> getJavaCourses() {
    return service.findJavaCourses();
  }
}
