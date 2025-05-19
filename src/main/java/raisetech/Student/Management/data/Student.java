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

  // ★ テスト用：すべての情報を設定するコンストラクタ
  public Student(int id, String fullName, String furigana, String nickname,
      String email, String city, int age, String gender, String remarks, boolean deletedFlag) {
    this.id = id;
    this.fullName = fullName;
    this.furigana = furigana;
    this.nickname = nickname;
    this.email = email;
    this.city = city;
    this.age = age;
    this.gender = gender;
    this.remarks = remarks;
    this.deletedFlag = deletedFlag;
  }

  // remarks, deletedFlag を省いたテスト用コンストラクタ（必要ならつける）
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    return id == student.id &&
        age == student.age &&
        deletedFlag == student.deletedFlag &&
        fullName.equals(student.fullName) &&
        furigana.equals(student.furigana) &&
        nickname.equals(student.nickname) &&
        email.equals(student.email) &&
        city.equals(student.city) &&
        gender.equals(student.gender) &&
        remarks.equals(student.remarks);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, fullName, furigana, nickname, email, city, age, gender, remarks, deletedFlag);
  }

}
