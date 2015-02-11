package webdziekanat.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Semester {

    public Semester(Semester semester) {
        this.id = semester.id;
        this.year = semester.year;
        this.terms = semester.terms;
        this.winterOrSummer = semester.winterOrSummer;
        this.universityName = semester.universityName;
        this.isActive = semester.isActive;
    }

    public Semester(){
        this.terms = new HashSet<Term>();
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @OneToMany
    private Set<Term> terms;
    
    private int year;
    
    private String winterOrSummer;
    
    private String universityName;
    
    private boolean isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Term> getTerms() {
        return terms;
    }

    public void setTerms(Set<Term> terms) {
        this.terms = terms;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getWinterOrSummer() {
        return winterOrSummer;
    }

    public void setWinterOrSummer(String winterOrSummer) {
        this.winterOrSummer = winterOrSummer;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
}
