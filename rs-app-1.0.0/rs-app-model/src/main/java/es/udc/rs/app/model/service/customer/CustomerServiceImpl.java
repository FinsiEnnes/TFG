package es.udc.rs.app.model.service.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.business.BusinessTypeDAO;
import es.udc.rs.app.model.dao.location.CountryDAO;
import es.udc.rs.app.model.dao.location.ProvinceDAO;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Province;

@Service
public class CustomerServiceImpl implements CustomerService {

	static Logger log = Logger.getLogger("project");
	
	// ============================================================================
	// ============================== DAO Injection ===============================
	// ============================================================================
	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	private ProvinceDAO provinceDAO;
	
	@Autowired
	private BusinessTypeDAO businessTypeDAO;
	
	
	// ============================================================================
	// ============================ Country operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Country findCountry(String id) throws InstanceNotFoundException {
		
		Country country = null;
		
		// Find the Country by id.
		try {
			country = countryDAO.find(id);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		// If this Country does not exist, an InstanceNotFoundException is thrown.
		if (country==null) {
			throw new InstanceNotFoundException(id, Country.class.getName());
		}
		
		log.info("Successfull search by id: "+country.toString());
		return country;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Country> findAllCountries() {
		
		List<Country> countries = new ArrayList<Country>();
		
		// Find all the Countries.
		try {
			countries = countryDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+countries.size()+ " registred countries.");
		return countries;
	} 

	// ============================================================================
	// =========================== Province operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Province findProvince(Long id) throws InstanceNotFoundException {
		
		Province province = null;
		
		// Find the Province by id.
		try {
			province = provinceDAO.find(id);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		// If this Country does not exist, an InstanceNotFoundException is thrown.
		if (province==null) {
			throw new InstanceNotFoundException(id, Province.class.getName());
		}
		
		log.info("Successfull search by id: "+province.toString());
		return province;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Province> findAllProvinces() {
		
		List<Province> provinces = new ArrayList<Province>();
		
		// Find all the Provinces by id.
		try {
			provinces = provinceDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+provinces.size()+ " registred provinces.");
		return provinces;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Province> findProvinceByCountry(Country country) {
		List<Province> provinces = new ArrayList<Province>();
		
		// Find all the Provinces by id.
		try {
			provinces = provinceDAO.findByCountry(country);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+provinces.size()+ " registred provinces in."+country.getName());
		return provinces;
	}
	
	
	// ============================================================================
	// ========================= BusinessType operations ==========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public BusinessType findBusinessType(String id) throws InstanceNotFoundException {
		
		BusinessType bt = null;
		
		// Find the Province by id.
		try {
			bt = businessTypeDAO.find(id);
		} 
		catch (DataAccessException e){
			throw e;
		}

		// If this Country does not exist, an InstanceNotFoundException is thrown.
		if (bt==null) {
			throw new InstanceNotFoundException(id, BusinessType.class.getName());
		}

		log.info("Successfull search by id: "+bt.toString());
		return bt;

	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<BusinessType> findAllBusinessTypes() {

		List<BusinessType> bts = new ArrayList<BusinessType>();
		
		// Find all the Provinces by id.
		try {
			bts = businessTypeDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+bts.size()+ " registred business types");
		return bts;
	}

}
