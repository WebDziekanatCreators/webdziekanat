package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Course;
import webdziekanat.model.Student;

public interface IStudentDAO {
    
    public void addStudent(Student student);
    
    public boolean deleteStudent(int studentNumber);
    
    public void updateStudent(Student student);
    
    public Student getStudentByStudentNumber(int studentNumber);
    
    public List<Student> getAll();
    
    public List<Student> getAllForCourse(Course course);
}
