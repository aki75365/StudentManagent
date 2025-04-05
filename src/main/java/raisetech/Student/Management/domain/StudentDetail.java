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
  private List<StudentCourse> studentCourseList = new ArrayList<>();

  // デフォルトコンストラクタ
  public StudentDetail() {
    this.student = new Student();
    this.studentCourseList.add(new StudentCourse());
  }
}

//DeletedFlag重複させないためStudentクラスのみに修正
//public void setDeletedFlag(boolean deletedFlag) {
//    this.deletedFlag = deletedFlag;
//    if (student != null) {
//      student.setDeletedFlag(deletedFlag);  // student内の削除フラグを同期
//    }
//  }
//
//  // deletedFlagのゲッターを追加
//  public boolean isDeletedFlag() {
//    return deletedFlag;
//  }
//}


//  // 名前のゲッター
//  public String getFullName() {
//    return student != null ? student.getFullName() : null;
//  }
//
//  public String getFurigana() {
//    return student != null ? student.getFurigana() : null;
//  }
//
//  public String getNickname() {
//    return student != null ? student.getNickname() : null;
//  }
//
//  public String getEmail() {
//    return student != null ? student.getEmail() : null;
//  }
//
//  public String getCity() {
//    return student != null ? student.getCity() : null;
//  }
//
//  public int getAge() {
//    return student != null ? student.getAge() : 0;
//  }
//
//  public String getGender() {
//    return student != null ? student.getGender() : null;
//  }
//
//  public String getRemarks() {
//    return student != null ? student.getRemarks() : null;
//  }
//
//  public boolean isDeletedFlag() {
//    return student != null && student.isDeletedFlag();
//  }
//}
//
