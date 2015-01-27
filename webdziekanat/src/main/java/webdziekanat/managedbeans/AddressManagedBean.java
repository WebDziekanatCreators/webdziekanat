package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.IAddressDAO;
import webdziekanat.model.Address;

@Component("addressMB")
@Scope("session")
public class AddressManagedBean implements Serializable{

    private static final long serialVersionUID = 2880746693462123546L;
    private static final Logger logger = LogManager.getLogger(AddressManagedBean.class);
    private Map<String,String> counties;
    
    @Autowired
    IAddressDAO addressDAO;
    
    Address address = new Address();
    
    List<Address> addresses;
    
    boolean isAdd;
    boolean isEdit;
    
    public String startAdd(){
        address = new Address();
        isAdd = true;
        isEdit = false;
        return "/pages/addAddress.xhtml";
    }
    
    public String addAddress(){
        
        try {
            Address addressNew = new Address(address);
            addressDAO.addAddress(addressNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Address: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }

    public String startEdit(Address src) {
        address = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addAddress.xhtml";
    }
    
    public String editAddress(){
        addressDAO.updateAddress(address);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
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
        counties.put("dolnoœl¹skie", "dolnoœl¹skie");
        counties.put("kujawsko-pomorskie", "kujawsko-pomorskie");
        counties.put("lubelskie", "lubelskie");
        counties.put("lubuskie", "lubuskie");
        counties.put("³ódzkie", "³ódzkie");
        counties.put("ma³opolskie", "ma³opolskie");
        counties.put("mazowieckie", "mazowieckie");
        counties.put("opolskie", "opolskie");
        counties.put("podkarpackie", "podkarpackie");
        counties.put("podlaskie", "podlaskie");
        counties.put("pomorskie", "pomorskie");
        counties.put("œl¹skie", "œl¹skie");
        counties.put("œwiêtokrzyskie", "œwiêtokrzyskie");
        counties.put("warmiñsko-mazurskie", "warmiñsko-mazurskie");
        counties.put("wielkopolskie", "wielkopolskie");
        counties.put("zachodniopomorskie", "zachodniopomorskie");
        
        return counties;
    }

    public void setCounties(Map<String,String> counties) {
        this.counties = counties;
    }
    
    
}
