package es.udc.rs.app.model.dao.task;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Priority;

@Repository
public class PriorityDAOImpl implements PriorityDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Priority find(String id) {
		Priority priority = (Priority) sessionFactory.getCurrentSession().get(Priority.class, id);
		return priority;
	}

	@Override
	public List<Priority> findAll() {
		// Create the query
		String queryString = "FROM Priority P ORDER BY P.id";
		
		// Execute the query 
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Priority> priorities = query.list();
		
		return priorities;
	}

	@Override
	public boolean priorityExists(String id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Priority.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

}
