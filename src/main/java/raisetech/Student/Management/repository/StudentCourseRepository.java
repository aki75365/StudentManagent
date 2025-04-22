package raisetech.Student.Management.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.Student.Management.data.StudentCourse;
import java.util.List;

/**
 * 受講生コース情報を扱うリポジトリ
 */
@Mapper
public interface StudentCourseRepository {

  /**
   * 受講生IDに関連するコース情報を全て取得します。
   * @param studentId 受講生ID
   * @return 受講生のコース情報のリスト
   */
  @Select("SELECT * FROM student_course WHERE student_id = #{studentId}")
  List<StudentCourse> findStudentCoursesByStudentId(int studentId);

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

  @Select("SELECT * FROM student_course")
  List<StudentCourse> findAllStudentCourses();
}
