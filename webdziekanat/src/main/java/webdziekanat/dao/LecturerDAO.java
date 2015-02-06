package webdziekanat.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
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
            if(lecturer.getSubjects().isEmpty()){
                entityManager.persist(lecturer);
            }
            else{

                Set<Subjects> temp = lecturer.getSubjects();
                lecturer.setSubjects(new HashSet<Subjects>());
                entityManager.persist(lecturer);
                addLecturerToSubjects(lecturer,temp);
            }
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public boolean deleteLecturer(int id) {
        try {
            Lecturer lecturer = getLecturerById(id);
            deleteLecturerFormSubject(lecturer,lecturer.getSubjects());
            lecturer.setSubjects(null);
            entityManager.remove(lecturer);
            return true;
        } catch (Exception e) {
            logger.error("Rollback deleteLecturer() - " + e.getMessage());
            return false;
        }
    }

    public void updateLecturer(Lecturer lecturer) {
        try {
            entityManager.merge(lecturer);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Lecturer getLecturerById(int id) {

        Lecturer result = new Lecturer();

        String hql = "Select lecturer from Lecturer lecturer where lecturer.id = :number";

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

    @Override
    public void addLecturerToSubjects(Lecturer lecturer, Set<Subjects> subs) {
        Lecturer lect = finder.findLecturer(lecturer);
        logger.info("addLecturerToSubjects() - " + lect.getId());
        for(Subjects subject : subs){
            try{
                subject.getLecturers().add(lect);
                entityManager.merge(subject);
            } catch (Exception e){
                logger.error("Rollback addLecturerToSubjects() - " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteLecturerFormSubject(Lecturer lecturer, Set<Subjects> subs) {
        //Lecturer lect = finder.findLecturer(lecturer);
        for(Subjects subject : subs){
            try{
                subject.getLecturers().remove(lecturer);
                entityManager.merge(subject);
            } catch (Exception e){
                logger.error("Rollback deleteLecturerToSubjects() - " + e.getMessage());
            }
        }
    }


}