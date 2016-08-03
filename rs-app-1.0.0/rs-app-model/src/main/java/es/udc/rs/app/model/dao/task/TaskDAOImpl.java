package es.udc.rs.app.model.dao.task;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
