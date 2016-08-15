package es.udc.rs.app.model.dao.freeday;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.FreeDay;

@Repository
public class FreeDayDAOImpl implements FreeDayDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(FreeDay freeDay) {
		
		// Check the persistence of the object
		if (freeDay.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		// Create the object and return the id
		Long id = (Long) sessionFactory.getCurrentSession().save(freeDay);
		return id;
	}

	@Override
	public FreeDay find(Long id) {
		FreeDay freeDay = (FreeDay) sessionFactory.getCurrentSession().get(FreeDay.class, id);
		return freeDay;
	}

	@Override
	public List<FreeDay> findAll() {
				
		// Create the string query
		String queryString = "FROM FreeDay F ORDER BY F.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get and return the result
		@SuppressWarnings("unchecked")
		List<FreeDay> freeDays = (List<FreeDay>) query.list();
		
		return freeDays;
	}

	@Override
	public boolean FreeDayExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FreeDay.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);
	}

	@Override
	public void update(FreeDay freeDay) {
		
		// Check the persistence of the object
		if (freeDay.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Update the object
		sessionFactory.getCurrentSession().update(freeDay);		
	}

	@Override
	public void remove(FreeDay freeDay) {
		sessionFactory.getCurrentSession().delete(freeDay);				
	}

}
