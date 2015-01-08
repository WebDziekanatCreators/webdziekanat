package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Student;

public interface IStudentDAO {
    
    public void addStudent(Student student);
    
    public void deleteStudent(int id);
    
    public void updateStudent(Student student);
    
    public Student getStudentById(int id);
    
    public List<Student> getAll();
}
