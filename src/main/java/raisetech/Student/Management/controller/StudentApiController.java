package raisetech.Student.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;
import raisetech.Student.Management.controller.converter.StudentConverter;

import java.util.List;

@RestController
public class StudentApiController {

  @Autowired
  private StudentService service;

  @Autowired
  private StudentConverter converter;

  // API：受講生一覧（JSONで返す）
  @GetMapping("/api/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchgetStudentList();
    List<StudentCourse> studentCourses = service.getStudentCourseList();
    return converter.convertStudentDetails(students, studentCourses);
  }

  // 受講生情報更新
  @PostMapping("/api/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {

    boolean cancelFlag = studentDetail.getStudent().isDeletedFlag();
    Student student = studentDetail.getStudent();
    student.setDeletedFlag(cancelFlag);

    service.updateStudent(student.getId(), student, cancelFlag);

    // studentId とコース情報を渡して一括更新
    service.updateStudentCourses(student.getId(), studentDetail.getStudentCourseList());

    return ResponseEntity.ok("更新処理が成功しました");
  }



  // API：受講生IDに関連するコース情報を取得
  @GetMapping("/api/student/{id}/courses")
  public ResponseEntity<List<StudentCourse>> getStudentCourses(@PathVariable("id") int id) {
    List<StudentCourse> courses = service.getStudentCoursesByStudentId(id);
    return ResponseEntity.ok(courses);
  }

  // API：受講生のコース情報を更新
  @PostMapping("/api/student/{id}/courses")
  public ResponseEntity<String> updateStudentCourses(@PathVariable("id") int id, @RequestBody List<StudentCourse> studentCourses) {
    service.updateStudentCourses(id, studentCourses);
    return ResponseEntity.ok("受講生のコース情報が更新されました");
  }
}
