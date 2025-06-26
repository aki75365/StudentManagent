package raisetech.Student.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.data.CourseApplicationStatus;
import raisetech.Student.Management.repository.CourseApplicationStatusRepository;

import java.util.List;

@Service
public class CourseApplicationStatusService {

  @Autowired
  private CourseApplicationStatusRepository repository;

  public CourseApplicationStatus getStatusById(int id) {
    return repository.findById(id);
  }

  public List<CourseApplicationStatus> getStatusesByStudentCourseId(int studentCourseId) {
    return repository.findByStudentCourseId(studentCourseId);
  }

  public List<CourseApplicationStatus> getAllStatuses() {
    return repository.findAll();
  }

  public void addStatus(CourseApplicationStatus status) {
    repository.insertStatus(status);
  }

  public void updateStatus(CourseApplicationStatus status) {
    repository.updateStatus(status);
  }

  public void deleteStatus(int id) {
    repository.deleteStatus(id);
  }
}
