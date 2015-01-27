package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.LearningGroup;

public interface IGroupDAO {
    public void addGroup(LearningGroup group);
    
    public boolean deleteGroup(int id);
    
    public void updateGroup(LearningGroup group);
    
    public LearningGroup getGroupById(int id);
    
    public List<LearningGroup> getAll();
}
