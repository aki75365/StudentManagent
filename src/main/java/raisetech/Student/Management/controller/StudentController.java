package raisetech.Student.Management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

@Controller
public class StudentController {

  @Autowired
  private StudentService service;

  @Autowired
  private StudentConverter converter;

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchgetStudentList();
    List<StudentCourse> studentCourses = service.getStudentCourseList();
    List<StudentDetail> studentDetails = converter.convertStudentDetails(students, studentCourses);

    model.addAttribute("studentList", studentDetails);
    return "studentList";
  }

  @GetMapping("/students")
  public String getStudentCourse(Model model) {
    List<Student> students = service.searchgetStudentList();
    model.addAttribute("students", students);
    return "student_course";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());

    // studentCourseList を初期化し、最低1件のデータを入れる
    List<StudentCourse> studentCourses = new ArrayList<>();
    studentCourses.add(new StudentCourse(0, 0, "", "", ""));
    studentDetail.setStudentCourseList(studentCourses);

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    Student student = studentDetail.getStudent();
    if (student == null) {
      student = new Student();
      studentDetail.setStudent(student);
    }

    // 受講生情報を登録
    service.registerStudent(studentDetail);

    return "redirect:/studentList";
  }

  // **受講生の詳細情報を表示するエンドポイントを追加**
  @GetMapping("/studentDetail/{id}")
  public String getStudentDetail(@PathVariable("id") int id, Model model) {
    StudentDetail studentDetail = service.getStudentDetailById(id);
    model.addAttribute("studentDetail", studentDetail);
    return "studentDetail";
  }
}


