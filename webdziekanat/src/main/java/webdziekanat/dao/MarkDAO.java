package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.interfaces.IMarkDAO;
import webdziekanat.model.Mark;
import webdziekanat.model.Student;
import webdziekanat.model.Subjects;
import webdziekanat.model.Term;

@Component
@Transactional
public class MarkDAO implements IMarkDAO{
    
    private static final Logger logger = LogManager.getLogger(MarkDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public MarkDAO() {
        
    }

    public void addMark(Mark mark) {
        try {
            entityManager.persist(mark);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteMark(int id) {
        try {
            entityManager.remove(getMarkById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

    }

    public void updateMark(Mark mark) {
        try {
            entityManager.merge(mark);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Mark getMarkById(int id) {

        Mark result = new Mark();

        result = entityManager.find(Mark.class, id);

        logger.info("Found [" + result.toString() + "]" + "with id: "
                + result.getId());

        return result;
    }

    public Mark getMarkForSubject(Subjects subject, Term term, Student student){
        Mark resultMark = null;
        List<Mark> allMarks = new ArrayList<Mark>();
        for(Mark mark : allMarks){
            if(mark.getSubject() == subject && mark.getTerm() == term && mark.getStudent() == student){
                resultMark = mark;
                break;
            }
        }
        return resultMark;
    }
    @SuppressWarnings("unchecked")
    public List<Mark> getAll() {
        List<Mark> result = new ArrayList<Mark>();

        try {

            String hqlString = "Select mark from Mark mark";
            
            result = (List<Mark>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }
}
