package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webdziekanat.interfaces.ILecturerDAO;
import webdziekanat.model.Lecturer;

public class LecturerDAO implements ILecturerDAO{
    
    private static final Logger logger = LogManager.getLogger(LecturerDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public LecturerDAO() {
        
    }

    public void addLecturer(Lecturer lecturer) {
        try {
            entityManager.persist(lecturer);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteLecturer(int id) {
        try {
            entityManager.remove(getLecturerById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
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

            String hqlString = "Select lecturer from Lecturer webdziekanat";
            
            result = (List<Lecturer>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }


}