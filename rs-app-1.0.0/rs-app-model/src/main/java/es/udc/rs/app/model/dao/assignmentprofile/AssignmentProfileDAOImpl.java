package es.udc.rs.app.model.dao.assignmentprofile;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.Task;

@Repository
public class AssignmentProfileDAOImpl implements AssignmentProfileDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(AssignmentProfile assignmentProfile) {
		
		if (assignmentProfile.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(assignmentProfile);
		return id;
	}

	@Override
	public AssignmentProfile find(Long id) {
		Session s = sessionFactory.getCurrentSession();
		AssignmentProfile assigProf = (AssignmentProfile) s.get(AssignmentProfile.class, id);
		
		return assigProf;
	}

	@Override
	public List<AssignmentProfile> findByTask(Task task) {
		
		// Create the query string
		String queryString = "FROM AssignmentProfile A WHERE A.task = :task ORDER BY A.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<AssignmentProfile> assigProf = (List<AssignmentProfile>) query.list();
		
		return assigProf;
	}

	@Override
	public boolean AssignmentProfileExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AssignmentProfile.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(AssignmentProfile assignmentProfile) {
		
		if (assignmentProfile.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(assignmentProfile);	
	}

	@Override
	public void remove(AssignmentProfile assignmentProfile) {
		sessionFactory.getCurrentSession().delete(assignmentProfile);	
	}

}
