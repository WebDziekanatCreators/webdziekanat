package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import webdziekanat.interfaces.IAddressDAO;
import webdziekanat.model.Address;

@ManagedBean(name="addressMB")
@RequestScoped
public class AddressManagedBean implements Serializable{

    private static final long serialVersionUID = 2880746693462123546L;
    private static final Logger logger = LogManager.getLogger(AddressManagedBean.class);
    private Map<String,String> counties;
    
    @ManagedProperty(value="#{addressDAO}")
    IAddressDAO addressDAO;
    
    Address address = new Address();
    
    List<Address> addresses;
    
    public String addAddress(){
        
        try {
            Address addressNew = new Address(address);
            addressDAO.addAddress(addressNew);
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Address: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Address> getList() {
        return addresses;
    }

    public void setList(List<Address> list) {
        this.addresses = list;
    }

    public IAddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(IAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public Map<String,String> getCounties() {
        counties = new HashMap<String, String>();
        counties.put("dolnośląskie", "dolnośląskie");
        counties.put("kujawsko-pomorskie", "kujawsko-pomorskie");
        counties.put("lubelskie", "lubelskie");
        counties.put("lubuskie", "lubuskie");
        counties.put("łódzkie", "łódzkie");
        counties.put("małopolskie", "małopolskie");
        counties.put("mazowieckie", "mazowieckie");
        counties.put("opolskie", "opolskie");
        counties.put("podkarpackie", "podkarpackie");
        counties.put("podlaskie", "podlaskie");
        counties.put("pomorskie", "pomorskie");
        counties.put("śląskie", "śląskie");
        counties.put("świętokrzyskie", "świętokrzyskie");
        counties.put("warmińsko-mazurskie", "warmińsko-mazurskie");
        counties.put("wielkopolskie", "wielkopolskie");
        counties.put("zachodniopomorskie", "zachodniopomorskie");
        
        return counties;
    }

    public void setCounties(Map<String,String> counties) {
        this.counties = counties;
    }
    
    
}
