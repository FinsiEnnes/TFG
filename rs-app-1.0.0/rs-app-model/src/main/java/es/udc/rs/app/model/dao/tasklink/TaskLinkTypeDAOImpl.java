package es.udc.rs.app.model.dao.tasklink;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.TaskLinkType;

@Repository
public class TaskLinkTypeDAOImpl implements TaskLinkTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public TaskLinkType find(String id) {
		TaskLinkType tlt = (TaskLinkType) sessionFactory.getCurrentSession().get(TaskLinkType.class, id);
		return tlt;
	}

	@Override
	public List<TaskLinkType> findAll() {
		// Create the query
		String queryString = "FROM TaskLinkType P ORDER BY P.id";
		
		// Execute the query 
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<TaskLinkType> tlts = query.list();
		
		return tlts;
	}

}
