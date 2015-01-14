package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.model.Student;

@ManagedBean(name = "studentMB")
@SessionScoped
public class StudentManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4400141672079406344L;
    private static final Logger logger = LogManager.getLogger(StudentManagedBean.class);

    @ManagedProperty(value = "#{studentDAO}")
    IStudentDAO studentDAO;

    Student student = new Student();

    List<Student> list;

    boolean isAdd;
    boolean isEdit;

    public String startAdd(){
        student = new Student();
        isAdd = true;
        isEdit = false;
        return "/pages/addStudent.xhtml";
    }
    
    public String addStudent() {

        try {
            Student studentNew = new Student(student);
            studentDAO.addStudent(studentNew);
            isAdd = false;
            return "/pages/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Student: " + e.getMessage());
            e.printStackTrace();
            return "/pages/error.xhtml";
        }
    }

    public String deleteStudent(Student src) {
        logger.info(src.toString());
        if (studentDAO.deleteStudent(src.getId())) {
            return "/pages/success.xhtml";
        }
        return "";
    }
    
    public String startEdit(Student src) {
        student = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addStudent.xhtml";
    }
    
    public String editStudent(Student src){
        studentDAO.updateStudent(src);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    
    public List<Student> getList() {
        list = new ArrayList<Student>();
        list.addAll(studentDAO.getAll());
        return list;
    }

    public void setList(List<Student> list) {
        this.list = list;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public IStudentDAO getStudentDAO() {
        return studentDAO;
    }

    public void setStudentDAO(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

}
