package raisetech.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

@Controller // 修正: @RestController → @Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  // 受講生リスト + コース情報を取得してビューに渡す
  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchgetStudentList();
    List<StudentCourse> studentCourses = service.getStudentCourseList();
    List<StudentDetail> studentDetails = converter.convertStudentDetails(students, studentCourses);

    model.addAttribute("studentList", studentDetails); // Model にデータを追加
    return "studentList"; // `studentList.html` のビューを返す
  }

  // 受講生リストを取得してビューに渡す
  @GetMapping("/students")
  public String getStudentCourse(Model model) {
    List<Student> students = service.searchgetStudentList();
    model.addAttribute("students", students);
    return "student_course"; // `student_course.html` のビューを返す
  }
}
