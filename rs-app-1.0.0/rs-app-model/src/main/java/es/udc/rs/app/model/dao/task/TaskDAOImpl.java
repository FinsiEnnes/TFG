package es.udc.rs.app.model.dao.task;

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
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;

@Repository
public class TaskDAOImpl implements TaskDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Task task) {
		
		if (task.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(task);
		return id;
	}

	@Override
	public Task find(Long id) {
		Task task = (Task) sessionFactory.getCurrentSession().get(Task.class, id);
		return task;
	}
	
	@Override 
	public List<Task> findByProject(Project project) {
		
		List<Task> projectTasks = new ArrayList<Task>();
		
		// First, we get the Phases of the Project
		String queryString = "FROM Phase P WHERE P.project = :project";
		
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
			
			// Get the task of this Phase
			projectTasks.addAll(tasks);
		}
		
		return projectTasks;
	}

	@Override
	public boolean TaskExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Task.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Task task) {
		
		if (task.getId() == null) {
			throw new RuntimeException("The object is no persistent.");
		}
		
		sessionFactory.getCurrentSession().update(task);	
	}

	@Override
	public void remove(Task task) {
		sessionFactory.getCurrentSession().delete(task);		
	}

}
