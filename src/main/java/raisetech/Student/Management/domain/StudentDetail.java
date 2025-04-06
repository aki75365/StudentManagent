package raisetech.Student.Management.domain;

import lombok.Getter;
import lombok.Setter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

import java.util.List;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourseList;
  private boolean deletedFlag; // 削除フラグを追加


}
