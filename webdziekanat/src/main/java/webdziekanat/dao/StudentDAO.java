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
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.model.*;

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
        logger.info("addStudent");
        if (existingAddress != null) {
            student.setStudentAddress(existingAddress);
        }
        try {
            if(student.getCourses() == null || student.getCourses().isEmpty()){
                entityManager.persist(student);

            } else {
                Set<Course> temp = student.getCourses();
                student.setCourses(new HashSet<Course>());
                entityManager.persist(student);
                addStudentToCourses(student, temp);
            }
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    private void addStudentToCourses(Student student, Set<Course> crs) {
        Student stud = finder.findStudent(student);
        for(Course course : crs){
            try{
                course.getStudents().add(stud);
                entityManager.merge(course);
            } catch (Exception e){
                logger.error("Rollback addStudentToCourses() - " + e.getMessage());
            }
        }
    }

    private void deleteStudentFromCourses(Student student, Set<Course> crs) {
        for(Course course : crs){
            try{
                course.getStudents().remove(student);
                entityManager.merge(course);
            } catch (Exception e){
                logger.error("Rollback addStudentToCourses() - " + e.getMessage());
            }
        }
    }

    public boolean deleteStudent(int studentNumber) {
        try {
            Student student = getStudentByStudentNumber(studentNumber);
            deleteStudentFromCourses(student,student.getCourses());
            student.setCourses(null);
            entityManager.remove(getStudentByStudentNumber(studentNumber));
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }
    }

    public void updateStudent(Student student) {
        try {
            if(student.getCourses() == null || student.getCourses().isEmpty()){
                entityManager.merge(student);
            }
            else {
                Set<Course> temp = student.getCourses();
                student.setCourses(new HashSet<Course>());
                addStudentToCourses(student, temp);
                entityManager.merge(student);
            }
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Student getStudentByStudentNumber(int studentNumber) {

        Student result;

        result = entityManager.find(Student.class, studentNumber);

        logger.info("Found [" + result.toString() + "]" + "with name: " + result.getName() + " and id: "
                + result.getStudentNumber());

        return result;
    }

    public List<Student> getAll() {
        List<Student> result = new ArrayList<Student>();
        String hqlString = "Select student from Student student";
        result = (List<Student>) entityManager.createQuery(hqlString).getResultList();
        return result;
    }
    
    public List<Student> getAllForCourse(Course course){
        List<Student> result = new ArrayList<Student>();

        String hqlString = "select a.students from Course a where a.id=" + course.getId();
        result = (List<Student>) entityManager.createQuery(hqlString).getResultList();
        return result;
    }

}
