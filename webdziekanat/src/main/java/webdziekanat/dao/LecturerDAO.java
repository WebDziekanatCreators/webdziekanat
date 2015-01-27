package webdziekanat.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.ILecturerDAO;
import webdziekanat.model.Lecturer;
import webdziekanat.model.Subjects;

@Component
@Transactional
public class LecturerDAO implements ILecturerDAO{
    
    private static final Logger logger = LogManager.getLogger(LecturerDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private DatabaseFinder finder;
    
    public LecturerDAO() {
        
    }

    public void addLecturer(Lecturer lecturer) {
        try {
            Set<Subjects> result = new HashSet<Subjects>();
            for (Subjects subject : lecturer.getSubjects()) {
                Subjects foundSubject = finder.findSubject(subject);
                if(foundSubject != null){
                    result.add(foundSubject);
                }
            }
            lecturer.setSubjects(result);
            entityManager.persist(lecturer);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    @Override
    public boolean deleteLecturer(int id) {
        try {
            entityManager.remove(getLecturerById(id));
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }

    }

    public void updateLecturer(Lecturer lecturer) {
        try {
            Lecturer foundLecturer = entityManager.find(Lecturer.class, lecturer.getId());
            foundLecturer = lecturer;
            entityManager.merge(foundLecturer);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Lecturer getLecturerById(int id) {

        Lecturer result = new Lecturer();

        String hql = "Select lecturer from Lecturer webdziekanat where lecturer.id = :number";

        result = (Lecturer) entityManager.createQuery(hql).setParameter("number", id).getSingleResult();

        logger.info("Found [" + result.toString() + "]" + "with id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Lecturer> getAll() {
        List<Lecturer> result = new ArrayList<Lecturer>();

        try {

            String hqlString = "Select lecturer from Lecturer lecturer";
            
            result = (List<Lecturer>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }


}