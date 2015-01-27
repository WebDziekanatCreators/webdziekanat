package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import webdziekanat.interfaces.IGroupDAO;
import webdziekanat.model.Group;
import webdziekanat.model.Mark;

@ManagedBean(name="groupMB")
@RequestScoped
public class GroupManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1341528378914248228L;
    private static final Logger logger = LogManager.getLogger(GroupManagedBean.class);
    
    @ManagedProperty(value="#{groupDAO}")
    IGroupDAO groupDAO;
    
    Group group = new Group();
    
    List<Group> groups;
    
    boolean isAdd;
    boolean isEdit;
    
    public String startAdd(){
        group = new Group();
        isAdd = true;
        isEdit = false;
        return "/pages/addGroup.xhtml";
    }
    
    public String addGroup(){
        
        try {
            Group groupNew = new Group(group);
            groupDAO.addGroup(groupNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Group: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    
    public String startEdit(Group src) {
        group = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addGroup.xhtml";
    }
    
    public String editGroup(Group src){
        groupDAO.updateGroup(src);
        isEdit = false;
        return "/pages/success.xhtml";
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Group> getList() {
        return groups;
    }

    public void setList(List<Group> list) {
        this.groups = list;
    }

    public IGroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(IGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }
}
