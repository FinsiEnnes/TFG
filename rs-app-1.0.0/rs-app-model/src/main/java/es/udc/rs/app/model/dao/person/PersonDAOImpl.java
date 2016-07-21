package es.udc.rs.app.model.dao.person;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Person;

@Repository
public class PersonDAOImpl implements PersonDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Person person) {		
		if (person.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}

		Long id = (Long) sessionFactory.getCurrentSession().save(person);
		return id;
	}

	@Override
	public Person find(Long id) {
		
		Person person = (Person) sessionFactory.getCurrentSession().get(Person.class, id);
		
		return person;
	}

	@Override
	public List<Person> findAll() {

		// Create the query.
		String query = "FROM Person P ORDER BY P.surname1 ASC";
		
		// Get the persons
		@SuppressWarnings("unchecked")
		List<Person> persons = (List<Person>) sessionFactory.getCurrentSession().createQuery(query).list();
		
		return persons;
	}
	
	@Override
	public Person findByNif(String nif) {
		
		// Create the criteria in function of the attributes.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
		criteria.add(Restrictions.eq("nif", nif));
				
		// Now gets the results.
		Person person = (Person) criteria.list().get(0);

		return person;
	}

	@Override
	public List<Person> findByName(String name, String surname1, String surname2) {
		
		// Create the criteria in function of the attributes.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
		criteria.add(Restrictions.eq("name", name));
		
		if (surname1!=null) { 
			criteria.add(Restrictions.eq("surname1", surname1));
		}
		
		if (surname2!=null) { 
			criteria.add(Restrictions.eq("surname2", surname2));
		}
		
		// Now gets the results.
		@SuppressWarnings("unchecked")
		List<Person> persons = criteria.list();

		return persons;
	}

	@Override
	public boolean personExists(Long id) {
		
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);		
	}
	
	
	@Override
	public void update(Person person) {
		
		if (person.getId() == null) {
			throw new RuntimeException("The object is no persistent.");
		}
		
		// Now we can update.
		sessionFactory.getCurrentSession().update(person);	
	}

	@Override
	public void remove(Person person) {
		sessionFactory.getCurrentSession().delete(person);
	}

}
