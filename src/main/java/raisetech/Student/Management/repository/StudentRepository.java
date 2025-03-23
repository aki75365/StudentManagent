package raisetech.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

/**
 * 受講生情報を扱うリポジトリ
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
  @Select("""
      SELECT s.id AS studentId, s.full_name, c.course_name, c.start_date, c.end_date
      FROM students s
      JOIN student_course c ON s.id = c.student_id
      WHERE c.course_name = 'JAVA'
  """)
  List<StudentCourse> findJavaCourses();

  /**
   * 全受講生のコース情報を取得します。
   * @return 全受講生とそのコース情報の一覧
   */
  @Select("SELECT * FROM student_course")
  List<StudentCourse> findAllStudentCourses();

  /**
   * 新しい受講生を登録します。
   * @param student 追加する受講生情報
   */
  @Insert("""
    INSERT INTO students (full_name, furigana, nickname, email, city, age, gender, remarks, deleted_flag)
    VALUES (#{fullName}, #{furigana}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remarks}, false)
""")

  void insertStudent(Student student); // StudentDetail ではなく Student を受け取る

}
