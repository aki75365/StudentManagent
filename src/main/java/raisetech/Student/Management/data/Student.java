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

  // 引数なしのコンストラクタ
  public Student() {}

  // 引数付きのコンストラクタ (id, fullName)
  public Student(int id, String fullName) {
    this.id = id;
    this.fullName = fullName;
  }

  // ★ テスト用：すべての情報を設定するコンストラクタ（remarks, deletedFlag は除外）
  public Student(int id, String fullName, String furigana, String nickname,
      String email, String city, int age, String gender) {
    this.id = id;
    this.fullName = fullName;
    this.furigana = furigana;
    this.nickname = nickname;
    this.email = email;
    this.city = city;
    this.age = age;
    this.gender = gender;
  }

  public boolean isDeletedFlag() {
    return deletedFlag;
  }

  public void setDeletedFlag(boolean deletedFlag) {
    this.deletedFlag = deletedFlag;
  }
}
