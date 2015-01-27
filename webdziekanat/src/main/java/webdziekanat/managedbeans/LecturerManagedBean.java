package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.ILecturerDAO;
import webdziekanat.interfaces.ISubjectDAO;
import webdziekanat.model.Lecturer;
import webdziekanat.model.Subjects;

@Component("lecturerMB")
@Scope("application")
public class LecturerManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4741255386760250882L;
    private static final Logger logger = LogManager.getLogger(LecturerManagedBean.class);

    // @ManagedProperty(value="#{lecturerDAO}")
    @Autowired
    private ILecturerDAO lecturerDAO;

    @Autowired
    private ISubjectDAO subjectDAO;
    
    private Lecturer lecturer = new Lecturer();

    private List<Lecturer> list;
    
    private List<Subjects> subjects;

    private List<Subjects> filteredSubjects;

    private Map<Subjects, Boolean> checkMap = new HashMap<Subjects, Boolean>();

    private boolean isAdd;
    private boolean isEdit;

    @PostConstruct
    public void init() {
        subjects = subjectDAO.getAll();
        for (Subjects subject : subjects) {
            checkMap.put(subject, Boolean.FALSE);
        }
    }
    
    public void reload(ComponentSystemEvent event){
        init();
    }
    
    public String processCheckbox(){
        Set<Subjects> result = new HashSet<Subjects>();
        for (Entry<Subjects, Boolean> entry : checkMap.entrySet()) {
            if(entry.getValue()){
                result.add(entry.getKey());
            }
        }
        
        lecturer.setSubjects(result);
        
        return "/pages/addLecturer.xhtml";
        
    }
    
    public String startAdd() {
        lecturer = new Lecturer();
        isAdd = true;
        isEdit = false;
        return "/pages/addLecturer.xhtml";
    }

    public String addLecturer() {

        try {
            Lecturer lecturerNew = new Lecturer(lecturer);
            lecturerDAO.addLecturer(lecturerNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Lecturer: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml";
        }
    }

    public String deleteLecturer(Lecturer src) {
        logger.info(src.toString());
        if (lecturerDAO.deleteLecturer(src.getId())) {
            return "/pages/success.xhtml";
        }
        return "";
    }

    public String startEdit(Lecturer src) {
        lecturer = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addLecturer.xhtml";
    }

    public String editLecturer() {
        lecturerDAO.updateLecturer(lecturer);
        isEdit = false;
        return "/pages/success.xhtml";
    }

    public String addSubjects(Lecturer src) {
        lecturer = src;
        return "/pages/findSubject.xhtml";
    }

    public List<Lecturer> getList() {
        list = new ArrayList<Lecturer>();
        list.addAll(lecturerDAO.getAll());
        return list;
    }

    public void setList(List<Lecturer> list) {
        this.list = list;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public ILecturerDAO getLecturerDAO() {
        return lecturerDAO;
    }

    public void setLecturerDAO(ILecturerDAO lecturerDAO) {
        this.lecturerDAO = lecturerDAO;
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

    public List<Subjects> getFilteredSubjects() {
        return filteredSubjects;
    }

    public void setFilteredSubjects(List<Subjects> filteredSubjects) {
        this.filteredSubjects = filteredSubjects;
    }

    public Map<Subjects, Boolean> getCheckMap() {
        return checkMap;
    }

    public void setCheckMap(Map<Subjects, Boolean> checkMap) {
        this.checkMap = checkMap;
    }

    public ISubjectDAO getSubjectDAO() {
        return subjectDAO;
    }

    public void setSubjectDAO(ISubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }
    
    

}
