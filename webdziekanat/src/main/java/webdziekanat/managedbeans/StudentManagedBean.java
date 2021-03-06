package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.Resources.Messages;
import webdziekanat.enums.Role;
import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.interfaces.IUserDAO;
import webdziekanat.model.Course;
import webdziekanat.model.Student;
import webdziekanat.model.User;

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

    @Autowired
    IUserDAO userDAO;

    @Autowired
    DatabaseFinder finder;

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
        checkMap = new HashMap<Course, Boolean>();
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
            for (Entry<Course, Boolean> entry : checkMap.entrySet()) {
                if(entry.getValue()){
                    student.addCourse(entry.getKey());
                }
            }
            studentDAO.addStudent(student);

            User user = new User();
            user.setStudent(student);
            user.getRoles().add(Role.STUDENT);
            userDAO.addUser(user);

            isAdd = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.addStudentSuccess));
            return "/pages/students.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Student: " + e.getMessage());
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", Messages.addStudentFailure));
            return "/pages/students.xhtml";
        }
    }

    public String deleteStudent(Student src) {
        logger.info(src.toString());

        User example = new User();
        example.setStudent(src);
        example.setUsername(String.valueOf(src.getStudentNumber()));

        User foundUser = finder.findUser(example);

        if(foundUser != null){
            userDAO.deleteUser(foundUser.getId());
        }

        if (studentDAO.deleteStudent(src.getStudentNumber())) {
            return "/pages/students.xhtml";
        }
        return "";
    }
    
    public String startEdit(Student src) {
        student = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addStudent.xhtml";
    }
    
    public String editStudent(){
        for (Entry<Course, Boolean> entry : checkMap.entrySet()) {
            if(entry.getValue()){
                student.addCourse(entry.getKey());
            }
        }
        studentDAO.updateStudent(student);
        isEdit = false;
        return "/pages/addStudent.xhtml";
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

    public DatabaseFinder getFinder() {
        return finder;
    }

    public void setFinder(DatabaseFinder finder) {
        this.finder = finder;
    }
}
