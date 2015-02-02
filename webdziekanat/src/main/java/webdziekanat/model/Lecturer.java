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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Lecturer {

    public Lecturer(Lecturer lecturer) {
        this.id = lecturer.id;
        this.name = lecturer.name;
        this.lastName = lecturer.lastName;
        this.mail = lecturer.mail;
        this.subjects = lecturer.subjects;
        this.terms = lecturer.terms;
    }
    
    public Lecturer(){
        this.terms = new HashSet<Term>();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    private String lastName;
    
    private String mail;
    
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy="lecturers", fetch = FetchType.EAGER)
    private Set<Subjects> subjects;
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "lecturers_terms", joinColumns = @JoinColumn(name = "lecturer_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id"))
    private Set<Term> terms;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subjects> subjects) {
        this.subjects = subjects;
    }

    public Set<Term> getTerms() {
        return terms;
    }

    public void setTerms(Set<Term> terms) {
        this.terms = terms;
    }
    
}
