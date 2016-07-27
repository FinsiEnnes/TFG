package es.udc.rs.app.model.dao.project;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Project;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Project project) {
		
		if (project.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(project);
		return id;
	}

	@Override
	public Project find(Long id) {
		Project project = (Project) sessionFactory.getCurrentSession().get(Project.class, id);
		return project;
	}

	@Override
	public Project findByName(String name) {
		
		// Create the criteria in function of the attributes.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
		criteria.add(Restrictions.eq("name", name));
		
		// Execute the criteria
		Project project = (Project) criteria.list().get(0);
		
		return project;
	}

	@Override
	public boolean ProjectExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Project project) {
		
		if (project.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(project);	
	}

	@Override
	public void remove(Project project) {
		sessionFactory.getCurrentSession().delete(project);		
	}

}
