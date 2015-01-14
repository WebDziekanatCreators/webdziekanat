package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Subjects;

public interface ISubjectDAO {
    public void addSubject(Subjects subject);
    
    public boolean deleteSubject(int id);
    
    public void updateSubject(Subjects subject);
    
    public Subjects getSubjectById(int id);
    
    public List<Subjects> getAll();
}
