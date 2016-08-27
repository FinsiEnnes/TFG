package es.udc.rs.app.model.dao.location;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Country;

@Repository
public class CountryDAOImpl implements CountryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Country find(String id) {
		Country country = (Country) sessionFactory.getCurrentSession().get(Country.class, id);
		return country;
	}

	@Override
	public List<Country> findAll() {
		// Create the query.
		String query = "FROM Country C ORDER BY C.name ASC";

		// Get the persons
		@SuppressWarnings("unchecked")
		List<Country> countries = (List<Country>) sessionFactory.getCurrentSession().createQuery(query).list();
		return countries;
	}

	@Override
	public boolean countryExists(String id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Country.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}
	
	

}
