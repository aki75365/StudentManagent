package raisetech.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import org.apache.ibatis.annotations.Options;

/**
 * 受講生情報を扱うリポジトリ
 */
@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE deleted_flag = false")
  List<Student> searchAllStudents();

  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<Student> findStudentsInTheir30s();

  @Select("""
      SELECT s.id AS studentId, s.full_name, c.course_name, c.start_date, c.end_date
      FROM students s
      JOIN student_course c ON s.id = c.student_id
      WHERE c.course_name = 'JAVA'
  """)
  List<StudentCourse> findJavaCourses();

  @Select("SELECT * FROM students WHERE id = #{id} AND deleted_flag = false")
  Student findStudentById(int id);

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

  @Insert("""
    INSERT INTO student_course (student_id, course_name, start_date, end_date)
    VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})
  """)
  void insertStudentCourse(StudentCourse studentCourse);

  @Insert("""
  INSERT INTO students (full_name, furigana, nickname, email, city, age, gender, remarks, deleted_flag)
  VALUES (#{fullName}, #{furigana}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remarks}, #{deletedFlag})
  """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Select("""
  SELECT * FROM students
  WHERE deleted_flag = false AND (
    full_name LIKE CONCAT('%', #{keyword}, '%') OR
    furigana LIKE CONCAT('%', #{keyword}, '%') OR
    nickname LIKE CONCAT('%', #{keyword}, '%') OR
    email LIKE CONCAT('%', #{keyword}, '%')
  )
  """)
  List<Student> searchStudentsByKeyword(@Param("keyword") String keyword);

  // 性別検索
  @Select("""
  SELECT * FROM students
  WHERE gender = #{gender} AND deleted_flag = false
  """)
  List<Student> findStudentsByGender(@Param("gender") String gender);

  //コース検索
  @Select("""
  SELECT s.id AS studentId, s.full_name, c.course_name, c.start_date, c.end_date
  FROM students s
  JOIN student_course c ON s.id = c.student_id
  WHERE c.course_name = #{courseName}
  """)
  List<StudentCourse> findStudentsByCourseName(@Param("courseName") String courseName);

}
