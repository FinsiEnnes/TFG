package es.udc.rs.app.model.dao.milestone;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;

@Repository
public class MilestoneDAOImpl implements MilestoneDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Milestone milestone) {
		
		// Check if the Milestone is persistent
		if (milestone.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create the new Milestone
		Long id = (Long) sessionFactory.getCurrentSession().save(milestone);
		return id;
	}

	@Override
	public Milestone find(Long id) {
		Milestone milestone = (Milestone) sessionFactory.getCurrentSession().get(Milestone.class, id);
		return milestone;
	}
	
	@Override
	public List<Milestone> findByProject(Project project) {
		
		List<Milestone> milestones = new ArrayList<Milestone>();
		
		// First, we get the Phases of the Project
		String queryString = "FROM Phase P WHERE P.project = :project";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		@SuppressWarnings("unchecked")
		List<Phase> phases = (List<Phase>) query.list();
		
		// For each Phase, we find it Milestones
		for (Phase p : phases) {
			queryString = "FROM Milestone M WHERE M.phase = :phase ORDER BY M.datePlan";
			
			query = sessionFactory.getCurrentSession().createQuery(queryString);
			query.setParameter("phase", p);
			
			@SuppressWarnings("unchecked")
			List<Milestone> thisMilestones = (List<Milestone>) query.list();
			
			milestones.addAll(thisMilestones);
		}
		
		return milestones;
	}

	@Override
	public boolean milestoneExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Milestone.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Milestone milestone) {
		
		// Check if the object is persistent
		if (milestone.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Update the milestone
		sessionFactory.getCurrentSession().update(milestone);		
	}

	@Override
	public void remove(Milestone milestone) {
		sessionFactory.getCurrentSession().delete(milestone);		
	}

}
