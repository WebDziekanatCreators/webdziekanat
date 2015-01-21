package webdziekanat.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Semester {

    public Semester(Semester semester) {
        this.id = semester.id;
        this.marks = semester.marks;
        this.number = semester.number;
        this.description = semester.description;
    }

    public Semester(){
        
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @OneToMany(cascade=CascadeType.PERSIST)
    private Set<Mark> marks;
    
    private int number;
    
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
