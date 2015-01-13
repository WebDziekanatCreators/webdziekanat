package webdziekanat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="students")
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
    
    @ManyToMany(cascade=CascadeType.ALL, mappedBy="students")
    private Set<Subjets> subjects;
    
    public Student() {
        this.studentAddress = new Address();
    }
    
    public Student(Student student) {
        
        this.name = student.getName();
        this.lastName = student.getLastName();
        this.studentNumber = student.getStudentNumber();
        this.studentAddress = student.getStudentAddress();
        
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

    public Set<Subjets> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subjets> subjects) {
        this.subjects = subjects;
    }
    
}
