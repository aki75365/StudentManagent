package raisetech.Student.Management.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

  private String id;
  private String full_name;  // full_nameに変更
  private String furigana;    // furiganaに変更
  private String nickname;
  private String email;
  private String city;         // cityに変更
  private int age;
  private String gender;        // genderに変更
}
