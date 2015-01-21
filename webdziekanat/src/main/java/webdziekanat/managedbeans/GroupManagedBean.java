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
    
    List<Group> groupes;
    
    public String addGroup(){
        
        try {
            Group groupNew = new Group(group);
            groupDAO.addGroup(groupNew);
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Group: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Group> getList() {
        return groupes;
    }

    public void setList(List<Group> list) {
        this.groupes = list;
    }

    public IGroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(IGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }
}
