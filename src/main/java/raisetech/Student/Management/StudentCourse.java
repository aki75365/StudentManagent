package raisetech.Student.Management;
public class StudentCourse {
  private int id;               // コースID
  private int studentId;         // 受講生ID
  private String courseName;     // コース名
  private String startDate;      // 受講開始日
  private String endDate;        // 受講終了予定日

  // コンストラクタ
  public StudentCourse(int id, int studentId, String courseName, String startDate, String endDate) {
    this.id = id;
    this.studentId = studentId;
    this.courseName = courseName;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  // ゲッターとセッター
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  // データ確認用のtoStringメソッド
  @Override
  public String toString() {
    return "StudentCourse{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", courseName='" + courseName + '\'' +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        '}';
  }
}
