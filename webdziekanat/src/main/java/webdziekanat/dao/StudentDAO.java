package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.model.Address;
import webdziekanat.model.Student;

@Component
@Transactional
public class StudentDAO implements IStudentDAO {

    private static final Logger logger = LogManager.getLogger(StudentDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseFinder finder;

    public StudentDAO() {

    }

    public void addStudent(Student student) {
        Address existingAddress = finder.findAddress(student.getStudentAddress());

        if (existingAddress != null) {
            student.setStudentAddress(existingAddress);
        }

        try {
            entityManager.persist(student);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public boolean deleteStudent(int id) {
        try {
            entityManager.remove(getStudentById(id));
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }

    }

    public void updateStudent(Student student) {
        try {
            entityManager.merge(student);
            
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Student getStudentById(int id) {

        Student result = new Student();
        
        result = entityManager.find(Student.class, id);

        logger.info("Found [" + result.toString() + "]" + "with name: " + result.getName() + "and id: "
                + result.getId());

        return result;
    }

    public List<Student> getAll() {
        List<Student> result = new ArrayList<Student>();
        String hqlString = "Select student from Student student";
        result = (List<Student>) entityManager.createQuery(hqlString).getResultList();
        return result;
    }

}
