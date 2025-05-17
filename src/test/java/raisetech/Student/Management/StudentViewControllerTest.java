package raisetech.Student.Management;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import raisetech.Student.Management.controller.StudentViewController;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.service.StudentService;

@WebMvcTest(StudentViewController.class)
public class StudentViewControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService studentService;

  @Test
  public void testGetStudentListView() throws Exception {
    List<Student> students = Arrays.asList(
        new Student(1, "田中 太郎", "タナカ タロウ", "タロちゃん", "taro@example.com", "東京", 25, "男性"),
        new Student(2, "佐藤 花子", "サトウ ハナコ", "ハナちゃん", "hana@example.com", "大阪", 22, "女性")
    );

    when(studentService.getAllStudents()).thenReturn(students);

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(view().name("studentList")) // View名
        .andExpect(model().attribute("studentList", students)); // モデル属性
  }

  @Test
  public void testViewStudentDetailView() throws Exception {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student(1, "田中 太郎", "タナカ タロウ", "タロちゃん", "taro@example.com", "東京", 25, "男性"));

    when(studentService.getStudentDetail(1)).thenReturn(studentDetail);

    mockMvc.perform(get("/1")) // 学生IDによる詳細表示
        .andExpect(status().isOk())
        .andExpect(view().name("studentDetail")) // View名
        .andExpect(model().attribute("studentDetail", studentDetail)); // モデル属性
  }
}
