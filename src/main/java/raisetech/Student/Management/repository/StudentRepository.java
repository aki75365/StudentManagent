package raisetech.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

/**
 * 受講生情報を扱うリポジトリ
 *
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> searchAllStudents();

  /**
   * 30代の受講生を検索します。
   * @return 30代の受講生情報の一覧
   */
  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<Student> findStudentsInTheir30s();

  /**
   * JAVAコースの受講生を検索します。
   * @return JAVAコースを受講する受講生情報の一覧
   */
  @Select("SELECT * FROM student_management WHERE courseName = 'JAVA'")
  List<StudentCourse> findJavaCourses();

  /**
   * 全件を標準出力します。
   */

}
