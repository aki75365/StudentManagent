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
  private StudentRepository repository;

  @Autowired
  private StudentCourseRepository studentCourseRepository;

  // 受講生情報を取得
  public List<Student> searchgetStudentList() {
    return repository.searchAllStudents();
  }

  // 受講生コース情報を取得
  public List<StudentCourse> getStudentCourseList() {
    return studentCourseRepository.findAllStudentCourses();
  }

  // 受講生のコース情報を受講生IDで取得
  public List<StudentCourse> getStudentCoursesByStudentId(int studentId) {
    // 受講生IDに関連するコース情報を取得
    return studentCourseRepository.findStudentCoursesByStudentId(studentId);
  }

  // 受講生詳細情報を取得（ID指定）
  public StudentDetail getStudentDetailById(int id) {
    // 受講生情報を取得
    Student student = repository.findStudentById(id);
    if (student == null) {
      throw new IllegalArgumentException("指定されたIDの受講生が見つかりません: " + id);
    }

    // 受講生コース情報を取得
    List<StudentCourse> courses = studentCourseRepository.findAllStudentCourses();
    List<StudentCourse> studentCourses = new ArrayList<>();
    for (StudentCourse course : courses) {
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

  // 受講生情報を更新
  public void updateStudent(int id, Student student, boolean cancelFlag) {
    student.setId(id);  // IDを設定してから更新
    // キャンセルフラグがチェックされていた場合、削除フラグを設定
    if (cancelFlag) {
      student.setDeletedFlag(true);  // キャンセルされた受講生は削除フラグを立てる
    }
    repository.updateStudent(id, student);  // 削除フラグを含む情報を更新
  }

  // 受講生コース情報を更新
  public void updateStudentCourses(int studentId, List<StudentCourse> studentCourses) {
    for (StudentCourse course : studentCourses) {
      // 各コースに対してstudentIdを設定して更新する処理を追加
      course.setStudentId(studentId);
      studentCourseRepository.updateStudentCourse(course);
    }
  }
  // 受講生コース情報を更新（単一のコース用）
  public void updateStudentCourse(StudentCourse studentCourse) {
    studentCourseRepository.updateStudentCourse(studentCourse);
  }


  // 受講生登録
  public void registerStudent(StudentDetail studentDetail) {
    if (studentDetail.getStudent() != null) {
      repository.insertStudent(studentDetail.getStudent());
    } else {
      throw new IllegalArgumentException("StudentDetail に Student オブジェクトが含まれていません");
    }
  }

  // 受講生コース情報を新規登録
  public void insertStudentCourse(StudentCourse studentCourse) {
    studentCourseRepository.insertStudentCourse(studentCourse);
  }
}
