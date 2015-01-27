package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.interfaces.IGroupDAO;
import webdziekanat.model.LearningGroup;

@Component
@Transactional
public class GroupDAO implements IGroupDAO{
    
    private static final Logger logger = LogManager.getLogger(GroupDAO.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public GroupDAO() {
        
    }

    public void addGroup(LearningGroup group) {
        try {
            entityManager.persist(group);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public boolean deleteGroup(int id) {
        try {
            entityManager.remove(getGroupById(id));
            return true;
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
            return false;
        }

    }

    public void updateGroup(LearningGroup group) {
        try {
            LearningGroup foundGroup = entityManager.find(LearningGroup.class, group.getId());
            foundGroup = group;
            entityManager.merge(foundGroup);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public LearningGroup getGroupById(int id) {

        LearningGroup result = new LearningGroup();

        result = entityManager.find(LearningGroup.class, id);

        logger.info("Found [" + result.toString() + "]" + "with id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<LearningGroup> getAll() {
        List<LearningGroup> result = new ArrayList<LearningGroup>();

        try {

            String hqlString = "Select group from Group group";
            
            result = (List<LearningGroup>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }


}
