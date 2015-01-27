package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import webdziekanat.interfaces.IAddressDAO;
import webdziekanat.model.Address;
import webdziekanat.model.Student;

@Component
@Transactional
public class AddressDAO implements IAddressDAO {

    private static final Logger logger = LogManager.getLogger(AddressDAO.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    public AddressDAO() {
        
    }

    public void addAddress(Address address) {
        try {
            entityManager.persist(address);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public void deleteAddress(int id) {
        try {
            entityManager.remove(getAddressById(id));
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }

    }

    public void updateAddress(Address address) {
        try {
            entityManager.merge(address);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Address getAddressById(int id) {

        Address result = new Address();
        
        result = entityManager.find(Address.class, id);

        logger.info("Found [" + result.toString() + "]" + "with street: " + result.getStreet() + "and id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Address> getAll() {
        List<Address> result = new ArrayList<Address>();

        try {

            String hqlString = "Select address from Address address";
            
            result = (List<Address>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }

}
