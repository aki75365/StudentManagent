package raisetech.Student.Management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  // 受講生の全件検索メソッド
  @Select("SELECT * FROM students")
  List<Student> findAllStudents();



  default void printAllStudents() {
    List<Student> students = findAllStudents();
    students.forEach(student -> System.out.println(student));
  }



}
