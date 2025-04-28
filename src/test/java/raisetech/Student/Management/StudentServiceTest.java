package raisetech.Student.Management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.repository.StudentRepository;
import raisetech.Student.Management.service.StudentService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository; // モック化

  @InjectMocks
  private StudentService studentService; // 実際のサービスをインジェクト

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // モックオブジェクトの初期化
  }

  // searchStudent のテスト
  @Test
  void searchStudent_ShouldReturnStudentList() {
    // モックデータ作成
    Student student1 = new Student();
    student1.setId(1);
    student1.setFullName("Taro Yamada");
    student1.setAge(25);

    Student student2 = new Student();
    student2.setId(2);
    student2.setFullName("Jiro Tanaka");
    student2.setAge(28);

    // モックリポジトリが返す値を設定
    when(studentRepository.searchAllStudents()).thenReturn(List.of(student1, student2));

    // メソッド呼び出し
    List<Student> students = studentService.getAllStudents();

    // 検証
    assertNotNull(students);
    assertEquals(2, students.size());
    assertEquals("Taro Yamada", students.get(0).getFullName());
    assertEquals("Jiro Tanaka", students.get(1).getFullName());

    // リポジトリが1回呼び出されたことを確認
    verify(studentRepository, times(1)).searchAllStudents();
  }

  // registerStudent のテスト
  @Test
  void registerStudent_ShouldSaveNewStudent() {
    // 新規学生の作成
    Student student = new Student();
    student.setId(1);
    student.setFullName("Taro Yamada");
    student.setAge(25);

    // StudentDetail を作成し、Student を設定
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    // メソッド呼び出し
    studentService.registerNewStudent(studentDetail);

    // リポジトリが登録メソッドを1回呼び出すことを確認
    verify(studentRepository, times(1)).insertStudent(student);
  }


  // updateStudent のテスト
  @Test
  void updateStudent_ShouldUpdateStudentDetails() {
    // モックデータ作成
    Student existingStudent = new Student();
    existingStudent.setId(1);
    existingStudent.setFullName("Taro Yamada");
    existingStudent.setAge(25);

    // 更新する学生情報
    Student updatedStudent = new Student();
    updatedStudent.setId(1);
    updatedStudent.setFullName("Taro Yamada Updated");
    updatedStudent.setAge(26);

    // モックリポジトリが返す値を設定（学生が存在する場合）
    when(studentRepository.findStudentById(1)).thenReturn(existingStudent);

    // メソッド呼び出し
    studentService.updateStudentDetails(1, updatedStudent, false);

    // リポジトリが更新メソッドを1回呼び出すことを確認
    verify(studentRepository, times(1)).updateStudent(eq(1), eq(updatedStudent));

    // 更新後の学生情報を検証
    assertEquals("Taro Yamada Updated", updatedStudent.getFullName());
    assertEquals(26, updatedStudent.getAge());
  }

  @Test
  void updateStudent_ShouldMarkAsDeletedWhenCancelFlagIsTrue() {
    // モックデータ作成
    Student existingStudent = new Student();
    existingStudent.setId(1);
    existingStudent.setFullName("Taro Yamada");
    existingStudent.setAge(25);

    // 削除フラグを立てて更新する
    Student updatedStudent = new Student();
    updatedStudent.setId(1);
    updatedStudent.setFullName("Taro Yamada");
    updatedStudent.setAge(25);

    // モックリポジトリが返す値を設定（学生が存在する場合）
    when(studentRepository.findStudentById(1)).thenReturn(existingStudent);

    // メソッド呼び出し（キャンセルフラグあり）
    studentService.updateStudentDetails(1, updatedStudent, true);

    // リポジトリが更新メソッドを1回呼び出すことを確認
    verify(studentRepository, times(1)).updateStudent(eq(1), eq(updatedStudent));

    // 削除フラグが立っているか確認
    assertTrue(updatedStudent.isDeletedFlag());
  }
}
