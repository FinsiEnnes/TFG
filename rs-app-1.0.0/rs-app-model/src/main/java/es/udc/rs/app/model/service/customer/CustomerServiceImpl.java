package es.udc.rs.app.model.service.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.business.BusinessCategoryDAO;
import es.udc.rs.app.model.dao.business.BusinessSizeDAO;
import es.udc.rs.app.model.dao.business.BusinessTypeDAO;
import es.udc.rs.app.model.dao.customer.CustomerDAO;
import es.udc.rs.app.model.dao.location.CountryDAO;
import es.udc.rs.app.model.dao.location.ProvinceDAO;
import es.udc.rs.app.model.domain.BusinessCategory;
import es.udc.rs.app.model.domain.BusinessSize;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Customer;
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
	
	@Autowired
	private BusinessCategoryDAO businessCatgDAO;
	
	@Autowired
	private BusinessSizeDAO businessSizeDAO;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	
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
	
	
	// ============================================================================
	// ======================= BusinessCategory operations ========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public BusinessCategory findBusinessCategory(String id) throws InstanceNotFoundException {
		
		BusinessCategory businessCatg = null;
		
		// Find businessCategory by id.
		try {
			businessCatg = businessCatgDAO.find(id);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if there is result.
		if (businessCatg == null) {
			throw new InstanceNotFoundException(id, BusinessCategory.class.getName());
		}

		log.info("Successfull search by id: "+businessCatg.toString());
		return businessCatg;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<BusinessCategory> findAllBusinessCategories() {
		
		List<BusinessCategory> businessCatgs = new ArrayList<BusinessCategory>();
		
		// Find all businessCategories
		try {
			businessCatgs = businessCatgDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+businessCatgs.size()+ " registred business categories");
		return businessCatgs;
	}
	
	// ============================================================================
	// ========================= BusinessSize operations ==========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public BusinessSize findBusinessSize(String id) throws InstanceNotFoundException {
		
		BusinessSize businessSize = null;
		
		// Find businessSize by id.
		try {
			businessSize = businessSizeDAO.find(id);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		// Check
		if (businessSize == null) {
			throw new InstanceNotFoundException(id, BusinessSize.class.getName());
		}

		log.info("Successfull search by id: "+businessSize.toString());
		return businessSize;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<BusinessSize> findAllBusinessSizes() {
		
		List<BusinessSize> businessSizes = new ArrayList<BusinessSize>();
		
		// Find all businessSizes.
		try {
			businessSizes = businessSizeDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+businessSizes.size()+ " registred business sizes");
		return businessSizes;
	}
	
	
	// ============================================================================
	// =========================== Customer operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createCustomer(Customer customer) {
		Long id = null;
		
		try{
			id = customerDAO.create(customer);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("Successfull insertion: "+customer.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Customer findCustomer(Long id)  throws InstanceNotFoundException {
		Customer customer = null;
		
		try{
			customer = customerDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		if (customer == null) {
			throw new InstanceNotFoundException(id, Customer.class.getName()); 
		}
		
		log.info("Successfull search by id: "+customer.toString());
		return customer;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Customer> findAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		
		try{
			customers = customerDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+customers.size()+ " registred customers.");
		return customers;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Customer> findCustomerByName(String name) {
		List<Customer> customers = new ArrayList<Customer>();
		
		try{
			customers = customerDAO.findByName(name);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("There are a total of "+customers.size()+ " registred customers with name like "+name+".");
		return customers;
	}
		
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateCustomer(Customer customer) throws InstanceNotFoundException {
		
		Long idCustomer = customer.getId();
		
		if (!customerDAO.CustomerExists(idCustomer)) {
			throw new InstanceNotFoundException(idCustomer, Customer.class.getName());
		}
		
		try{
			customerDAO.update(customer);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("Successfull updated: "+customer.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeCustomer(Long id) throws InstanceNotFoundException {
	
		Customer customer = findCustomer(id);
		
		try{
			customerDAO.remove(customer);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("Successfull deleted: "+customer.toString());
	}

}
