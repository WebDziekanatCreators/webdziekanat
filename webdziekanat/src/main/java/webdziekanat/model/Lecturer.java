package webdziekanat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Lecturer {

    public Lecturer(Lecturer lecturer) {
        this.id = lecturer.id;
        this.name = lecturer.name;
        this.lastName = lecturer.lastName;
        this.mail = lecturer.mail;
        this.subjects = lecturer.subjects;
    }
    
    public Lecturer(){
        
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    private String lastName;
    
    private String mail;
    
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy="lecturers", fetch = FetchType.EAGER)
    private Set<Subjects> subjects;

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
    
}
