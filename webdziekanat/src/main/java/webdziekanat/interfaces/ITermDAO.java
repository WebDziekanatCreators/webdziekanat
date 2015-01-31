package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Term;

public interface ITermDAO {
    public void addTerm(Term term);
    
    public boolean deleteTerm(int id);
    
    public void updateTerm(Term term);
    
    public Term getTermById(int id);
    
    public List<Term> getAll();
}
