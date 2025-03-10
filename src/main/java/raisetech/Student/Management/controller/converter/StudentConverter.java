package raisetech.Student.Management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

@Component
public class StudentConverter {

  // メソッド名の変更が必要
  public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentCourse> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student student : students) {
      List<StudentCourse> convertStudentCourses = studentCourses.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId())) // 修正
          .collect(Collectors.toList());
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setStudentCourseList(convertStudentCourses); // 修正
      studentDetails.add(studentDetail);
    }
    return studentDetails; // セミコロンで終了
  }


//  // students と studentCourses を StudentDetail に変換するメソッド
//  public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentCourse> studentCourses) {
//    return students.stream().map(student -> {
//      // 学生ごとのコース情報をフィルタリング
//      List<StudentCourse> studentCoursesForStudent = studentCourses.stream()
//          .filter(course -> course.getStudentId().equals(student.getId()))
//          .collect(Collectors.toList());
//
//      // StudentDetail に変換
//      StudentDetail studentDetail = new StudentDetail();
//      studentDetail.setStudent(student);  // 学生情報をセット
//      studentDetail.setStudentCourseList(studentCoursesForStudent);  // コース情報をセット
//      return studentDetail;
//    }).collect(Collectors.toList());  // List にまとめて返す
//  }

}
