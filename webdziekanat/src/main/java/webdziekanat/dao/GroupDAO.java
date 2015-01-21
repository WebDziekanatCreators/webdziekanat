package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webdziekanat.interfaces.IGroupDAO;
import webdziekanat.model.Group;

public class GroupDAO implements IGroupDAO{
    
    private static final Logger logger = LogManager.getLogger(GroupDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public GroupDAO() {
        
    }

    public void addGroup(Group group) {
        try {
            entityManager.persist(group);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteGroup(int id) {
        try {
            entityManager.remove(getGroupById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

    }

    public void updateGroup(Group group) {
        try {
            Group foundGroup = entityManager.find(Group.class, group.getId());
            foundGroup = group;
            entityManager.merge(foundGroup);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Group getGroupById(int id) {

        Group result = new Group();

        String hql = "Select group from Group webdziekanat where group.id = :number";

        result = (Group) entityManager.createQuery(hql).setParameter("number", id).getSingleResult();

        logger.info("Found [" + result.toString() + "]" + "with id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Group> getAll() {
        List<Group> result = new ArrayList<Group>();

        try {

            String hqlString = "Select group from Group webdziekanat";
            
            result = (List<Group>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }


}
