package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Component;
import webdziekanat.interfaces.ISemesterDAO;
import webdziekanat.model.Semester;

@Component("semesterMB")
@Scope("request")
public class SemesterManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7721007864549442424L;
    private static final Logger logger = LogManager.getLogger(SemesterManagedBean.class);
    
    @Autowired
    ISemesterDAO semesterDAO;
    
    Semester semester = new Semester();
    
    List<Semester> semesters;

    boolean isAdd;
    boolean isEdit;
    
    public String startAdd(){
        semester = new Semester();
        isAdd = true;
        isEdit = false;
        return "/pages/addSemester.xhtml";
    }
    
    public String startEdit(Semester src) {
        semester = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addSemester.xhtml";
    }
    
    public String editSemester(){
        semesterDAO.updateSemester(semester);
        isEdit = false;
        return "/pages/success.xhtml";
    }

    public String addSemester(){
        
        try {
            Semester semesterNew = new Semester(semester);
            semesterDAO.addSemester(semesterNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Semester: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Semester> getList() {
        return semesters;
    }

    public void setList(List<Semester> list) {
        this.semesters = list;
    }

    public ISemesterDAO getSemesterDAO() {
        return semesterDAO;
    }

    public void setSemesterDAO(ISemesterDAO semesterDAO) {
        this.semesterDAO = semesterDAO;
    }

}
