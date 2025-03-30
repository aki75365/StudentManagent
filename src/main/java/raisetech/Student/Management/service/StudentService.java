package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.repository.StudentCourseRepository;

import java.util.List;
import java.util.ArrayList;

@Service
public class StudentService {

  @Autowired
  private StudentRepository repository;

  @Autowired
  private StudentCourseRepository studentCourseRepository;

  // 受講生情報を取得
  public List<Student> searchgetStudentList() {
    return repository.searchAllStudents();
  }

  // 受講生コース情報を取得
  public List<raisetech.Student.Management.data.StudentCourse> getStudentCourseList() {
    return studentCourseRepository.findAllStudentCourses();
  }

  // 受講生詳細情報を取得（ID指定）
  public StudentDetail getStudentDetailById(int id) {
    // 受講生情報を取得
    Student student = repository.findStudentById(id);
    if (student == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + id);
    }

    // 受講生コース情報を取得
    List<raisetech.Student.Management.data.StudentCourse> courses = studentCourseRepository.findAllStudentCourses();
    List<raisetech.Student.Management.data.StudentCourse> studentCourses = new ArrayList<>();
    for (raisetech.Student.Management.data.StudentCourse course : courses) {
      if (course.getStudentId() == id) {
        studentCourses.add(course);
      }
    }

    // StudentDetail に受講生情報とコース情報をセット
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourses);

    return studentDetail;
  }

  // 受講生登録
  public void registerStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      repository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }

  // 受講生情報を更新
  public void updateStudent(int id, Student student) {
    student.setId(id);  // IDを設定してから更新
    repository.updateStudent(student);  // 受講生情報を更新
  }

  // 受講生コース情報を更新
  public void updateStudentCourse(raisetech.Student.Management.data.StudentCourse studentCourse) {
    studentCourseRepository.updateStudentCourse(studentCourse);
  }

  // 受講生コース情報を新規登録
  public void insertStudentCourse(raisetech.Student.Management.data.StudentCourse studentCourse) {
    studentCourseRepository.insertStudentCourse(studentCourse);
  }
}
