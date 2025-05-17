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
class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Test
  void testSearchAllStudents() {
    List<Student> students = studentRepository.searchAllStudents();
    assertThat(students).isNotNull();
  }

  @Test
  void testFindStudentsInTheir30s() {
    List<Student> students = studentRepository.findStudentsInTheir30s();
    students.forEach(s -> assertThat(s.getAge()).isBetween(30, 39));
  }

  @Test
  void testFindJavaCourses() {
    List<StudentCourse> courses = studentRepository.findJavaCourses();
    courses.forEach(c -> assertThat(c.getCourseName()).isEqualTo("JAVA"));
  }

  @Test
  void testFindStudentById() {
    Student inserted = insertDummyStudent();
    Student student = studentRepository.findStudentById(inserted.getId());
    assertThat(student).isNotNull();
    assertThat(student.getId()).isEqualTo(inserted.getId());
  }

  @Test
  void testUpdateStudent() {
    Student student = insertDummyStudent();
    student.setNickname("Updated Nick");
    studentRepository.updateStudent(student.getId(), student);
    Student updated = studentRepository.findStudentById(student.getId());
    assertThat(updated.getNickname()).isEqualTo("Updated Nick");
  }

  @Test
  void testInsertStudentCourse() {
    Student student = insertDummyStudent();
    StudentCourse course = new StudentCourse();
    course.setStudentId(student.getId());
    course.setCourseName("JAVA");
    course.setStartDate(LocalDate.now().toString());   // 例: "2025-05-17"
    course.setEndDate(LocalDate.now().plusMonths(3).toString());
    studentRepository.insertStudentCourse(course);
    // 確認は StudentCourseRepository の findStudentCoursesByStudentId を使用
  }

  @Test
  void testInsertStudent() {
    Student student = insertDummyStudent();
    assertThat(student.getId()).isNotNull();
  }

  private Student insertDummyStudent() {
    Student student = new Student();
    student.setFullName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスティー");
    student.setEmail("test@example.com");
    student.setCity("東京");
    student.setAge(33);
    student.setGender("male");  // ここを「male」に変更
    student.setRemarks("備考内容");
    student.setDeletedFlag(false);
    studentRepository.insertStudent(student);
    return student;
  }
}
