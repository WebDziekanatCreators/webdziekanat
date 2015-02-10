package webdziekanat.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.IUserDAO;
import webdziekanat.model.Lecturer;
import webdziekanat.model.Student;
import webdziekanat.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub on 04.02.2015.
 */
@Component
@Transactional
public class UserDAO implements IUserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DatabaseFinder finder;

    public UserDAO() {
    }

    @Override
    public void addUser(User user) {
        logger.info("addUser");

        user.generatePassword();

        Student foundStudent = null;
        Lecturer foundLecturer = null;
        if(user.getStudent() != null){
            foundStudent = finder.findStudent(user.getStudent());
        }
        if(user.getLecturer() != null){
            foundLecturer = finder.findLecturer(user.getLecturer());
        }
        if(foundStudent != null){
            user.setStudent(foundStudent);
            user.setUsername(String.valueOf(foundStudent.getStudentNumber()));
            try {
                entityManager.persist(user);
            } catch (Exception e) {
                logger.error("Rollback - " + e.getMessage());
            }
        }
        else if(foundLecturer != null){
            user.setLecturer(foundLecturer);
            user.setUsername(String.valueOf(foundLecturer.getMail()));
            try {
                entityManager.persist(user);
            } catch (Exception e) {
                logger.error("Rollback - " + e.getMessage());
            }
        }else{
            try {
                entityManager.persist(user);
            } catch (Exception e) {
                logger.error("Rollback - " + e.getMessage());
            }
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            User user = getUserById(id);
            entityManager.remove(user);
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            entityManager.merge(user);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    @Override
    public User getUserById(int id) {
        User result;

        result = entityManager.find(User.class,id);

        logger.info("Found [" + result.toString() + "]" + "with username: " + result.getUsername() + " and id: "
                + result.getId());

        return result;
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        User result = null;
        try{
            String replaced = username.replace("@", "\\@");
            String hqlString = "select user from User user where user.username  = '" + replaced +  "' and user.password = " + password;
            result = (User) entityManager.createQuery(hqlString).getSingleResult();
        } catch(Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

        return result;
    }


    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<User>();

        String hqlString = "select user from User user";
        result = (List<User>) entityManager.createQuery(hqlString).getResultList();
        return result;
    }
}
