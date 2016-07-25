package es.udc.rs.app.model.dao.location;

import java.util.List;

import org.hibernate.SessionFactory;
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

}
