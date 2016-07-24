package es.udc.rs.app.model.dao.historyperson;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.ProfessionalCategory;

@Repository
public class HistoryPersonDAOImpl implements HistoryPersonDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public Long create(HistoryPerson historyPerson) {
		if (historyPerson.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(historyPerson);
		return id;
	}

	@Override
	public HistoryPerson find(Long id) {
		HistoryPerson hp = (HistoryPerson) sessionFactory.getCurrentSession().get(HistoryPerson.class, id);
		return hp;
	}

	@Override
	public List<HistoryPerson> findAll() {
		
		String query = "FROM HistoryPerson H ORDER BY H.id ASC";

		@SuppressWarnings("unchecked")
		List<HistoryPerson> hps = (List<HistoryPerson>) sessionFactory.getCurrentSession().createQuery(query).list();

		return hps;
	}

	@Override
	public List<HistoryPerson> findByPerson(Person person) {
		// Create the criteria in function of the attributes.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HistoryPerson.class);
		criteria.add(Restrictions.eq("person", person));

		// Now gets the results.
		@SuppressWarnings("unchecked")
		List<HistoryPerson> hps = (List<HistoryPerson>) criteria.list();

		return hps;
	}

	@Override
	public List<HistoryPerson> findByProfCatg(ProfessionalCategory profcatg) {
		// Create the criteria in function of the attributes.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HistoryPerson.class);
		criteria.add(Restrictions.eq("profcatg", profcatg));

		// Now gets the results.
		@SuppressWarnings("unchecked")
		List<HistoryPerson> hps = (List<HistoryPerson>) criteria.list();

		return hps;
	}

	@Override
	public boolean historyPersonExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HistoryPerson.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(HistoryPerson historyPerson) {
		if (historyPerson.getId() != null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(historyPerson);
		
	}

	@Override
	public void remove(HistoryPerson historyPerson) {
		sessionFactory.getCurrentSession().delete(historyPerson);
		
	}

}
