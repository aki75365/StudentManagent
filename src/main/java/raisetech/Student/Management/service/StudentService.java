package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.repository.StudentCourseRepository;

import java.util.List;
import java.util.ArrayList;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private StudentCourseRepository studentCourseRepository;

  // 受講生情報を全件取得
  public List<Student> getAllStudents() {
    return studentRepository.searchAllStudents();
  }

  // 受講生のコース情報を全件取得
  public List<StudentCourse> getAllStudentCourses() {
    return studentCourseRepository.findAllStudentCourses();
  }

  // 受講生のコース情報を受講生IDで取得
  public List<StudentCourse> getCoursesByStudentId(int studentId) {
    return studentCourseRepository.findStudentCoursesByStudentId(studentId);
  }

  // 受講生詳細情報をID指定で取得
  public StudentDetail getStudentDetail(int studentId) {
    // 受講生情報を取得
    Student student = studentRepository.findStudentById(studentId);
    if (student == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + studentId);
    }

    // 受講生コース情報を取得
    List<StudentCourse> studentCourses = new ArrayList<>();
    for (StudentCourse course : studentCourseRepository.findAllStudentCourses()) {
      if (course.getStudentId() == studentId) {
        studentCourses.add(course);
      }
    }

    // StudentDetail に受講生情報とコース情報をセット
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourses);

    return studentDetail;
  }

  // 受講生情報を更新
  public void updateStudentDetails(int studentId, Student student, boolean cancelFlag) {
    student.setId(studentId);
    if (cancelFlag) {
      student.setDeletedFlag(true);
    }
    studentRepository.updateStudent(studentId, student); // int型でそのまま渡す
  }

  // 受講生コース情報を更新
  public void updateStudentCourses(int studentId, List<StudentCourse> studentCourses) {
    for (StudentCourse course : studentCourses) {
      course.setStudentId(studentId);  // 各コースに受講生IDを設定
      studentCourseRepository.updateStudentCourse(course);
    }
  }

  // 単一の受講生コース情報を更新
  public void updateSingleStudentCourse(StudentCourse studentCourse) {
    studentCourseRepository.updateStudentCourse(studentCourse);
  }

  // 受講生を登録
  public void registerNewStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      studentRepository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }

  // 受講生コース情報を新規登録
  public void registerNewStudentCourse(StudentCourse studentCourse) {
    studentCourseRepository.insertStudentCourse(studentCourse);
  }
}
