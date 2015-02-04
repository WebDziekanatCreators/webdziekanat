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
import webdziekanat.interfaces.IMarkDAO;
import webdziekanat.model.Mark;
import webdziekanat.model.Student;

@Component("markMB")
@Scope("application")
public class MarkManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9209614458615343840L;
    private static final Logger logger = LogManager.getLogger(MarkManagedBean.class);
    
    @Autowired
    IMarkDAO markDAO;
    
    private Mark mark = new Mark();
    
    private Student student = new Student();
    
    List<Mark> marks;
    
    boolean isAdd;
    boolean isEdit;
    
    public String marksForStudent(Student src){
        this.student = src;
        return "/pages/studentMarks.xhtml";
    }
    
    public String startAdd(){
        mark = new Mark();
        isAdd = true;
        isEdit = false;
        return "/pages/addMark.xhtml";
    }
    
    public String addMark(){
        
        try {
            Mark markNew = new Mark(mark);
            markDAO.addMark(markNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Mark: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    
    public String startEdit(Mark src) {
        mark = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addMark.xhtml";
    }
    
    public String editMark(){
        markDAO.updateMark(mark);
        isEdit = false;
        return "/pages/success.xhtml";
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public List<Mark> getList() {
        return marks;
    }

    public void setList(List<Mark> list) {
        this.marks = list;
    }

    public IMarkDAO getMarkDAO() {
        return markDAO;
    }

    public void setMarkDAO(IMarkDAO markDAO) {
        this.markDAO = markDAO;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

}
