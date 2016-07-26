package es.udc.rs.app.model.dao.customer;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Customer customer) {
		
		if (customer.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(customer);
		return id;
	}

	@Override
	public Customer find(Long id) {
		Customer customer = (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		
		// Create the query
		String queryString = "FROM Customer C ORDER BY C.name";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		@SuppressWarnings("unchecked")
		List<Customer> customers = (List<Customer>) query.list();
		
		// Return the result.
		return customers;
	}

	@Override
	public List<Customer> findByName(String name) {
		// Create the query
		String queryString = "FROM Customer C WHERE C.name LIKE :name ORDER BY C.name";

		// Execute the query
		Query query = sessionFactory.getCurrentSession()
					  .createQuery(queryString).setParameter("name", "%"+name+"%");

		@SuppressWarnings("unchecked")
		List<Customer> customers = (List<Customer>) query.list();

		// Return the result.
		return customers;
	}

	@Override
	public boolean CustomerExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);		
	}

	@Override
	public void update(Customer customer) {
		
		if (customer.getId() == null) {
			throw new RuntimeException("The object is no persistent.");
		}
		
		sessionFactory.getCurrentSession().update(customer);		
	}

	@Override
	public void remove(Customer customer) {
		sessionFactory.getCurrentSession().delete(customer);
	}

}
