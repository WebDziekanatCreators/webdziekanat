package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import webdziekanat.Resources.Messages;
import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.ISubjectDAO;
import webdziekanat.model.Lecturer;
import webdziekanat.model.Subjects;

@ManagedBean(name = "subjectMB")
@ApplicationScoped
public class SubjectManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2386001858661911581L;
    private static final Logger logger = LogManager.getLogger(StudentManagedBean.class);

    @ManagedProperty(value = "#{subjectDAO}")
    ISubjectDAO subjectDAO;
    
    @Autowired
    private DatabaseFinder finder;

    Subjects subject = new Subjects();

    List<Subjects> list = new ArrayList<Subjects>();
    
    private DualListModel<Lecturer> lecturers;

    boolean isAdd;
    boolean isEdit;

    public String startAdd(){
        subject = new Subjects();
        isAdd = true;
        isEdit = false;
        return "/pages/addSubject.xhtml";
    }
    
    public String addSubject() {

        try {
            Subjects subjectNew = new Subjects(subject);
            subjectDAO.addSubject(subjectNew);
            isAdd = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.addSubjectsSuccess) );
            return "/pages/subjects.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Subject: " + e.getMessage());
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", Messages.addSubjectsFailure));
            return "/pages/subjects.xhtml";
        }
    }
    
    public String deleteSubject(Subjects src) {
        logger.info(src.toString());
        if (subjectDAO.deleteSubject(src.getId())) {
            return "/pages/subjects.xhtml";
        }
        return "";
    }
    
    public String startEdit(Subjects src) {
        subject = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addSubject.xhtml";
    }
    
    public String editSubject(){
        subjectDAO.updateSubject(subject);
        isEdit = false;
        return "/pages/subjects.xhtml";
    }
    
    public List<Subjects> getList() {
        list = new ArrayList<Subjects>();
        list.addAll(subjectDAO.getAll());
        return list;
    }

    public void setList(List<Subjects> list) {
        this.list = list;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public ISubjectDAO getSubjectDAO() {
        return subjectDAO;
    }

    public void setSubjectDAO(ISubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
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
