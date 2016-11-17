package es.udc.rs.app.model.dao.predecessor;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;

@Repository
public class PredecessorDAOImpl implements PredecessorDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Predecessor predecessor) {
		
		// Check if the object is persistent
		if (predecessor.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create the object
		Long id = (Long) sessionFactory.getCurrentSession().save(predecessor);
		return id;
	}

	@Override
	public Predecessor find(Long id) {
		Predecessor predecessor = (Predecessor) sessionFactory.getCurrentSession().get(Predecessor.class, id);
		return predecessor;
	}

	@Override
	public List<Predecessor> findByTask(Task task) {
		
		// Create the query String 
		String queryString = "FROM Predecessor P WHERE P.task = :task ORDER BY P.linkType";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Predecessor> predecessors = (List<Predecessor>) query.list();
		
		return predecessors;
	}
	
	@Override
	public List<Predecessor> findByProject(Project project) {
		
		List<Predecessor> predecessors = new ArrayList<Predecessor>();
		
		// First, we get the Phases of the Project
		String queryString = "FROM Phase P WHERE P.project = :project ORDER BY P.ini ASC";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		@SuppressWarnings("unchecked")
		List<Phase> phases = (List<Phase>) query.list();
		
		// For each Phase, we find it Tasks
		for (Phase p : phases) {
			queryString = "FROM Task T WHERE T.phase = :phase ORDER BY T.iniPlan";
			
			query = sessionFactory.getCurrentSession().createQuery(queryString);
			query.setParameter("phase", p);
			
			@SuppressWarnings("unchecked")
			List<Task> tasks = (List<Task>) query.list();
			
			// For each task, we get the links with others tasks
			for (Task t : tasks) {
				queryString = "FROM Predecessor P WHERE P.task = :task";
				
				query = sessionFactory.getCurrentSession().createQuery(queryString);
				query.setParameter("task", t);
				
				@SuppressWarnings("unchecked")
				List<Predecessor> thisLinks = (List<Predecessor>) query.list();
				
				predecessors.addAll(thisLinks);
			}
		}
		
		return predecessors;
	}

	@Override
	public boolean PredecessorExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Predecessor.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Predecessor predecessor) {
		// Check if the object is persistent
		if (predecessor.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Create the object
		sessionFactory.getCurrentSession().update(predecessor);
		
	}

	@Override
	public void remove(Predecessor predecessor) {
		sessionFactory.getCurrentSession().delete(predecessor);
	}

}
