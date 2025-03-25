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

  public List<Student> searchgetStudentList() {
    return repository.searchAllStudents();
  }

  public List<StudentCourse> getStudentCourseList() {
    return repository.findAllStudentCourses();
  }

  public List<Student> findStudentsInTheir30s() {
    return repository.findStudentsInTheir30s();
  }

  public List<StudentCourse> findJavaCourses() {
    return repository.findJavaCourses();
  }

  public void registerStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      repository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }

  // **追加: 受講生の詳細情報を取得するメソッド**
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
