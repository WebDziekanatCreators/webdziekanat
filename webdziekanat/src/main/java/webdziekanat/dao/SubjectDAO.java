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
import webdziekanat.interfaces.ISubjectDAO;
import webdziekanat.model.Subjects;

@Component
@Transactional
public class SubjectDAO implements ISubjectDAO {
    
    private static final Logger logger = LogManager.getLogger(StudentDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseFinder finder;
    
    public SubjectDAO(){
        
    }
    
    public void addSubject(Subjects subject) {
        try {
            entityManager.persist(subject);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public boolean deleteSubject(int id) {
        try {
            entityManager.remove(getSubjectById(id));
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }
    }

    public void updateSubject(Subjects subject) {
        try {
            entityManager.merge(subject);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
        
    }

    public Subjects getSubjectById(int id) {
        Subjects result = new Subjects();
        
        result = entityManager.find(Subjects.class, id);

        logger.info("Found [" + result.toString() + "]" + "with name: " + result.getName() + "and id: "
                + result.getId());

        return result;
    }

    public List<Subjects> getAll() {
        List<Subjects> result = new ArrayList<Subjects>();
        String hqlString = "Select subjects from Subjects subjects";
        result = (List<Subjects>) entityManager.createQuery(hqlString).getResultList();
        return result;
    }
    
}
