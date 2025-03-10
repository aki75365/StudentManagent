package raisetech.Student.Management.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

  private String id;
  private String full_name;  // データベースと一致
  private String furigana;
  private String nickname;
  private String email;
  private String city;
  private int age;
  private String gender;
  private String remarks;      // 備考を"remarks"に修正
  private boolean deleted_flag; // 削除フラグを"deleted_flag"に修正
}
