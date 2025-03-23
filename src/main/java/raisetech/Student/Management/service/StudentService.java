package raisetech.Student.Management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  // 全件検索メソッド
  public List<Student> searchgetStudentList() {
    return repository.searchAllStudents();
  }

  // 受講生のコース情報を取得するメソッド
  public List<StudentCourse> getStudentCourseList() {
    return repository.findAllStudentCourses();
  }

  // 30代の受講生を取得するメソッド
  public List<Student> findStudentsInTheir30s() {
    return repository.findStudentsInTheir30s();
  }

  // JAVAコースの受講生を取得するメソッド
  public List<StudentCourse> findJavaCourses() {
    return repository.findJavaCourses();
  }

  // 受講生を登録するメソッド（StudentDetailのまま）
  public void registerStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      repository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }
}
