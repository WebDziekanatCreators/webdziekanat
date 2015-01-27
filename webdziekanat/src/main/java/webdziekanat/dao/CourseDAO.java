package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.model.Course;

@Component
@Transactional
public class CourseDAO implements ICourseDAO {
    
    private static final Logger logger = LogManager.getLogger(CourseDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public CourseDAO() {
        
    }

    public void addCourse(Course course) {
        try {
            entityManager.persist(course);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteCourse(int id) {
        try {
            entityManager.remove(getCourseById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

    }

    public void updateCourse(Course course) {
        try {
            entityManager.merge(course);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Course getCourseById(int id) {

        Course result = new Course();

        result = entityManager.find(Course.class, id);

        logger.info("Found [" + result.toString() + "]" + "with id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Course> getAll() {
        List<Course> result = new ArrayList<Course>();

        try {

            String hqlString = "Select course from Course course";
            
            result = (List<Course>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }
}
