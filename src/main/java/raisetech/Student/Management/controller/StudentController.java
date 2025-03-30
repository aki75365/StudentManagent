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

  // 受講生一覧を表示する
  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    // 受講生情報を取得
    List<Student> students = service.searchgetStudentList();
    // 受講生のコース情報を取得
    List<StudentCourse> studentCourses = service.getStudentCourseList();
    // 受講生詳細情報に変換
    List<StudentDetail> studentDetails = converter.convertStudentDetails(students, studentCourses);

    model.addAttribute("studentList", studentDetails);
    return "studentList"; // studentList.html に遷移
  }

  // 受講生の詳細情報を表示
  @GetMapping("/studentDetail/{id}")
  public String getStudentDetail(@PathVariable("id") int id, Model model) {
    // 受講生の詳細情報を取得
    StudentDetail studentDetail = service.getStudentDetailById(id);
    model.addAttribute("studentDetail", studentDetail);
    return "studentDetail"; // studentDetail.html に遷移
  }

  // 受講生情報を編集するページ
  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable("id") int id, Model model) {
    // 編集する受講生の詳細情報を取得
    StudentDetail studentDetail = service.getStudentDetailById(id);
    model.addAttribute("studentDetail", studentDetail);
    return "editStudent"; // editStudent.html に遷移
  }

  // 受講生情報を更新する
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "editStudent"; // エラーがあれば editStudent.html に戻る
    }

    // 受講生情報を更新
    service.updateStudent(studentDetail.getStudent().getId(), studentDetail.getStudent());

    // コース情報を更新
    for (StudentCourse course : studentDetail.getStudentCourseList()) {
      service.updateStudentCourse(course);
    }

    return "redirect:/studentList"; // 更新後に受講生一覧に戻る
  }

  // 新規受講生登録フォームを表示
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());

    // studentCourseList を初期化し、最低1件のデータを入れる
    List<StudentCourse> studentCourses = new ArrayList<>();
    studentCourses.add(new StudentCourse(0, 0, "", "", ""));
    studentDetail.setStudentCourseList(studentCourses);

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent"; // registerStudent.html に遷移
  }

  // 新規受講生を登録する
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent"; // エラーがあれば registerStudent.html に戻る
    }

    // 受講生情報を登録
    service.registerStudent(studentDetail);

    return "redirect:/studentList"; // 登録後に受講生一覧に戻る
  }

  // 受講生コース情報を表示
  @GetMapping("/students")
  public String getStudentCourse(Model model) {
    List<Student> students = service.searchgetStudentList();
    model.addAttribute("students", students);
    return "student_course"; // student_course.html に遷移
  }
}
