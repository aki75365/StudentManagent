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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentApiController.class)
class StudentApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService studentService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReturnAllStudents() throws Exception {
    Mockito.when(studentService.getAllStudents())
        .thenReturn(List.of(new Student()));

    mockMvc.perform(get("/api/students"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnStudentDetail() throws Exception {
    StudentDetail detail = new StudentDetail();
    Mockito.when(studentService.getStudentDetail(1)).thenReturn(detail);

    mockMvc.perform(get("/api/students/1"))
        .andExpect(status().isOk());
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
