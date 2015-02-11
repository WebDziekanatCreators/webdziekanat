package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Mark;
import webdziekanat.model.Student;
import webdziekanat.model.Subjects;
import webdziekanat.model.Term;

public interface IMarkDAO {
    public void addMark(Mark mark);
    
    public void deleteMark(int id);
    
    public void updateMark(Mark mark);
    
    public Mark getMarkById(int id);
    
    public Mark getMarkForSubject(Subjects subject, Term term, Student student);
    
    public List<Mark> getAll();
}
