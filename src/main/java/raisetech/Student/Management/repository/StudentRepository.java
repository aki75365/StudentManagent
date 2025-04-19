package raisetech.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  @Select("SELECT * FROM students WHERE deleted_flag = false")
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
  void insertStudent(Student student);

  /**
   * 受講生IDを指定して受講生情報を取得します。
   * @param id 受講生ID
   * @return 指定されたIDの受講生情報
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  /**
   * 受講生情報を更新します。
   * @param id 現在の受講生ID
   * @param student 更新する受講生情報
   */
  @Update("""
  UPDATE students
  SET full_name = #{student.fullName},
      furigana = #{student.furigana},
      nickname = #{student.nickname},
      email = #{student.email},
      city = #{student.city},
      age = #{student.age},
      gender = #{student.gender},
      remarks = #{student.remarks},
      deleted_flag = #{student.deletedFlag}
  WHERE id = #{id}
  """)
  void updateStudent(@Param("id") int id, @Param("student") Student student);

  /**
   * 受講生コース情報を更新します。
   * @param studentCourse 更新する受講生のコース情報
   */
  @Update("""
    UPDATE student_course
    SET course_name = #{courseName},
        start_date = #{startDate},
        end_date = #{endDate}
    WHERE student_id = #{studentId}
  """)
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コース情報を新規登録します。
   * @param studentCourse 新規登録する受講生のコース情報
   */
  @Insert("""
    INSERT INTO student_course (student_id, course_name, start_date, end_date)
    VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})
  """)
  void insertStudentCourse(StudentCourse studentCourse);
}
