package webdziekanat.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Mark {

    public Mark(Mark mark) {
        this.id = mark.id;
        this.subject = mark.subject;
        this.mark = mark.mark;
        this.isActive = mark.isActive;
        this.term = mark.term;
        this.student = mark.student;
    }
    
    public Mark(){
        this.subject = new Subjects();
        this.term = new Term();
        this.student = new Student();
        this.lecturers = new HashSet<Lecturer>();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @OneToOne
    private Subjects subject;
    
    @OneToOne
    private Term term;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="studentNumber")
    private Student student;
    
    private double mark;
    
    boolean isActive;
    
    @ManyToMany(mappedBy="marks")
    private Set<Lecturer> lecturers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }
    
}