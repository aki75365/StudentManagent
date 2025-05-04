package raisetech.Student.Management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag; // ← 追加
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.exception.StudentNotFoundException;
import raisetech.Student.Management.service.StudentService;
import jakarta.validation.constraints.Size;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/students")
@Tag(name = "Student API", description = "受講生とコース情報を管理するAPI") // ← 追加
public class StudentApiController {

  private final StudentService studentService;

  @Autowired
  public StudentApiController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  @Operation(summary = "全受講生情報の取得", description = "登録されている全ての受講生情報を取得します。")
  public List<Student> getAllStudents() {
    return studentService.getAllStudents();
  }

  @GetMapping("/{studentId}")
  @Operation(summary = "受講生詳細情報の取得", description = "指定したIDの受講生の詳細情報を取得します。")
  public ResponseEntity<StudentDetail> getStudentDetail(
      @PathVariable @Size(min = 1, max = 3, message = "studentIdは1〜3桁の数字で指定してください") String studentId) {
    try {
      int id = Integer.parseInt(studentId);
      StudentDetail detail = studentService.getStudentDetail(id);
      return ResponseEntity.ok(detail);
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest().body(null);

    }
  }

  @PutMapping("/{studentId}")
  @Operation(summary = "受講生情報の更新", description = "指定したIDの受講生情報を更新します。")
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
    } catch (StudentNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("更新に失敗しました: " + e.getMessage());
    }
  }


  @PostMapping
  @Operation(summary = "新規受講生の登録", description = "新しい受講生情報を登録します。")
  public ResponseEntity<String> createStudent(@RequestBody StudentDetail studentDetail) {
    try {
      studentService.registerNewStudent(studentDetail);
      return ResponseEntity.status(201).body("登録が完了しました");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("登録に失敗しました: " + e.getMessage());
    }
  }

  @PutMapping("/{studentId}/courses")
  @Operation(summary = "受講生コース情報の更新", description = "指定した受講生IDに対して、コース情報を更新します。")
  public ResponseEntity<String> updateStudentCourses(
      @PathVariable int studentId,
      @RequestBody List<StudentCourse> studentCourses
  ) {
    studentService.updateStudentCourses(studentId, studentCourses);
    return ResponseEntity.ok("コース情報の更新が完了しました");
  }

  @GetMapping("/{studentId}/courses")
  @Operation(summary = "受講生のコース情報取得", description = "指定した受講生IDに紐づくコース情報を取得します。")
  public List<StudentCourse> getStudentCourses(@PathVariable int studentId) {
    return studentService.getCoursesByStudentId(studentId);
  }


  @PostMapping("/{studentId}/courses")
  @Operation(summary = "受講生コース情報の新規登録", description = "指定した受講生IDに新しいコース情報を登録します。")
  public ResponseEntity<String> registerCourse(@PathVariable int studentId, @RequestBody StudentCourse course) {
    course.setStudentId(studentId);
    studentService.registerNewStudentCourse(course);
    return ResponseEntity.status(201).body("コース情報を追加しました");
  }


  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}

