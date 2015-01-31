package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.ITermDAO;
import webdziekanat.model.Term;

@Component
@Transactional
public class TermDAO implements ITermDAO {

    private static final Logger logger = LogManager.getLogger(StudentDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseFinder finder;
    
    public TermDAO(){
        
    }
    
    public void addTerm(Term term) {
        try {
            entityManager.persist(term);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public boolean deleteTerm(int id) {
        try {
            entityManager.remove(getTermById(id));
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }
    }

    public void updateTerm(Term term) {
        try {
            entityManager.merge(term);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
        
    }

    public Term getTermById(int id) {
        Term result = new Term();
        
        result = entityManager.find(Term.class, id);

        logger.info("Found [" + result.toString() + "]" + "with name: " + result.getNumber() + "and id: "
                + result.getId());

        return result;
    }

    public List<Term> getAll() {
        List<Term> result = new ArrayList<Term>();
        String hqlString = "Select term from Term term";
        result = (List<Term>) entityManager.createQuery(hqlString).getResultList();
        return result;
    }
}
