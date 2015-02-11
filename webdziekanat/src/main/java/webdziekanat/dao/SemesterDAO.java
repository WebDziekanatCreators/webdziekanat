package webdziekanat.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.interfaces.ISemesterDAO;
import webdziekanat.model.Semester;
import webdziekanat.model.Student;
import webdziekanat.model.Term;

@Component
@Transactional
public class SemesterDAO implements ISemesterDAO{
    
    private static final Logger logger = LogManager.getLogger(SemesterDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public SemesterDAO() {
        
    }

    public void addSemester(Semester semester) {
        try {
            entityManager.persist(semester);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteSemester(int id) {
        try {
            entityManager.remove(getSemesterById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

    }

    public void updateSemester(Semester semester) {
        try {
            entityManager.merge(semester);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Semester getSemesterById(int id) {

        Semester result = new Semester();

        result = entityManager.find(Semester.class, id);

        logger.info("Found [" + result.toString() + "]" + "with id: "
                + result.getId());

        return result;
    }
    
    public Semester getLastSemester(){
        
        List<Semester> semesters = getAll();
        Collections.sort(semesters, new Comparator<Semester>() {
            @Override public int compare(Semester s1, Semester s2) {
                return s1.getId() - s2.getId();
            }

        });
        
        return semesters.get(semesters.size() - 1);
    }

    @SuppressWarnings("unchecked")
    public List<Semester> getAll() {
        List<Semester> result = new ArrayList<Semester>();

        try {

            String hqlString = "Select semester from Semester semester";
            
            result = (List<Semester>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }

}
