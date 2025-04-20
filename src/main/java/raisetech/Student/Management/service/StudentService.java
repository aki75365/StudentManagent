package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ← 追加
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

  // 読み取り：受講生情報を全件取得
  public List<Student> getAllStudents() {
    return studentRepository.searchAllStudents();
  }

  // 読み取り：受講生のコース情報を全件取得
  public List<StudentCourse> getAllStudentCourses() {
    return studentCourseRepository.findAllStudentCourses();
  }

  // 読み取り：受講生IDでコース情報取得
  public List<StudentCourse> getCoursesByStudentId(int studentId) {
    return studentCourseRepository.findStudentCoursesByStudentId(studentId);
  }

  // 読み取り：受講生詳細情報を取得
  public StudentDetail getStudentDetail(int studentId) {
    Student student = studentRepository.findStudentById(studentId);
    if (student == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + studentId);
    }

    List<StudentCourse> studentCourses = new ArrayList<>();
    for (StudentCourse course : studentCourseRepository.findAllStudentCourses()) {
      if (course.getStudentId() == studentId) {
        studentCourses.add(course);
      }
    }

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourses);

    return studentDetail;
  }

  // 書き込み：受講生情報の更新
  @Transactional
  public void updateStudentDetails(int studentId, Student student, boolean cancelFlag) {
    student.setId(studentId);
    if (cancelFlag) {
      student.setDeletedFlag(true);
    }
    studentRepository.updateStudent(studentId, student);
  }

  // 書き込み：複数の受講生コース情報を更新
  @Transactional
  public void updateStudentCourses(int studentId, List<StudentCourse> studentCourses) {
    for (StudentCourse course : studentCourses) {
      course.setStudentId(studentId);
      studentCourseRepository.updateStudentCourse(course);
    }
  }

  // 書き込み：単一の受講生コース情報を更新
  @Transactional
  public void updateSingleStudentCourse(StudentCourse studentCourse) {
    studentCourseRepository.updateStudentCourse(studentCourse);
  }

  // 書き込み：受講生情報を新規登録
  @Transactional
  public void registerNewStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      studentRepository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }

  // 書き込み：受講生コース情報を新規登録
  @Transactional
  public void registerNewStudentCourse(StudentCourse studentCourse) {
    studentCourseRepository.insertStudentCourse(studentCourse);
  }
}
