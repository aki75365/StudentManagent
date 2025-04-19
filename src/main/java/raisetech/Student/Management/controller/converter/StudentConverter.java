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
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourse> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student student : students) {
      List<StudentCourse> convertStudentCourses = studentCourses.stream()
          .filter(studentCourse -> student.getId() == studentCourse.getStudentId())

          .collect(Collectors.toList());
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setStudentCourseList(convertStudentCourses); // 修正
      studentDetails.add(studentDetail);
    }
    return studentDetails; // セミコロンで終了
  }
}

