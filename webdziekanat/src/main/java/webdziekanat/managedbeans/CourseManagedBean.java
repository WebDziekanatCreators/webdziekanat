package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.model.Course;

@Component("courseMB")
@Scope("application")
public class CourseManagedBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7010484655540325484L;

    private static final Logger logger = LogManager.getLogger(CourseManagedBean.class);
    
    @Autowired
    ICourseDAO courseDAO;
    
    Course course = new Course();
    
    List<Course> courses;
    
    boolean isAdd;
    boolean isEdit;
    
    int startYear;
    Map<String, String> startTerms;
    String startTerm;
    
    
    public String startAdd(){
        course = new Course();
        isAdd = true;
        isEdit = false;
        return "/pages/addCourse.xhtml";
    }
    
    public String addCourse(){
        
        try {
            String startSemester = startYear + " - " + startTerm;
            course.setStartSemester(startSemester);
            Course courseNew = new Course(course);
            courseDAO.addCourse(courseNew);
            isAdd = false;
            return "/courses.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Course: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public ICourseDAO getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(ICourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public String startEdit(Course src) {
        course = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addCourse.xhtml";
    }

    public String showDetails(Course src){
        course = src;
        return "/pages/courseDetails.xhtml";
    }
    
    public String editCourse(){
        courseDAO.updateCourse(course);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Course> getList() {
        courses = new ArrayList<Course>();
        courses.addAll(courseDAO.getAll());
        return courses;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public Map<String, String> getStartTerms() {
        startTerms = new HashMap<String, String>();
        startTerms.put("Winter", "Winter");
        startTerms.put("Summer", "Summer");
        return startTerms;
    }

    public void setStartTerms(Map<String, String> startTerms) {
        this.startTerms = startTerms;
    }

    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    public void reload(ComponentSystemEvent event){
        courses = new ArrayList<Course>();
        courses.addAll(courseDAO.getAll());
    }
    
}
