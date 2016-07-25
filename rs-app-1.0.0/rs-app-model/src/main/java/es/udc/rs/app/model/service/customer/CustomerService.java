package es.udc.rs.app.model.service.customer;

import java.util.List;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Province;

public interface CustomerService {

	// ============================ Country operations ============================
	public Country findCountry(String id) throws InstanceNotFoundException;
	
	public List<Country> findAllCountries();

	// =========================== Province operations ============================
	public Province findProvince(Long id) throws InstanceNotFoundException;
		
	public List<Province> findAllProvinces();
	
	public List<Province> findProvinceByCountry(Country country);
	
	// ========================= BusinessType operations ==========================
	public BusinessType findBusinessType(String id) throws InstanceNotFoundException;
	
	public List<BusinessType> findAllBusinessTypes();
}
