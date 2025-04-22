package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;
import jakarta.validation.constraints.Size;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/students")
public class StudentApiController {

  private final StudentService studentService;

  @Autowired
  public StudentApiController(StudentService studentService) {
    this.studentService = studentService;
  }

  // 全受講生情報の取得
  @GetMapping
  public List<Student> getAllStudents() {
    return studentService.getAllStudents();
  }

  // 受講生詳細情報の取得
  @GetMapping("/{studentId}")
  public ResponseEntity<StudentDetail> getStudentDetail(
      @PathVariable @Size(min = 1, max = 3, message = "studentIdは1〜3桁の数字で指定してください") String studentId) {
    try {
      int id = Integer.parseInt(studentId);
      StudentDetail detail = studentService.getStudentDetail(id);
      return ResponseEntity.ok(detail);
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body(null); // 数字以外が渡された場合
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // 受講生情報の更新（競合している@PutMappingを1つに修正）
  @PutMapping("/{studentId}")
  public ResponseEntity<String> updateStudent(
      @PathVariable @Size(min = 1, max = 3, message = "studentIdは1〜3桁の数字で指定してください") String studentId,
      @RequestBody Student student
  ) {
    try {
      int id = Integer.parseInt(studentId);
      studentService.updateStudentDetails(id, student, false); // ← false を明示
      return ResponseEntity.ok("更新が完了しました");
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body("studentIdは数値で指定してください");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("更新に失敗しました: " + e.getMessage());
    }
  }


  // 新規受講生登録（Student + Course情報）
  @PostMapping
  public ResponseEntity<String> createStudent(@RequestBody StudentDetail studentDetail) {
    try {
      studentService.registerNewStudent(studentDetail);
      return ResponseEntity.status(201).body("登録が完了しました");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("登録に失敗しました: " + e.getMessage());
    }
  }

  // 受講生コース情報の更新
  @PutMapping("/{studentId}/courses")
  public ResponseEntity<String> updateStudentCourses(
      @PathVariable int studentId,
      @RequestBody List<StudentCourse> studentCourses
  ) {
    studentService.updateStudentCourses(studentId, studentCourses);
    return ResponseEntity.ok("コース情報の更新が完了しました");
  }

  // 受講生のコース情報の取得
  @GetMapping("/{studentId}/courses")
  public List<StudentCourse> getStudentCourses(@PathVariable int studentId) {
    return studentService.getCoursesByStudentId(studentId);
  }

  // 受講生コース情報の新規登録（個別）
  @PostMapping("/{studentId}/courses")
  public ResponseEntity<String> registerCourse(@PathVariable int studentId, @RequestBody StudentCourse course) {
    course.setStudentId(studentId);
    studentService.registerNewStudentCourse(course);
    return ResponseEntity.status(201).body("コース情報を追加しました");
  }
}
