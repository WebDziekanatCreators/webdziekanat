package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Mark;

public interface IMarkDAO {
    public void addMark(Mark mark);
    
    public void deleteMark(int id);
    
    public void updateMark(Mark mark);
    
    public Mark getMarkById(int id);
    
    public List<Mark> getAll();
}
