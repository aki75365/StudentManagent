package raisetech.Student.Management;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
  Student searchByName(String name);

  @Insert("INSERT INTO student(name, age) VALUES(#{name}, #{age})") // 🔹 カラム名を明示
  void registerStudent(String name, int age);

  @Update("UPDATE student SET age = #{age} WHERE name = #{name}") // 🔹 タイプミス修正
  void updateStudent(String name, int age);

  @Delete("DELETE FROM student WHERE name = #{name}") // 🔹 `#(name)` を `#{name}` に修正
  void deleteStudent(String name); // 🔹 メソッド名を `deleteStudent` に統一
}
