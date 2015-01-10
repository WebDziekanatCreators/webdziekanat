package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Address;

public interface IAddressDAO {
    public void addAddress(Address address);
    
    public void deleteAddress(int id);
    
    public void updateAddress(Address address);
    
    public Address getAddressById(int id);
    
    public List<Address> getAll();
}
