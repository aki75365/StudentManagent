package raisetech.Student.Management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String fullName;  // データベースと一致
  private String furigana;
  private String nickname;
  private String email;
  private String city;
  private int age;
  private String gender;
  private String remarks;     // 備考を"remarks"に修正
  private boolean deletedFlag; // 削除フラグを"deleted_flag"に修正
}
