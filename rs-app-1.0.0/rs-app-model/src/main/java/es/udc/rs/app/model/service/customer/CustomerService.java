package es.udc.rs.app.model.service.customer;

import java.util.List;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.BusinessCategory;
import es.udc.rs.app.model.domain.BusinessSize;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Customer;
import es.udc.rs.app.model.domain.Province;

public interface CustomerService {

	// ============================ Country operations ============================
	public Country findCountry(String id) throws InstanceNotFoundException;
	
	public List<Country> findAllCountries();

	
	// =========================== Province operations ============================
	public Province findProvince(Long id) throws InstanceNotFoundException;
		
	public List<Province> findAllProvinces();
	
	public List<Province> findProvinceByCountry(Country country) throws InstanceNotFoundException;
	
	
	// ========================= BusinessType operations ==========================
	public BusinessType findBusinessType(String id) throws InstanceNotFoundException;
	
	public List<BusinessType> findAllBusinessTypes();
	
	
	// ======================= BusinessCategory operations ========================
	public BusinessCategory findBusinessCategory(String id) throws InstanceNotFoundException;
	
	public List<BusinessCategory> findAllBusinessCategories();
	
	
	// ========================= BusinessSize operations ==========================
	public BusinessSize findBusinessSize(String id) throws InstanceNotFoundException;
	
	public List<BusinessSize> findAllBusinessSizes();
	
	
	// =========================== Customer operations ============================
	public Long createCustomer(Customer customer) throws InstanceNotFoundException;
	
	public Customer findCustomer(Long id) throws InstanceNotFoundException;
	
	public List<Customer> findAllCustomers();
		
	public List<Customer> findCustomerByName(String name);
		
	public void updateCustomer(Customer customer) throws InstanceNotFoundException;
	
	public void removeCustomer(Long id) throws InstanceNotFoundException;	


}
