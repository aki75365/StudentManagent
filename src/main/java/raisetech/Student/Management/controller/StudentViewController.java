package raisetech.Student.Management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.service.StudentService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.Size;

@RestController
public class StudentViewController {

  private final StudentService studentService;

  @Autowired
  public StudentViewController(StudentService studentService) {
    this.studentService = studentService;
  }

  // 受講生一覧を表示
  @GetMapping("/studentList")
  public List<Student> getstudentList() {
    return studentService.getAllStudents();
  }

  // 受講生詳細を表示
  @GetMapping("/{studentId}")
  public String viewStudent(@PathVariable int studentId, Model model) {
    try {
      StudentDetail studentDetail = studentService.getStudentDetail(studentId);
      model.addAttribute("studentDetail", studentDetail);
      return "studentDetail";
    } catch (IllegalArgumentException e) {
      return "redirect:/students";
    }
  }

  // 受講生情報を登録画面表示
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  // 受講生情報を登録処理
  @PostMapping("/register")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail) {
    studentService.registerNewStudent(studentDetail);
    return "redirect:/students";
  }

  // 受講生情報を編集画面表示
  @GetMapping("/edit/{studentId}")
  public String editStudent(@PathVariable int studentId, Model model) {
    try {
      StudentDetail studentDetail = studentService.getStudentDetail(studentId);
      model.addAttribute("studentDetail", studentDetail);
      return "editStudent";
    } catch (IllegalArgumentException e) {
      return "redirect:/students";
    }
  }

  // 受講生情報を更新処理
  @PutMapping("/{studentId}")
  public ResponseEntity<String> updateStudent(
      @PathVariable @Size(min = 1, max = 3, message = "studentIdは1〜3桁の数字で指定してください") String studentId,
      @RequestBody Student student
  ) {
    try {
      int id = Integer.parseInt(studentId);
      student.setId(id); // IDをStudentにセット

      // 削除フラグ（deletedFlag）はすでにStudentに含まれていると仮定
      studentService.updateStudentDetails(student);

      return ResponseEntity.ok("更新が完了しました");
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("studentIdは数値で指定してください");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("更新に失敗しました: " + e.getMessage());
    }
  }
}

