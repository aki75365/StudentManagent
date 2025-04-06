package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentViewController {

  @Autowired
  private StudentService service;

  // 受講生の詳細表示ページ
  @GetMapping("/studentDetail/{id}")
  public String getStudentDetail(@PathVariable("id") int id, Model model) {
    StudentDetail studentDetail = service.getStudentDetailById(id);
    model.addAttribute("studentDetail", studentDetail);
    return "studentDetail";
  }

  // 編集ページ
  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable("id") int id, Model model) {
    StudentDetail studentDetail = service.getStudentDetailById(id);
    model.addAttribute("studentDetail", studentDetail);
    return "editStudent";
  }

  // フォーム送信で受講生を更新（画面側）
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "editStudent";
    }

    boolean cancelFlag = studentDetail.getStudent().isDeletedFlag();
    Student student = studentDetail.getStudent();
    student.setDeletedFlag(cancelFlag);

    service.updateStudent(student.getId(), student, cancelFlag);

    // 受講生IDを渡して、複数のコース情報を更新する
    service.updateStudentCourses(student.getId(), studentDetail.getStudentCourseList());

    return "redirect:/studentList";
  }


  // 新規受講生登録画面
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    List<StudentCourse> studentCourses = new ArrayList<>();
    studentCourses.add(new StudentCourse(0, 0, "", "", ""));
    studentDetail.setStudentCourseList(studentCourses);
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  // 新規登録処理
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  // 一覧ページ
  @GetMapping("/studentList")
  public String studentListView(Model model) {
    List<Student> students = service.searchgetStudentList();
    model.addAttribute("students", students);
    return "students";
  }
}
