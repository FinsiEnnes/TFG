package es.udc.rs.app.model.dao.aptitude;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.Person;

@Repository
public class AptitudeDAOImpl implements AptitudeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Aptitude aptitude) {
		
		if (aptitude.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(aptitude);
		
		return id;
	}

	@Override
	public Aptitude find(Long id) {
		
		Aptitude aptitude = (Aptitude) sessionFactory.getCurrentSession().get(Aptitude.class, id);
		
		return aptitude;
	}

	@Override
	public List<Aptitude> findAll() {
		
		// Create the query.
		String query = "FROM Aptitude A ORDER BY A.person ASC";
				
		// Get the timeoffs
		@SuppressWarnings("unchecked")
		List<Aptitude> aptitudes = (List<Aptitude>) sessionFactory.getCurrentSession().createQuery(query).list();
				
		return aptitudes;
	}

	@Override
	public List<Aptitude> findByPerson(Person person) {
		
		// Create the query.
		String queryString  = "FROM Aptitude A WHERE person = :person ORDER BY A.name ASC";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("person", person);
		
		// Get the timeoffs
		@SuppressWarnings("unchecked")
		List<Aptitude> aptitudes = (List<Aptitude>) query.list();
		
		return aptitudes;
	}

	@Override
	public boolean aptitudeExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Aptitude.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());

		// Get the number of rows.
		Long count = (Long) criteria.uniqueResult();

		return (count==1L);	
	}

	@Override
	public void update(Aptitude aptitude) {
		
		if (aptitude.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(aptitude);
	}

	@Override
	public void remove(Aptitude aptitude) {
		sessionFactory.getCurrentSession().delete(aptitude);
	}

}
