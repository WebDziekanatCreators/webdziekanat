package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.ITermDAO;
import webdziekanat.model.Subjects;
import webdziekanat.model.Term;

@Component("termMB")
@Scope("application")
public class TermManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -883480192688558715L;
    

    private static final Logger logger = LogManager.getLogger(TermManagedBean.class);
    
    @Autowired
    ITermDAO termDAO;
    
    Term term = new Term();
    
    List<Term> terms;
    
    boolean isAdd;
    boolean isEdit;
    
    public String startAdd(){
        term = new Term();
        isAdd = true;
        isEdit = false;
        return "/pages/addTerm.xhtml";
    }
    
    public String addTerm(){
        
        try {
            Term termNew = new Term(term);
            termDAO.addTerm(termNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Term: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    
    public String startEdit(Term src) {
        term = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addTerm.xhtml";
    }
    
    public String editTerm(){
        termDAO.updateTerm(term);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    
    public String deleteTerm(Term src) {
        logger.info(src.toString());
        if (termDAO.deleteTerm(src.getId())) {
            return "/pages/success.xhtml";
        }
        return "";
    }

    public String showDetails(Term src){
        term = src;
        return "/pages/termDetails.xhtml";
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

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public ITermDAO getTermDAO() {
        return termDAO;
    }

    public void setTermDAO(ITermDAO termDAO) {
        this.termDAO = termDAO;
    }

    public List<Term> getTerms() {
        terms = new ArrayList<Term>();
        terms.addAll(termDAO.getAll());
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

}
