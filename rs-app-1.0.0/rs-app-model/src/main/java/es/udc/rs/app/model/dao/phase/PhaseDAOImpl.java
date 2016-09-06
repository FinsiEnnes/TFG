package es.udc.rs.app.model.dao.phase;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;

@Repository
public class PhaseDAOImpl implements PhaseDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Phase phase) {
		
		// Check if the object is persistent
		if (phase.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create the phase and return the new id
		Long id = (Long) sessionFactory.getCurrentSession().save(phase);
		return id;
	}

	@Override
	public Phase find(Long id) {
		Phase phase = (Phase) sessionFactory.getCurrentSession().get(Phase.class, id);		
		return phase;
	}

	@Override
	public List<Phase> findByProject(Project project) {
		
		// Create the query
		String queryString = "FROM Phase P WHERE P.project = :project ORDER BY P.iniPlan ASC";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Phase> phases = (List<Phase>) query.list();
		
		return phases;
	}

	@Override
	public boolean phaseExists(Long id) {		
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Phase.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Phase phase) {
		// Check if the object is persistent
		if (phase.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Now update
		sessionFactory.getCurrentSession().update(phase);		
	}

	@Override
	public void remove(Phase phase) {
		sessionFactory.getCurrentSession().delete(phase);				
	}

}
