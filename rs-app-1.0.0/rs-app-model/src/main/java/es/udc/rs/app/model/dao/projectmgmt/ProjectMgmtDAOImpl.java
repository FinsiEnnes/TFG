package es.udc.rs.app.model.dao.projectmgmt;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectMgmt;

@Repository
public class ProjectMgmtDAOImpl implements ProjectMgmtDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(ProjectMgmt projectMgmt) {
		
		// Check the persistence of the object
		if (projectMgmt.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create the object and return the id
		Long id = (Long) sessionFactory.getCurrentSession().save(projectMgmt);
		return id;
	}

	@Override
	public ProjectMgmt find(Long id) {
		// Get the session 
		Session session = sessionFactory.getCurrentSession();
		
		// Now, get the ProjectMgmt
		ProjectMgmt projectMgmt = (ProjectMgmt) session.get(ProjectMgmt.class, id);
		return projectMgmt;
	}

	@Override
	public List<ProjectMgmt> findAll() {
		// Create the string query
		String queryString = "FROM ProjectMgmt P ORDER BY P.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get and return the result
		@SuppressWarnings("unchecked")
		List<ProjectMgmt> projectMgmts = (List<ProjectMgmt>) query.list();
		
		return projectMgmts;
	}

	@Override
	public List<ProjectMgmt> findByProject(Project project) {
		// Create the string query
		String queryString = "FROM ProjectMgmt P WHERE P.project = :project ORDER BY P.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		// Get and return the result
		@SuppressWarnings("unchecked")
		List<ProjectMgmt> projectMgmts = (List<ProjectMgmt>) query.list();
		
		return projectMgmts;
	}

	@Override
	public boolean ProjectMgmtExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProjectMgmt.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);
	}

	@Override
	public void update(ProjectMgmt projectMgmt) {
		// Check the persistence of the object
		if (projectMgmt.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Update the object
		sessionFactory.getCurrentSession().update(projectMgmt);	
		
	}

	@Override
	public void remove(ProjectMgmt projectMgmt) {
		sessionFactory.getCurrentSession().delete(projectMgmt);		
	}

}
