package webdziekanat.finders;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Component;

import webdziekanat.model.*;

@Component
@Transactional
public class DatabaseFinder {

    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseFinder() {
    }

    public Student findStudent(Student src) {
        Session session = (Session) entityManager.getDelegate();

        Example addressExample = Example.create(src);

        Criteria criteria = session.createCriteria(Student.class).add(addressExample);

        Student foundStudent = (Student) criteria.uniqueResult();

        return foundStudent;
    }

    public Address findAddress(Address src) {
        Session session = (Session) entityManager.getDelegate();

        Example addressExample = Example.create(src);

        Criteria criteria = session.createCriteria(Address.class).add(addressExample);

        Address foundAddress = (Address) criteria.uniqueResult();

        return foundAddress;
    }
    
    public Subjects findSubject(Subjects src) {
        Session session = (Session) entityManager.getDelegate();

        Example addressExample = Example.create(src);

        Criteria criteria = session.createCriteria(Subjects.class).add(addressExample);

        Subjects foundSubject = (Subjects) criteria.uniqueResult();

        return foundSubject;
    }

    public Course findCourse(Course src) {
        Session session = (Session) entityManager.getDelegate();

        Example courseExample = Example.create(src);

        Criteria criteria = session.createCriteria(Course.class).add(courseExample);

        Course foundCourse = (Course) criteria.uniqueResult();

        return foundCourse;
    }

    public Lecturer findLecturer(Lecturer src) {
        Session session = (Session) entityManager.getDelegate();

        Example lecturerExample = Example.create(src);

        Criteria criteria = session.createCriteria(Lecturer.class).add(lecturerExample);

        Lecturer foundLecturer = (Lecturer) criteria.uniqueResult();

        return foundLecturer;
    }
}
