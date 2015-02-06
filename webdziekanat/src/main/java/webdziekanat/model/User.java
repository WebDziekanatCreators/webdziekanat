package webdziekanat.model;

import org.apache.commons.lang3.RandomStringUtils;
import webdziekanat.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 04.02.2015.
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String username;

    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Student student;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Lecturer lecturer;

    @ElementCollection(targetClass=Role.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_roles", joinColumns={@JoinColumn(name="user_id")})
    @Column(name="role")
    private List<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<Role> getRoles() {
        if(this.roles == null){
            this.roles = new ArrayList<Role>();
        }
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean hasRole(Role role){
        return roles.contains(role);
    }

    public void generatePassword(){
        this.password = RandomStringUtils.randomAlphanumeric(10);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
