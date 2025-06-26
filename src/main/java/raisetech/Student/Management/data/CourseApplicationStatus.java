package raisetech.Student.Management.data;

import lombok.Data;

@Data
public class CourseApplicationStatus {
  private int id;
  private int studentCourseId;
  private String status; // 例: "仮申込", "本申込", "受講中", "受講終了"
}
