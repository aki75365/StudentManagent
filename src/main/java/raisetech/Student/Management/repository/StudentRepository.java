package raisetech.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

/**
 * 受講生情報を扱うリポジトリ
 */
@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchAllStudents();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudentById(String id);  // <-- メソッド名を変更

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

  @Select("SELECT * FROM students_courses WHERE id = #{id}")
  StudentCourse searchStudentCourseById(String id);  // <-- メソッド名を変更

  @Insert("""
    INSERT INTO students (full_name, furigana, nickname, email, city, age, gender, remarks, deleted_flag)
    VALUES (#{fullName}, #{furigana}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remarks}, 0)
  """)
  void insertStudent(Student student);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  /**
   * 受講生情報を更新します。
   * @param student 更新する受講生情報
   */
  @Update("""
    UPDATE students 
    SET full_name = #{fullName}, 
        furigana = #{furigana}, 
        nickname = #{nickname}, 
        email = #{email}, 
        city = #{city}, 
        age = #{age}, 
        gender = #{gender}, 
        remarks = #{remarks} 
    WHERE id = #{id}
  """)
  void updateStudent(Student student);

  /**
   * 受講生のコース情報を更新します。
   * @param studentCourse 更新する受講生のコース情報
   */
  @Update("""
    UPDATE student_course 
    SET course_name = #{courseName}, 
        start_date = #{startDate}, 
        end_date = #{endDate} 
    WHERE id = #{id}
  """)
  void updateStudentCourse(StudentCourse studentCourse);
}
