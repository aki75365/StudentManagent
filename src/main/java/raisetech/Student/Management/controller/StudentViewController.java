package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

@Controller
@RequestMapping("/students") // ★ ベースパスを明示して他と衝突しないように
public class StudentViewController {

  private final StudentService studentService;

  @Autowired
  public StudentViewController(StudentService studentService) {
    this.studentService = studentService;
  }

  // 詳細ページ表示
  @GetMapping("/{studentId}")
  public String viewStudent(@PathVariable int studentId, Model model) {
    try {
      StudentDetail studentDetail = studentService.getStudentDetail(studentId);
      model.addAttribute("studentDetail", studentDetail);
      return "studentDetail"; // Thymeleafなどで studentDetail.html を表示
    } catch (IllegalArgumentException e) {
      return "redirect:/students";
    }
  }

  // 登録フォーム表示
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent"; // registerStudent.html
  }

  // 登録処理
  @PostMapping("/register")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail) {
    studentService.registerNewStudent(studentDetail);
    return "redirect:/students";
  }

  // 編集フォーム表示
  @GetMapping("/edit/{studentId}")
  public String editStudent(@PathVariable int studentId, Model model) {
    try {
      StudentDetail studentDetail = studentService.getStudentDetail(studentId);
      model.addAttribute("studentDetail", studentDetail);
      return "editStudent"; // editStudent.html
    } catch (IllegalArgumentException e) {
      return "redirect:/students";
    }
  }

  // ※ JSON を返すAPI処理（PUT、List取得など）は StudentApiController に移動しました
}
