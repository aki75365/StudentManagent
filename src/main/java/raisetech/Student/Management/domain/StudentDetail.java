package raisetech.Student.Management.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;

  // デフォルトコンストラクタ
  public StudentDetail() {
    this.student = new Student();
    this.studentCourseList = new ArrayList<>();
  }
}