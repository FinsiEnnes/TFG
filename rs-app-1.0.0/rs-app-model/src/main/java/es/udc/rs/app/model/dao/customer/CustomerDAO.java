package es.udc.rs.app.model.dao.customer;

import java.util.List;

import es.udc.rs.app.model.domain.Customer;

public interface CustomerDAO {

	public Long create(Customer customer);
	
	public Customer find(Long id);
	
	public List<Customer> findAll();
		
	public List<Customer> findByName(String name);
	
	public boolean CustomerExists(Long id);
	
	public void update(Customer customer);
	
	public void remove(Customer customer);	
}
