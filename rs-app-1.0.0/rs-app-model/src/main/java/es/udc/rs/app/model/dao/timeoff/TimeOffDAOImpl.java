package es.udc.rs.app.model.dao.timeoff;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;

@Repository
public class TimeOffDAOImpl implements TimeOffDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(TimeOff timeoff) {
		
		if (timeoff.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}

		Long id = (Long) sessionFactory.getCurrentSession().save(timeoff);
		return id;
	}

	@Override
	public TimeOff find(Long id) {

		TimeOff timeoff = (TimeOff) sessionFactory.getCurrentSession().get(TimeOff.class, id);
		
		return timeoff;
	}

	@Override
	public List<TimeOff> findAll() {
		
		// Create the query.
		String query = "FROM TimeOff T ORDER BY T.ini ASC";
				
		// Get the timeoffs
		@SuppressWarnings("unchecked")
		List<TimeOff> timeoffs = (List<TimeOff>) sessionFactory.getCurrentSession().createQuery(query).list();
				
		return timeoffs;
	}

	@Override
	public List<TimeOff> findByPerson(Person person) {
		
		// Create the query.
		String queryString  = "FROM TimeOff T WHERE person = :person ORDER BY T.ini ASC";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("person", person);
		
		// Get the timeoffs
		@SuppressWarnings("unchecked")
		List<TimeOff> timeoffs = (List<TimeOff>) query.list();
		
		return timeoffs;
	}
	
	@Override 
	public boolean timeoffExists(Long id) {
		
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TimeOff.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());

		// Get the number of rows.
		Long count = (Long) criteria.uniqueResult();

		return (count==1L);	
	}

	@Override
	public void update(TimeOff timeoff) {
		
		if (timeoff.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		// Now we can update.
		sessionFactory.getCurrentSession().update(timeoff);	
		
	}

	@Override
	public void remove(TimeOff timeoff) {
		sessionFactory.getCurrentSession().delete(timeoff);		
	}

}
