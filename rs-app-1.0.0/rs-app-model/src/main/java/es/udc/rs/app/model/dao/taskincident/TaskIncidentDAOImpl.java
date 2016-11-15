package es.udc.rs.app.model.dao.taskincident;

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
import es.udc.rs.app.model.domain.TaskIncident;

@Repository
public class TaskIncidentDAOImpl implements TaskIncidentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(TaskIncident taskIncident) {
		
		// Check if the object is persistent
		if (taskIncident.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Save the TaskIncident
		Long id = (Long) sessionFactory.getCurrentSession().save(taskIncident);
		return id;
	}

	@Override
	public TaskIncident find(Long id) {
		TaskIncident taskIncident = (TaskIncident) sessionFactory.getCurrentSession().get(TaskIncident.class, id);
		return taskIncident;
	}

	@Override
	public List<TaskIncident> findByTask(Task task) {
		// Create the query String 
		String queryString = "FROM TaskIncident T WHERE T.task = :task ORDER BY T.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<TaskIncident> taskIncidents = (List<TaskIncident>) query.list();
		
		return taskIncidents;
	}
	
	@Override 
	public List<TaskIncident> findByProject(Project project) {
		
		List<TaskIncident> taskIncidents = new ArrayList<TaskIncident>();
		List<TaskIncident> incidentsOfThisTask = new ArrayList<TaskIncident>();
		
		// First, we get the Phases of the Project
		String queryString = "FROM Phase P WHERE P.project = :project ORDER BY P.id ASC";
		
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
			
			// For each Task, we check if exists incidents
			for (Task t : tasks) {
				incidentsOfThisTask = findByTask(t);
				
				if (!incidentsOfThisTask.isEmpty()) {
					taskIncidents.addAll(incidentsOfThisTask);
				}
			}
		}
		
		return taskIncidents;
	}

	@Override
	public boolean TaskIncidentExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TaskIncident.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(TaskIncident taskIncident) {
		
		// Check if the object is persistent
		if (taskIncident.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Update the TaskIncident
		sessionFactory.getCurrentSession().update(taskIncident);		
	}

	@Override
	public void remove(TaskIncident taskIncident) {
		sessionFactory.getCurrentSession().delete(taskIncident);
		
	}

}
