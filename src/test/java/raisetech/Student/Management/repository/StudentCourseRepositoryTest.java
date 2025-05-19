package raisetech.Student.Management.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudentCourseRepositoryTest {

  @Autowired
  private StudentCourseRepository studentCourseRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Test
  void testFindStudentCoursesByStudentId() {// 受講生IDを使ってコース情報を取得し、中身の検証を行うテスト
    // 生徒ID 1 のコースをDBから取得
    int studentId = 1;
    List<StudentCourse> courses = studentCourseRepository.findStudentCoursesByStudentId(studentId);

    // 1件以上あることを確認
    assertThat(courses).isNotEmpty();

    // 期待値のStudentCourseインスタンスを作成
    StudentCourse expectedCourse = new StudentCourse(
        1,                // id（コースID）
        studentId,        // student_id
        "Javaプログラミング基礎", // course_name
        "2024-01-10",     // start_date
        "2024-06-10"      // end_date
    );

    // 検索結果に期待のコースが含まれていることを検証（equalsが必要に応じて実装されている前提）
    assertThat(courses).contains(expectedCourse);
  }



  @Test
  void testUpdateStudentCourse() { // コース情報を更新できるかのテスト
    Student student = insertDummyStudent();
    StudentCourse course = insertDummyCourse(student.getId());
    course.setCourseName("Spring");
    studentCourseRepository.updateStudentCourse(course);

    List<StudentCourse> updated = studentCourseRepository.findStudentCoursesByStudentId(
        student.getId());
    assertThat(updated.get(0).getCourseName()).isEqualTo("Spring"); // 更新されたことを確認
  }

  @Test
  void testInsertStudentCourse() { // コース情報の登録処理が成功するかのテスト
    Student student = insertDummyStudent();
    StudentCourse course = insertDummyCourse(student.getId());

    List<StudentCourse> found = studentCourseRepository.findStudentCoursesByStudentId(student.getId());
    assertThat(found).isNotEmpty();

    // idフィールドはDBで自動採番されるので無視して比較
    assertThat(found.get(0))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(course);
  }


  @Test
  public void testFindAllStudentCourses() { // 全受講生のコース情報を一覧で取得できるかのテスト
    // まずテスト用のデータを追加
    Student student = insertDummyStudent();
    StudentCourse insertedCourse = insertDummyCourse(student.getId());

    // 全件取得
    List<StudentCourse> allCourses = studentCourseRepository.findAllStudentCourses();

    // insertedCourse と比較するために id フィールドは無視して比較
    assertThat(allCourses).anySatisfy(course ->
        assertThat(course)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(insertedCourse)
    );
  }

  private Student insertDummyStudent() {
    Student student = new Student(0, "テスト太郎", "テストタロウ", "テスティー",
        "test@example.com", "東京", 33, "Male", "備考内容", false);
    studentRepository.insertStudent(student);
    return student;
  }

  private StudentCourse insertDummyCourse(int studentId) {
    StudentCourse course = new StudentCourse(
        0,
        studentId,
        "JAVA",
        LocalDate.now().toString(),
        LocalDate.now().plusMonths(2).toString()
    );
    studentCourseRepository.insertStudentCourse(course);
    return course;
  }

  @Test
  void testFindCoursesForYamadaKenta() {
    int studentId = 1;
    StudentCourse expected = new StudentCourse(
        1, studentId, "Javaプログラミング基礎", "2024-01-10", "2024-06-10"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

  @Test
  void testFindCoursesForSatoHanako() {
    int studentId = 2;
    StudentCourse expected = new StudentCourse(
        2, studentId, "データベース設計", "2024-02-01", "2024-07-01"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

  @Test
  void testFindCoursesForYamadaTaro() {
    int studentId = 3;
    StudentCourse expected = new StudentCourse(
        3, studentId, "Python入門", "2024-03-15", "2024-08-15"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

  @Test
  void testFindCoursesForTanakaHanako() {
    int studentId = 4;
    StudentCourse expected = new StudentCourse(
        4, studentId, "Javaプログラミング基礎", "2025-02-01", "2025-04-01"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

  @Test
  void testFindCoursesForSuzukiIchiro() {
    int studentId = 5;
    StudentCourse expected = new StudentCourse(
        5, studentId, "Web開発入門", "2025-02-15", "2025-05-15"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

  @Test
  void testFindCoursesForSatoMisaki1() {
    int studentId = 6;
    StudentCourse expected = new StudentCourse(
        6, studentId, "データベース設計", "2025-03-01", "2025-06-01"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

  @Test
  void testFindCoursesForSatoMisaki2() {
    int studentId = 6;
    StudentCourse expected = new StudentCourse(
        7, studentId, "Pythonデータ分析", "2025-02-10", "2025-05-10"
    );
    assertThat(studentCourseRepository.findStudentCoursesByStudentId(studentId))
        .contains(expected);
  }

}
