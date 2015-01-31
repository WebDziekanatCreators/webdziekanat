package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.interfaces.ITermDAO;
import webdziekanat.model.Course;
import webdziekanat.model.Term;

@Component("adminMB")
@Scope("application")
public class AdminManagedBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6883199685964638353L;
    private static final Logger logger = LogManager.getLogger(AdminManagedBean.class);
    
    @Autowired
    ICourseDAO courseDAO;
    
    @Autowired
    ITermDAO termDAO;
    
    @Autowired
    IStudentDAO studentDAO;
    
    public void startNewTerm(){
        List<Course> courses = new ArrayList<Course>();
        courses.addAll(courseDAO.getAll());
        List<Course> activeCourses = new ArrayList<Course>();
        for(Course course : courses){
            if(course.isActive() == false){
                continue;
            } else if(course.getTerms().size() >= 10){
                // If course has completed 10 semesters, deactivate it
                course.setActive(false);
                courseDAO.updateCourse(course);
            } else {
                Term newTerm = new Term();
                newTerm.setCourse(course);
                int termNumber = course.getTerms().size() + 1;
                newTerm.setNumber(termNumber);
                termDAO.addTerm(newTerm);
                course.getTerms().add(newTerm);
                courseDAO.updateCourse(course);
            }
        }
    }

}
