package webdziekanat.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int studentNumber;
    
    private String name;
    
    @Column(name="last_name")
    private String lastName;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Address studentAddress;
    
    @ManyToOne
    @JoinColumn(name="group_id")
    private LearningGroup group;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentNumber")
    private Set<Mark> marks;
    
    @ManyToMany(cascade=CascadeType.ALL, mappedBy="students", fetch=FetchType.EAGER)
    private Set<Course> courses;
    
    public Student() {
        this.studentAddress = new Address();
        this.marks = new HashSet<Mark>();
    }
    
    public Student(Student student) {
        this.studentNumber = student.getStudentNumber();
        this.name = student.getName();
        this.lastName = student.getLastName();
        this.studentAddress = student.getStudentAddress();
        this.marks = student.getMarks();
        this.courses = student.getCourses();
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

    public Address getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(Address studentAddress) {
        this.studentAddress = studentAddress;
    }

    public Set<Course> getCourses() {
        return courses;
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

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

}
