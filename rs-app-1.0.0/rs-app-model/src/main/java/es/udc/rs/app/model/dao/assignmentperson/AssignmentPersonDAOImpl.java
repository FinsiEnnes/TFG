package es.udc.rs.app.model.dao.assignmentperson;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;

@Repository
public class AssignmentPersonDAOImpl implements AssignmentPersonDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(AssignmentPerson assignmentPerson) {
		if (assignmentPerson.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(assignmentPerson);
		return id;
	}

	@Override
	public AssignmentPerson find(Long id) {
		Session s = sessionFactory.getCurrentSession();
		AssignmentPerson assigPerson = (AssignmentPerson) s.get(AssignmentPerson.class, id);
		
		return assigPerson;
	}

	@Override
	public List<AssignmentPerson> findByTask(Task task) {
		// Create the query string
		String queryString = "FROM AssignmentPerson A WHERE A.task = :task ORDER BY A.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<AssignmentPerson> assigPersons = (List<AssignmentPerson>) query.list();
		
		return assigPersons;
	}

	@Override
	public List<AssignmentPerson> findByPerson(HistoryPerson historyPerson) {
		// Create the query string
		String queryString = "FROM AssignmentPerson A WHERE A.historyPerson = :hp ORDER BY A.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("hp", historyPerson);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<AssignmentPerson> assigPersons = (List<AssignmentPerson>) query.list();
		
		return assigPersons;
	}

	@Override
	public boolean AssignmentPersonExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AssignmentPerson.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(AssignmentPerson assignmentPerson) {
		if (assignmentPerson.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(assignmentPerson);		
	}

	@Override
	public void remove(AssignmentPerson assignmentPerson) {
		sessionFactory.getCurrentSession().delete(assignmentPerson);		
	}

}
