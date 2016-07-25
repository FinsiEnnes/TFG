package es.udc.rs.app.model.dao.location;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Province;

@Repository
public class ProvinceDAOImpl implements ProvinceDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Province find(Long id) {
		Province province = (Province) sessionFactory.getCurrentSession().get(Province.class, id);
		return province;
	}

	@Override
	public List<Province> findAll() {
		// Create the query.
		String query = "FROM Province P ORDER BY P.name ASC";

		// Get the persons
		@SuppressWarnings("unchecked")
		List<Province> provinces = (List<Province>) sessionFactory.getCurrentSession().createQuery(query).list();
		return provinces;
	}
	
	@Override
	public List<Province> findByCountry(Country country) {
		
		String stringQuery = "FROM Province P WHERE P.country = :country ORDER BY P.name ASC";
		Query query = sessionFactory.getCurrentSession()
					  .createQuery(stringQuery).setParameter("country", country);
		
		@SuppressWarnings("unchecked")
		List<Province> provinces = (List<Province>) query.list();
		
		return provinces;
	}

}
