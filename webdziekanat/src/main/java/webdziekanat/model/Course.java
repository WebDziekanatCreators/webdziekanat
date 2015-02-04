package webdziekanat.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String startSemester;
    
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Set<LearningGroup> groups;
    
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "course_students", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;
    
    @Transient
    private List<Student> studentsList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Set<Term> terms;
    
    private boolean isActive;

    public Course(){
        this.groups = new HashSet<LearningGroup>();
        this.students = new HashSet<Student>();
        this.terms = new HashSet<Term>();
        isActive = true;
    }
    
    public Course(Course course) {
        this.id = course.id;
        this.startSemester = course.getStartSemester();
        this.name = course.getName();
        this.groups = course.getGroups();
        this.students = course.getStudents();
        this.terms = course.getTerms();
        this.isActive = course.isActive();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartSemester() {
        return startSemester;
    }

    public void setStartSemester(String startSemester) {
        this.startSemester = startSemester;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LearningGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<LearningGroup> groups) {
        this.groups = groups;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Term> getTerms() {
        return terms;
    }

    public void setTerms(Set<Term> terms) {
        this.terms = terms;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Student> getStudentsList() {
        if(studentsList == null){
            studentsList = new ArrayList<Student>();
            studentsList.addAll(students);
        }
        
        return studentsList;
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

}
