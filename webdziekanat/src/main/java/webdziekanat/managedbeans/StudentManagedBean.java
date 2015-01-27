package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.model.Course;
import webdziekanat.model.Student;
import webdziekanat.model.Subjects;

@Component("studentMB")
@Scope("application")
public class StudentManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4400141672079406344L;
    private static final Logger logger = LogManager.getLogger(StudentManagedBean.class);

    @Autowired
    IStudentDAO studentDAO;
    
    @Autowired
    ICourseDAO courseDAO;

    Student student = new Student();
    
    private List<Course> courses;

    private List<Course> filteredCourses;
    
    private Map<Course, Boolean> checkMap = new HashMap<Course, Boolean>();

    List<Student> list;

    boolean isAdd;
    boolean isEdit;
    
    @PostConstruct
    public void init() {
        courses = courseDAO.getAll();
        for (Course course : courses) {
            checkMap.put(course, Boolean.FALSE);
        }
    }
    
    public void reload(ComponentSystemEvent event){
        init();
    }
    public String startAdd(){
        student = new Student();
        isAdd = true;
        isEdit = false;
        return "/pages/addStudent.xhtml";
    }
    
    public String addStudent() {

        try {
            Set<Course> result = new HashSet<Course>();
            for (Entry<Course, Boolean> entry : checkMap.entrySet()) {
                if(entry.getValue()){
                    result.add(entry.getKey());
                }
            }
            student.setCourse(result);
            Student studentNew = new Student(student);
            studentDAO.addStudent(studentNew);
            isAdd = false;
            return "/pages/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Student: " + e.getMessage());
            e.printStackTrace();
            return "/pages/error.xhtml";
        }
    }

    public String deleteStudent(Student src) {
        logger.info(src.toString());
        if (studentDAO.deleteStudent(src.getId())) {
            return "/pages/success.xhtml";
        }
        return "";
    }
    
    public String startEdit(Student src) {
        student = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addStudent.xhtml";
    }
    
    public String editStudent(Student src){
        studentDAO.updateStudent(src);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    
    public List<Student> getList() {
        list = new ArrayList<Student>();
        list.addAll(studentDAO.getAll());
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public IStudentDAO getStudentDAO() {
        return studentDAO;
    }

    public void setStudentDAO(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getFilteredCourses() {
        return filteredCourses;
    }

    public void setFilteredCourses(List<Course> filteredCourses) {
        this.filteredCourses = filteredCourses;
    }

    public ICourseDAO getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(ICourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Map<Course, Boolean> getCheckMap() {
        return checkMap;
    }

    public void setCheckMap(Map<Course, Boolean> checkMap) {
        this.checkMap = checkMap;
    }

}
