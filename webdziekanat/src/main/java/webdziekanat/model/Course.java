package webdziekanat.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String startSemester;
    
    private String name;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<LearningGroup> groups;

    public Course(){
        this.groups = new HashSet<LearningGroup>();
    }
    
    public Course(Course course) {
        this.id = course.id;
        this.startSemester = course.startSemester;
        this.name = course.name;
        this.groups = course.groups;
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

}
