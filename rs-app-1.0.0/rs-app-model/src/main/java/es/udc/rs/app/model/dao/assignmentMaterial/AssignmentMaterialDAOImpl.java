package es.udc.rs.app.model.dao.assignmentMaterial;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.Task;

@Repository
public class AssignmentMaterialDAOImpl implements AssignmentMaterialDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(AssignmentMaterial assignmentMaterial) {
		
		// Checks if it is persistent
		if (assignmentMaterial.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create and return the id
		Long id = (Long) sessionFactory.getCurrentSession().save(assignmentMaterial);
		return id;
	}

	@Override
	public AssignmentMaterial find(Long id) {
		Session s = sessionFactory.getCurrentSession();
		AssignmentMaterial assigMat = (AssignmentMaterial) s.get(AssignmentMaterial.class, id);
		
		return assigMat;
	}

	@Override
	public List<AssignmentMaterial> findByTask(Task task) {
		// Create the query string
		String queryString = "FROM AssignmentMaterial A WHERE A.task = :task ORDER BY A.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<AssignmentMaterial> assigMats = (List<AssignmentMaterial>) query.list();
		
		return assigMats;
	}

	@Override
	public List<AssignmentMaterial> findByTaskPlan(Task task) {
		// Create the query string
		String queryString = "FROM AssignmentMaterial A "
						   + "WHERE A.task = :task  AND A.plan = true AND A.real = false "
						   + "ORDER BY A.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<AssignmentMaterial> assigMats = (List<AssignmentMaterial>) query.list();
		
		return assigMats;
	}

	@Override
	public List<AssignmentMaterial> findByTaskReal(Task task) {
		// Create the query string
		String queryString = "FROM AssignmentMaterial A "
				   + "WHERE A.task = :task  AND A.plan = false AND A.real = true "
				   + "ORDER BY A.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<AssignmentMaterial> assigMats = (List<AssignmentMaterial>) query.list();
		
		return assigMats;
	}

	@Override
	public boolean AssignmentMaterialExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AssignmentMaterial.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(AssignmentMaterial assignmentMaterial) {
		// Checks if it is persistent
		if (assignmentMaterial.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Update the object
		sessionFactory.getCurrentSession().update(assignmentMaterial);
		
	}

	@Override
	public void remove(AssignmentMaterial assignmentMaterial) {
		sessionFactory.getCurrentSession().delete(assignmentMaterial);
		
	}

}
