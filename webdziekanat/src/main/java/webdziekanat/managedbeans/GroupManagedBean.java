package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
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

import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.IGroupDAO;
import webdziekanat.model.Course;
import webdziekanat.model.LearningGroup;
import webdziekanat.model.Mark;
import webdziekanat.model.Subjects;

@Component("groupMB")
@Scope("application")
public class GroupManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1341528378914248228L;
    private static final Logger logger = LogManager.getLogger(GroupManagedBean.class);
    
    @Autowired
    IGroupDAO groupDAO;
    @Autowired
    ICourseDAO courseDAO;
    
    LearningGroup group = new LearningGroup();
    
    List<LearningGroup> groups;
    
    boolean isAdd;
    boolean isEdit;
    
    public String startAdd(){
        group = new LearningGroup();
        isAdd = true;
        isEdit = false;
        return "/pages/addGroup.xhtml";
    }
    
    public String addGroup(){
        
        try {
            LearningGroup groupNew = new LearningGroup(group);
            groupDAO.addGroup(groupNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Group: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    
    public String addNewGroup(Course course){
        try{
            int maxIndex = 0;
            groups = new ArrayList<LearningGroup>();
            groups.addAll(course.getGroups());
            for (LearningGroup group : groups) {
                if(group.getGroupNumber() > maxIndex)
                    maxIndex = group.getGroupNumber();
            }
            LearningGroup newGroup = new LearningGroup();
            newGroup.setGroupNumber(maxIndex+1);
            course.getGroups().add(newGroup);
            groupDAO.addGroup(newGroup);
            courseDAO.updateCourse(course);
            return "success.xhtml";
        }catch(DataAccessException e){
            return "/error.xhtml";
        }
    }
    
    public String startEdit(LearningGroup src) {
        group = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addGroup.xhtml";
    }
    
    public String editGroup(LearningGroup src){
        groupDAO.updateGroup(src);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    
    public String deleteGroup(LearningGroup src) {
        logger.info(src.toString());
        if (groupDAO.deleteGroup(src.getId())) {
            return "/pages/success.xhtml";
        }
        return "";
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

    public LearningGroup getGroup() {
        return group;
    }

    public void setGroup(LearningGroup group) {
        this.group = group;
    }

    public IGroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(IGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public List<LearningGroup> getGroups() {
        groups = new ArrayList<LearningGroup>();
        groups.addAll(groupDAO.getAll());
        return groups;
    }

    public void setGroups(List<LearningGroup> groups) {
        this.groups = groups;
    }
}
