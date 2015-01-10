package webdziekanat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webdziekanat.interfaces.IAddressDAO;
import webdziekanat.model.Address;

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
            Address foundAddress = entityManager.find(Address.class, address.getId());
            foundAddress = address;
            entityManager.merge(foundAddress);
        } catch (Exception e) {
            logger.error("Rollback - " + e.getMessage());
        }
    }

    public Address getAddressById(int id) {

        Address result = new Address();

        String hql = "Select address from Address webdziekanat where address.id = :number";

        result = (Address) entityManager.createQuery(hql).setParameter("number", id).getSingleResult();

        logger.info("Found [" + result.toString() + "]" + "with street: " + result.getStreet() + "and id: "
                + result.getId());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Address> getAll() {
        List<Address> result = new ArrayList<Address>();

        try {

            String hqlString = "Select address from Address webdziekanat";
            
            result = (List<Address>) entityManager.createQuery(hqlString).getResultList();

        } catch (Exception e) {
            logger.error("Rollback" + e.getMessage());
        }

        return result;
    }

}
