package es.udc.rs.app.model.dao.projectfreeday;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectFreeDay;

@Repository
public class ProjectFreeDayDAOImpl implements ProjectFreeDayDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(ProjectFreeDay projectFreeDay) {
		
		// Check the persistence of the object
		if (projectFreeDay.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create the object and return the id
		Long id = (Long) sessionFactory.getCurrentSession().save(projectFreeDay);
		return id;
	}

	@Override
	public ProjectFreeDay find(Long id) {
		
		// Get the session 
		Session session = sessionFactory.getCurrentSession();
		
		// Now, get the ProjectFreeDay
		ProjectFreeDay projectFreeDay = (ProjectFreeDay) session.get(ProjectFreeDay.class, id);
		return projectFreeDay;
	}

	@Override
	public List<ProjectFreeDay> findAll() {
		// Create the string query
		String queryString = "FROM ProjectFreeDay P ORDER BY P.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get and return the result
		@SuppressWarnings("unchecked")
		List<ProjectFreeDay> projectFreeDays = (List<ProjectFreeDay>) query.list();
		
		return projectFreeDays;
	}

	@Override
	public List<FreeDay> findByProject(Project project) {
		// Create the string query
		String queryString = "FROM ProjectFreeDay P WHERE P.project = :project ORDER BY P.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		// Get and return the result
		@SuppressWarnings("unchecked")
		List<ProjectFreeDay> projectFreeDays = (List<ProjectFreeDay>) query.list();
		
		// Now we get the FreeDays
		List<FreeDay> freeDays = new ArrayList<FreeDay>();
		FreeDay freeDay;

		for (ProjectFreeDay pfd : projectFreeDays) {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FreeDay.class);
			criteria.add(Restrictions.eq("id", pfd.getFreeDay().getId()));
			
			freeDay = (FreeDay) criteria.list().get(0);
			freeDays.add(freeDay);
		}
		
		return freeDays;
	}
	
	@Override
	public List<FreeDay> findDistinctThisProject(Project project) {
		// Create the string query
		String queryString = "FROM FreeDay F "
						   + "WHERE F.id NOT IN (SELECT P.freeDay "
						   					  + "FROM ProjectFreeDay P "
						   					  + "WHERE P.project = :project)";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		// Get and return the result
		@SuppressWarnings("unchecked")
		List<FreeDay> freeDays = (List<FreeDay>) query.list();
		
		// Return the result
		return freeDays;
	}

	@Override
	public boolean ProjectFreeDayExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProjectFreeDay.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);
	}

	@Override
	public void update(ProjectFreeDay projectFreeDay) {
		// Check the persistence of the object
		if (projectFreeDay.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Update the object
		sessionFactory.getCurrentSession().update(projectFreeDay);		
		
	}

	@Override
	public void remove(ProjectFreeDay projectFreeDay) {
		// Remove the object
		sessionFactory.getCurrentSession().delete(projectFreeDay);	
		
	}

}
