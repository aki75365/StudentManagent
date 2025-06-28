package raisetech.Student.Management;

import org.junit.jupiter.api.Test;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Student2ConverterTest {

  private final StudentConverter converter = new StudentConverter();

  //1人の受講生に複数のコースがある場合、正しく紐づけて StudentDetail に変換されるかを確認する。
  @Test
  public void testConvertStudentDetails_singleStudentWithCourses() {
    Student student = new Student();
    student.setId(1);
    student.setFullName("田中 太郎");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId(1);
    course1.setCourseName("Java入門");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId(1);
    course2.setCourseName("Spring基礎");

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        Arrays.asList(course1, course2)
    );

    assertEquals(1, result.size());
    StudentDetail detail = result.get(0);
    assertEquals(student, detail.getStudent());
    assertEquals(2, detail.getStudentCourseList().size());
  }

  //複数の受講生がいる場合でも、それぞれの受講生に正しく対応するコースが紐づくかを確認する。
  @Test
  public void testConvertStudentDetails_multipleStudents() {
    Student student1 = new Student();
    student1.setId(1);
    student1.setFullName("佐藤 花子");

    Student student2 = new Student();
    student2.setId(2);
    student2.setFullName("鈴木 一郎");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId(1);
    course1.setCourseName("Java");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId(2);
    course2.setCourseName("Python");

    List<StudentDetail> result = converter.convertStudentDetails(
        Arrays.asList(student1, student2),
        Arrays.asList(course1, course2)
    );

    assertEquals(2, result.size());
    assertEquals(1, result.get(0).getStudentCourseList().size());
    assertEquals("Java", result.get(0).getStudentCourseList().get(0).getCourseName());
  }

  //受講生には存在しない studentId のコースがあった場合、該当の受講生にはコースが紐づかないことを確認する。
  @Test
  public void testConvertStudentDetails_noMatchingCourses() {
    Student student = new Student();
    student.setId(1);
    student.setFullName("高橋 次郎");

    StudentCourse unrelatedCourse = new StudentCourse();
    unrelatedCourse.setStudentId(999);
    unrelatedCourse.setCourseName("C++");

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        List.of(unrelatedCourse)
    );

    assertEquals(1, result.size());
    assertTrue(result.get(0).getStudentCourseList().isEmpty());
  }

  //空のリストを渡した場合でも、例外が発生せず、空の結果が返るかを確認する。
  @Test
  public void testConvertStudentDetails_emptyInput() {
    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(),
        List.of()
    );

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
