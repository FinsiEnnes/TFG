package es.udc.rs.app.model.dao.incident;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Incident;

@Repository
public class IncidentDAOImpl implements IncidentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Incident incident) {
		
		if (incident.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(incident);
		return id;
	}

	@Override
	public Incident find(Long id) {
		Incident incident = (Incident) sessionFactory.getCurrentSession().get(Incident.class, id);
		return incident;
	}

	@Override
	public boolean IncidentExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Incident.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());

		// Get the number of rows.
		long count = (Long) criteria.uniqueResult();

		return (count==1L);	
	}

	@Override
	public void update(Incident incident) {
		
		if (incident.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(incident);
		
	}

	@Override
	public void remove(Incident incident) {
		sessionFactory.getCurrentSession().delete(incident);		
	}

}
