package raisetech.Student.Management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String fullName;
  private String furigana;
  private String nickname;
  private String email;
  private String city;
  private int age;
  private String gender;
  private String remarks;
  private boolean deletedFlag; // 削除フラグ

  // isDeletedFlag メソッドを追加
  public boolean isDeletedFlag() {
    return deletedFlag;
  }

  // setDeletedFlag メソッドを追加（setter）
  public void setDeletedFlag(boolean deletedFlag) {
    this.deletedFlag = deletedFlag;
  }
}
