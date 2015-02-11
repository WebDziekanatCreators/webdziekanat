package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import webdziekanat.Resources.Messages;
import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.ISemesterDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.interfaces.ITermDAO;
import webdziekanat.model.Course;
import webdziekanat.model.Semester;
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
    
    @Autowired
    ISemesterDAO semesterDAO;
    
    private Semester semester = new Semester();
    
    public void startNewTerm(){
        Semester newSemester = new Semester();
        Semester oldSemester = semesterDAO.getLastSemester();
        if(oldSemester.getWinterOrSummer() == "Winter"){
            newSemester.setWinterOrSummer("Summer");
            newSemester.setYear(oldSemester.getYear() + 1);
        } else{
            newSemester.setWinterOrSummer("Winter");
            newSemester.setYear(oldSemester.getYear());
        }
        oldSemester.setActive(false);
        newSemester.setActive(true);
        newSemester.setUniversityName(oldSemester.getUniversityName());
        semesterDAO.updateSemester(oldSemester);
        semesterDAO.addSemester(newSemester);
        
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
                newTerm.setSemester(newSemester);
                termDAO.addTerm(newTerm);
                newSemester.getTerms().add(newTerm);
                course.getTerms().add(newTerm);
                courseDAO.updateCourse(course);
            }
        }
        semesterDAO.updateSemester(newSemester);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.startingNewSemesterSucceeded));
    }
    
    public String setup(){
        Semester semesterNew = new Semester(semester);
        semesterDAO.addSemester(semesterNew);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.systemConfigurationSucceeded));
        return "/pages/admin.xhtml";
    }
    
    public void addSemester(){
        
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

}
