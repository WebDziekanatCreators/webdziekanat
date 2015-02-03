package webdziekanat.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    @Column(name="last_name")
    private String lastName;
    
    @Column(name="student_number")
    private String studentNumber;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Address studentAddress;
    
    @ManyToOne
    @JoinColumn(name="group_id")
    private LearningGroup group;
    
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Set<Mark> marks;
    
    @ManyToMany(cascade=CascadeType.ALL, mappedBy="students", fetch=FetchType.EAGER)
    private Set<Course> courses;
    
    public Student() {
        this.studentAddress = new Address();
        this.marks = new HashSet<Mark>();
    }
    
    public Student(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.lastName = student.getLastName();
        this.studentNumber = student.getStudentNumber();
        this.studentAddress = student.getStudentAddress();
        this.marks = student.getMarks();
        this.courses = student.getCourses();
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Address getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(Address studentAddress) {
        this.studentAddress = studentAddress;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourse(Set<Course> courses) {
        this.courses = courses;
    }
    
    public void addCourse(Course course){
        if(courses == null){
            courses = new HashSet<Course>();
        }
        courses.add(course);
    }

    public LearningGroup getGroup() {
        return group;
    }

    public void setGroup(LearningGroup group) {
        this.group = group;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    
}
