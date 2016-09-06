package es.udc.rs.app.model.dao.incident;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Damage;

@Repository
public class DamageDAOImpl implements DamageDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Damage find(String id) {
		
		// Find the Damage by id
		Damage damage = (Damage) sessionFactory.getCurrentSession().get(Damage.class, id);
		return damage;
	}

	@Override
	public List<Damage> findAll() {
		
		// Create the query
		String queryString = "FROM Damage D ORDER BY D.id ASC";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Damage> damages = (List<Damage>) query.list();
		
		return damages;
	}

	@Override
	public boolean damageExists(String id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Damage.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());

		// Get the number of rows.
		long count = (Long) criteria.uniqueResult();

		return (count==1L);	
	}

}
