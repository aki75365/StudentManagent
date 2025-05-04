package raisetech.Student.Management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.exception.StudentNotFoundException;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.repository.StudentCourseRepository;
import raisetech.Student.Management.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private StudentCourseRepository studentCourseRepository;

  @InjectMocks
  private StudentService studentService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllStudents_ShouldReturnAllStudents() {
    List<Student> mockStudents = Arrays.asList(new Student(), new Student());
    when(studentRepository.searchAllStudents()).thenReturn(mockStudents);

    List<Student> result = studentService.getAllStudents();

    assertEquals(2, result.size());
    verify(studentRepository, times(1)).searchAllStudents();
  }

  @Test
  void getAllStudentCourses_ShouldReturnAllCourses() {
    List<StudentCourse> mockCourses = Arrays.asList(new StudentCourse(), new StudentCourse());
    when(studentCourseRepository.findAllStudentCourses()).thenReturn(mockCourses);

    List<StudentCourse> result = studentService.getAllStudentCourses();

    assertEquals(2, result.size());
    verify(studentCourseRepository, times(1)).findAllStudentCourses();
  }

  @Test
  void getCoursesByStudentId_ShouldReturnCoursesForStudent() {
    int studentId = 1;
    List<StudentCourse> mockCourses = Arrays.asList(new StudentCourse(), new StudentCourse());
    when(studentCourseRepository.findStudentCoursesByStudentId(studentId)).thenReturn(mockCourses);

    List<StudentCourse> result = studentService.getCoursesByStudentId(studentId);

    assertEquals(2, result.size());
    verify(studentCourseRepository, times(1)).findStudentCoursesByStudentId(studentId);
  }

  @Test
  void getStudentDetail_ShouldReturnStudentDetail_WhenStudentExists() {
    int studentId = 1;
    Student mockStudent = new Student();
    mockStudent.setId(studentId);

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId(studentId);
    StudentCourse course2 = new StudentCourse();
    course2.setStudentId(2); // 別の学生用

    when(studentRepository.findStudentById(studentId)).thenReturn(mockStudent);
    when(studentCourseRepository.findAllStudentCourses()).thenReturn(Arrays.asList(course1, course2));

    StudentDetail result = studentService.getStudentDetail(studentId);

    assertEquals(mockStudent, result.getStudent());
    assertEquals(1, result.getStudentCourseList().size());
    assertEquals(studentId, result.getStudentCourseList().get(0).getStudentId());

    verify(studentRepository, times(1)).findStudentById(studentId);
    verify(studentCourseRepository, times(1)).findAllStudentCourses();
  }

  @Test
  void getStudentDetail_ShouldThrowException_WhenStudentNotFound() {
    int studentId = 1;
    when(studentRepository.findStudentById(studentId)).thenReturn(null);

    assertThrows(StudentNotFoundException.class, () -> studentService.getStudentDetail(studentId));

    verify(studentRepository, times(1)).findStudentById(studentId);
  }

  @Test
  void updateStudentDetails_SingleArg_ShouldCallUpdateStudent() {
    Student mockStudent = new Student();
    mockStudent.setId(1);

    studentService.updateStudentDetails(mockStudent);

    verify(studentRepository, times(1)).updateStudent(1, mockStudent);
  }

  @Test
  void updateStudentDetails_ThreeArgs_ShouldCallUpdateStudent_WithDeletedFlagTrue() {
    int id = 1;
    Student mockStudent = new Student();

    studentService.updateStudentDetails(id, mockStudent, true);

    assertEquals(true, mockStudent.isDeletedFlag());
    verify(studentRepository, times(1)).updateStudent(id, mockStudent);
  }

  @Test
  void updateStudentCourses_ShouldUpdateEachCourse() {
    int studentId = 1;
    StudentCourse course1 = new StudentCourse();
    StudentCourse course2 = new StudentCourse();
    List<StudentCourse> courses = Arrays.asList(course1, course2);

    studentService.updateStudentCourses(studentId, courses);

    assertEquals(studentId, course1.getStudentId());
    assertEquals(studentId, course2.getStudentId());
    verify(studentCourseRepository, times(1)).updateStudentCourse(course1);
    verify(studentCourseRepository, times(1)).updateStudentCourse(course2);
  }

  @Test
  void updateSingleStudentCourse_ShouldCallUpdateStudentCourse() {
    StudentCourse course = new StudentCourse();

    studentService.updateSingleStudentCourse(course);

    verify(studentCourseRepository, times(1)).updateStudentCourse(course);
  }

  @Test
  void registerNewStudent_ShouldInsertStudent_WhenStudentExists() {
    Student student = new Student();
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    studentService.registerNewStudent(detail);

    verify(studentRepository, times(1)).insertStudent(student);
  }

  @Test
  void registerNewStudent_ShouldThrowException_WhenStudentIsNull() {
    StudentDetail detail = new StudentDetail();
    detail.setStudent(null);

    assertThrows(IllegalArgumentException.class, () -> studentService.registerNewStudent(detail));

    verify(studentRepository, never()).insertStudent(any());
  }

  @Test
  void registerNewStudentCourse_ShouldInsertStudentCourse() {
    StudentCourse course = new StudentCourse();

    studentService.registerNewStudentCourse(course);

    verify(studentCourseRepository, times(1)).insertStudentCourse(course);
  }
}
