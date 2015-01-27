package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.model.Course;
import webdziekanat.model.Course;

@ManagedBean(name="courseMB")
@RequestScoped
public class CourseManagedBean implements Serializable{

    private static final long serialVersionUID = 2880746693462123546L;
    private static final Logger logger = LogManager.getLogger(CourseManagedBean.class);
    private Map<String,String> counties;
    
    @ManagedProperty(value="#{courseDAO}")
    ICourseDAO courseDAO;
    
    Course course = new Course();
    
    List<Course> courses;
    
    boolean isAdd;
    boolean isEdit;
    
    public String startAdd(){
        course = new Course();
        isAdd = true;
        isEdit = false;
        return "/pages/addCourse.xhtml";
    }
    
    public String addCourse(){
        
        try {
            Course courseNew = new Course(course);
            courseDAO.addCourse(courseNew);
            isAdd = false;
            return "/success.xhtml";
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

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public String startEdit(Course src) {
        course = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addCourse.xhtml";
    }
    
    public String editCourse(Course src){
        courseDAO.updateCourse(src);
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
        return courses;
    }

    
    
}
