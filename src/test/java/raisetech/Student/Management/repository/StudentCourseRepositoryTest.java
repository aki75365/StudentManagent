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
  void testFindStudentCoursesByStudentId() {
    Student student = insertDummyStudent();
    StudentCourse course = insertDummyCourse(student.getId());

    List<StudentCourse> courses = studentCourseRepository.findStudentCoursesByStudentId(
        student.getId());
    assertThat(courses).isNotEmpty();
  }

  @Test
  void testUpdateStudentCourse() {
    Student student = insertDummyStudent();
    StudentCourse course = insertDummyCourse(student.getId());
    course.setCourseName("Spring");
    studentCourseRepository.updateStudentCourse(course);

    List<StudentCourse> updated = studentCourseRepository.findStudentCoursesByStudentId(
        student.getId());
    assertThat(updated.get(0).getCourseName()).isEqualTo("Spring");
  }

  @Test
  void testInsertStudentCourse() {
    Student student = insertDummyStudent();
    StudentCourse course = insertDummyCourse(student.getId());

    List<StudentCourse> found = studentCourseRepository.findStudentCoursesByStudentId(
        student.getId());
    assertThat(found).isNotEmpty();
  }

  @Test
  void testFindAllStudentCourses() {
    List<StudentCourse> courses = studentCourseRepository.findAllStudentCourses();
    assertThat(courses).isNotNull();
  }

  private Student insertDummyStudent() {
    Student student = new Student();
    student.setFullName("テスト太郎");
    student.setFurigana("テストタロウ");
    student.setNickname("テスティー");
    student.setEmail("test@example.com");
    student.setCity("東京");
    student.setAge(33);
    student.setGender("Male"); // ← ENUMに合わせて修正
    student.setRemarks("備考内容");
    student.setDeletedFlag(false);
    studentRepository.insertStudent(student);
    return student;
  }


  private StudentCourse insertDummyCourse(int studentId) {
    StudentCourse course = new StudentCourse();
    course.setStudentId(studentId);
    course.setCourseName("JAVA");
    course.setStartDate(LocalDate.now().toString());
    course.setEndDate(LocalDate.now().plusMonths(2).toString());
    studentCourseRepository.insertStudentCourse(course);
    return course;
  }
}
