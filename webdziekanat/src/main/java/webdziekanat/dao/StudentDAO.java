package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.model.Student;

@Component
@Transactional
public class StudentDAO implements IStudentDAO {

    private static final Logger logger = LogManager.getLogger(StudentDAO.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    public StudentDAO() {
        
    }

    public void addStudent(Student student) {
        try {
            entityManager.persist(student);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteStudent(int id) {
        try {
            entityManager.remove(getStudentById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

    }

    public void updateStudent(Student student) {
        try {
            Student foundStudent = entityManager.find(Student.class, student.getId());
            foundStudent = student;
            entityManager.merge(foundStudent);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Student getStudentById(int id) {

        Student result = new Student();

        String hql = "Select user from User webdziekanat where user.id = :number";

        result = (Student) entityManager.createQuery(hql).setParameter("number", id).getSingleResult();

        logger.info("Found [" + result.toString() + "]" + "with name: " + result.getName() + "and id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Student> getAll() {
        List<Student> result = new ArrayList<Student>();

        try {

            String hqlString = "Select student from Student webdziekanat";
            
            result = (List<Student>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }

}
