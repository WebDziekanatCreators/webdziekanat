package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.IGroupDAO;
import webdziekanat.interfaces.ISemesterDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.interfaces.ITermDAO;
import webdziekanat.model.Course;
import webdziekanat.model.LearningGroup;
import webdziekanat.model.Semester;
import webdziekanat.model.Student;
import webdziekanat.model.Term;

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
    
    @Autowired
    IStudentDAO studentDAO;
    
    @Autowired
    IGroupDAO groupDAO;
    
    @Autowired
    ISemesterDAO semesterDAO;
    
    @Autowired
    ITermDAO termDAO;
    
    Course course = new Course();
    
    List<Course> courses;
    
    boolean isAdd;
    boolean isEdit;
    
    int startYear;
    Map<String, String> startTerms;
    String startTerm;
    
    private List<Student> filteredStudents = new ArrayList<Student>();
    
    int studentsInGroup = 0;
    
    @PostConstruct
    public void init() {
        courses = new ArrayList<Course>();
        courses.addAll(courseDAO.getAll());
        
    }
    
    public void reload(ComponentSystemEvent event){
        init();
        filteredStudents = new ArrayList<Student>();
    }
    
    public void detailsReload(ComponentSystemEvent event){
        showDetails(course);
    }
        
    public String startAdd(){
        course = new Course();
        isAdd = true;
        isEdit = false;
        return "/pages/addCourse.xhtml";
    }
    
    public String addCourse(){
        
        try {
            Semester currentSemester = semesterDAO.getLastSemester();
            String startSemester = currentSemester.getYear() + " - " + currentSemester.getWinterOrSummer();
            course.setStartSemester(startSemester);
            Course courseNew = new Course(course);

            courseDAO.addCourse(courseNew);
            
            Term term = new Term();
            term.setAverage(0.0);
            term.setCourse(courseNew);
            term.setNumber(1);
            term.setSemester(semesterDAO.getLastSemester());
            termDAO.addTerm(term);
            
            courseNew.getTerms().add(term);
            courseDAO.updateCourse(courseNew);
            
            isAdd = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.addCourseSuccess) );
            return "/pages/courses.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Course: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", Messages.addCourseFailure));
            e.printStackTrace();
            return "/pages/courses.xhtml"; 
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
        return "/pages/courses.xhtml";
    }
    
    public String createGroups(){
        int groupNumber = course.getGroups().size() + 1;
        Set<Student> studentsForGroupSet = new HashSet<Student>();
        for (Student student : course.getStudents()) {
            if(student.getGroup() != null)
                continue;
            studentsForGroupSet.add(student);
            if(studentsForGroupSet.size() < studentsInGroup)
                continue;
            else{
                addGroupForCourse(groupNumber, studentsForGroupSet);
                groupNumber++;
                studentsForGroupSet = new HashSet<Student>();
            }
        }
        if(studentsForGroupSet.size() > 0){
            addGroupForCourse(groupNumber, studentsForGroupSet);
        }
        courseDAO.updateCourse(course);
        return "/pages/courseDetails.xhtml";
    }
    
    private void addGroupForCourse(int groupNumber, Set<Student> studentsForGroup){
        LearningGroup group = new LearningGroup();
        group.setGroupNumber(groupNumber);
        group.setStudents(studentsForGroup);
        group.setCourse(course);
        groupDAO.addGroup(group);
        for(Student student : studentsForGroup){
            student.setGroup(group);
            studentDAO.updateStudent(student);
        }
        course.getGroups().add(group);
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }

    public int getStudentsInGroup() {
        return studentsInGroup;
    }

    public void setStudentsInGroup(int studentsInGroup) {
        this.studentsInGroup = studentsInGroup;
    }
    
}
