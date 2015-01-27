package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Course;

public interface ICourseDAO {
    public void addCourse(Course course);
    
    public void deleteCourse(int id);
    
    public void updateCourse(Course course);
    
    public Course getCourseById(int id);
    
    public List<Course> getAll();
}
