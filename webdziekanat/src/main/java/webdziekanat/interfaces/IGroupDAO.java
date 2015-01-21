package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Group;

public interface IGroupDAO {
    public void addGroup(Group group);
    
    public void deleteGroup(int id);
    
    public void updateGroup(Group group);
    
    public Group getGroupById(int id);
    
    public List<Group> getAll();
}
