package raisetech.Student.Management.repository;

import raisetech.Student.Management.data.CourseApplicationStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseApplicationStatusRepository {

  @Select("SELECT * FROM course_application_status WHERE id = #{id}")
  CourseApplicationStatus findById(int id);

  @Select("SELECT * FROM course_application_status WHERE student_course_id = #{studentCourseId}")
  List<CourseApplicationStatus> findByStudentCourseId(int studentCourseId);

  @Insert("INSERT INTO course_application_status (student_course_id, status) VALUES (#{studentCourseId}, #{status})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStatus(CourseApplicationStatus status);

  @Update("UPDATE course_application_status SET status = #{status} WHERE id = #{id}")
  void updateStatus(CourseApplicationStatus status);

  @Delete("DELETE FROM course_application_status WHERE id = #{id}")
  void deleteStatus(int id);

  @Select("SELECT * FROM course_application_status")
  List<CourseApplicationStatus> findAll();
}
