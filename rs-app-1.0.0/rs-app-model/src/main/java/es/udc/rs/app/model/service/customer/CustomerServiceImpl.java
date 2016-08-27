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
import es.udc.rs.app.model.util.FindInstanceService;
import es.udc.rs.app.model.util.ModelConstants;

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
	
	// Assistant service
	@Autowired
	private FindInstanceService findInstanceService;
	
	
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
		
		log.info(ModelConstants.FIND_ID + country.toString());
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
		
		log.info(ModelConstants.FIND_ALL + countries.size() + " registred countries");
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
		
		log.info(ModelConstants.FIND_ID + province.toString());
		return province;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Province> findAllProvinces() {
		
		List<Province> provinces = new ArrayList<Province>();
		
		// Find all the Provinces.
		try {
			provinces = provinceDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + provinces.size()+ " registred provinces.");
		return provinces;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Province> findProvinceByCountry(Country country) throws InstanceNotFoundException {
		List<Province> provinces = new ArrayList<Province>();
		
		// Check if the Country exists
		findInstanceService.findCountry(country);
		
		// Find all the Provinces by Country.
		try {
			provinces = provinceDAO.findByCountry(country);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + provinces.size()+ " registred provinces in the Country"
				+ " with nameCountry[" + country.getName() + "]");
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

		log.info(ModelConstants.FIND_ID + bt.toString());
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
		
		log.info(ModelConstants.FIND_ALL + " registred business types");
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

		log.info(ModelConstants.FIND_ID + businessCatg.toString());
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
		
		log.info(ModelConstants.FIND_ALL + " registred business categories");
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

		log.info(ModelConstants.FIND_ID + businessSize.toString());
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
		
		log.info(ModelConstants.FIND_ALL + " registred business sizes");
		return businessSizes;
	}
	
	
	// ============================================================================
	// =========================== Customer operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createCustomer(Customer customer) throws InstanceNotFoundException {
		Long id = null;
		
		// Check if exist the Province and the business attributes
		findInstanceService.findProvince(customer.getProvince());
		findInstanceService.findBusinessCategory(customer.getCategory());
		findInstanceService.findBusinessSize(customer.getSize());
		findInstanceService.findBusinessType(customer.getType());
		
		try{
			id = customerDAO.create(customer);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.CREATE + customer.toString());
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
		
		log.info(ModelConstants.FIND_ID + customer.toString());
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
		
		log.info(ModelConstants.FIND_ALL + " registred customers.");
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
		
		log.info(ModelConstants.FIND_ALL + customers.size()+ " registred customers with name like "+name+".");
		return customers;
	}
		
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateCustomer(Customer customer) throws InstanceNotFoundException {
		
		// Check if the customer exists
		findInstanceService.findCustomer(customer);
		
		try{
			customerDAO.update(customer);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + customer.toString());
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
		
		log.info(ModelConstants.DELETE + customer.toString());
	}

}
