package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import webdziekanat.dao.StudentDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.model.Student;

@ManagedBean(name="studentMB")
@RequestScoped
public class StudentManagedBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4400141672079406344L;
    private static final Logger logger = LogManager.getLogger(StudentManagedBean.class);
    
    @Autowired
    @ManagedProperty(value="#{StudentDAO}")
    IStudentDAO studentDAO;
    
    Student student = new Student();
    
    List<Student> list;
    
    public String addStudent(){
        
        try {
            Student studentNew = new Student(student);
            getStudentDAO().addStudent(studentNew);
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Student: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml";
        }
    }

    public IStudentDAO getStudentDAO() {
        return studentDAO;
    }

    public void setStudentDAO(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getList() {
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }
    
}
