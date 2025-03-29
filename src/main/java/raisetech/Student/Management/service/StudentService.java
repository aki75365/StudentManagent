package raisetech.Student.Management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  public StudentDetail searchStudent(String id) {
    // IDを整数に変換
    int studentId;
    try {
      studentId = Integer.parseInt(id);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("IDの形式が不正です: " + id);
    }

    // 受講生情報を取得
    Student student = repository.findStudentById(studentId);
    if (student == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + id);
    }

    // 受講生のコース情報を取得
    List<StudentCourse> courses = repository.findAllStudentCourses();
    List<StudentCourse> studentCourses = courses.stream()
        .filter(course -> course.getStudentId() == studentId)
        .toList();

    // `StudentDetail` にまとめる
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourses);

    return studentDetail;
  }

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
  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      repository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }

  @Transactional
  public void updateStudent(Student student) {
    if (repository.findStudentById(student.getId()) == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + student.getId());
    }
    repository.updateStudent(student);
  }

  @Transactional
  public void updateStudentCourse(StudentCourse studentCourse) {
    repository.updateStudentCourse(studentCourse);
  }

  public StudentDetail getStudentDetailById(int id) {
    Student student = repository.findStudentById(id);
    if (student == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + id);
    }

    // 受講生のコース情報を取得
    List<StudentCourse> courses = repository.findAllStudentCourses();
    List<StudentCourse> studentCourses = courses.stream()
        .filter(course -> course.getStudentId() == id)
        .toList();

    // `StudentDetail` にまとめる
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourses);

    return studentDetail;
  }
}
