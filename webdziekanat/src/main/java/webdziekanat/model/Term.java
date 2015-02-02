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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int number;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy="terms", fetch = FetchType.EAGER)
    private Set<Subjects> subjects;
    

    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy="terms", fetch = FetchType.EAGER)
    private Set<Lecturer> lecturers;
    
    @Transient
    private List<Subjects> subjectsList;
    
    @Transient
    private List<Lecturer> lecturersList;
    
    public Term(){
        subjects = new HashSet<Subjects>();
        lecturers = new HashSet<Lecturer>();
    }
    
    public Term(Term term){
        this.id = term.getId();
        this.number = term.getNumber();
        this.course = term.getCourse();
        this.subjects = term.getSubjects();
        this.lecturers = term.getLecturers();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subjects> subjects) {
        this.subjects = subjects;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setSubjectsList(List<Subjects> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public void setLecturersList(List<Lecturer> lecturersList) {
        this.lecturersList = lecturersList;
    }
    
    public List<Subjects> getSubjectsList(){
        if(subjectsList == null){
            subjectsList = new ArrayList<Subjects>();
            subjectsList.addAll(subjects);
        }
        return subjectsList;
    }

    public List<Lecturer> getLecturersList(){
        if(lecturersList == null){
            lecturersList = new ArrayList<Lecturer>();
            lecturersList.addAll(lecturers);
        }
        return lecturersList;
    }

}
