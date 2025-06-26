package raisetech.Student.Management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentApiController.class)
class Student2ApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService studentService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReturnAllStudents() throws Exception {
    Mockito.when(studentService.getAllStudents())
        .thenReturn(List.of(new Student(1, "山田太郎")));

    mockMvc.perform(get("/api/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].fullName", is("山田太郎")));
  }

  @Test
  void shouldReturnStudentDetail() throws Exception {
    StudentDetail detail = new StudentDetail();
    Student student = new Student();
    student.setId(1);
    student.setFullName("山田太郎");
    student.setFurigana("ヤマダタロウ");
    student.setNickname("タロー");
    student.setEmail("taro@example.com");
    student.setCity("東京");
    student.setAge(30);
    student.setGender("male");
    student.setDeletedFlag(false);
    detail.setStudent(student);

    StudentCourse course = new StudentCourse();
    course.setId(1);
    course.setStudentId(1);
    course.setCourseName("Javaコース");
    course.setStartDate("2025-01-01");
    course.setEndDate("2025-06-30");
    detail.addCourse(course);

    Mockito.when(studentService.getStudentDetail(1)).thenReturn(detail);

    mockMvc.perform(get("/api/students/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id", is(1)))
        .andExpect(jsonPath("$.student.fullName", is("山田太郎")))
        .andExpect(jsonPath("$.student.furigana", is("ヤマダタロウ")))
        .andExpect(jsonPath("$.student.nickname", is("タロー")))
        .andExpect(jsonPath("$.student.email", is("taro@example.com")))
        .andExpect(jsonPath("$.student.city", is("東京")))
        .andExpect(jsonPath("$.student.age", is(30)))
        .andExpect(jsonPath("$.student.gender", is("male")))
        .andExpect(jsonPath("$.student.deletedFlag", is(false)))
        .andExpect(jsonPath("$.studentCourseList[0].id", is(1)))
        .andExpect(jsonPath("$.studentCourseList[0].studentId", is(1)))
        .andExpect(jsonPath("$.studentCourseList[0].courseName", is("Javaコース")))
        .andExpect(jsonPath("$.studentCourseList[0].startDate", is("2025-01-01")))
        .andExpect(jsonPath("$.studentCourseList[0].endDate", is("2025-06-30")));
  }

  @Test
  void shouldReturnBadRequestWhenStudentIdIsNotNumber() throws Exception {
    mockMvc.perform(get("/api/students/abc"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldUpdateStudent() throws Exception {
    Student student = new Student();
    String studentJson = objectMapper.writeValueAsString(student);

    mockMvc.perform(put("/api/students/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(studentJson))
        .andExpect(status().isOk());
  }

  @Test
  void shouldCreateStudent() throws Exception {
    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student();
    student.setFullName("新規太郎");
    student.setGender("male");
    studentDetail.setStudent(student);
    String detailJson = objectMapper.writeValueAsString(studentDetail);

    mockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(detailJson))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldUpdateStudentCourses() throws Exception {
    List<StudentCourse> courses = List.of(new StudentCourse());
    String coursesJson = objectMapper.writeValueAsString(courses);

    mockMvc.perform(put("/api/students/1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(coursesJson))
        .andExpect(status().isOk());
  }

  @Test
  void shouldGetStudentCourses() throws Exception {
    Mockito.when(studentService.getCoursesByStudentId(1))
        .thenReturn(List.of(new StudentCourse()));

    mockMvc.perform(get("/api/students/1/courses"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldRegisterCourse() throws Exception {
    StudentCourse course = new StudentCourse();
    String courseJson = objectMapper.writeValueAsString(course);

    mockMvc.perform(post("/api/students/1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(courseJson))
        .andExpect(status().isCreated());
  }

  // ⑤ 性別で受講生検索のテストを追加
  @Test
  void shouldReturnStudentsByGender() throws Exception {
    Mockito.when(studentService.findStudentsByGender("Male"))
        .thenReturn(List.of(new Student()));

    mockMvc.perform(get("/api/students/gender")
            .param("gender", "Male"))
        .andExpect(status().isOk());
  }
//コンフリクト解消のためテスト２
}
