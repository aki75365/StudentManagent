package raisetech.Student.Management;

import org.junit.jupiter.api.Test;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentConverterTest {

  private final StudentConverter converter = new StudentConverter();

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
