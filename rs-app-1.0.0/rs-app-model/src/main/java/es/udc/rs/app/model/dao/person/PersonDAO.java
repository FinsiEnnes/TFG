package es.udc.rs.app.model.dao.person;

import java.util.List;

import es.udc.rs.app.model.domain.Person;

public interface PersonDAO {
	
	public Long create(Person person);
	
	public Person find(Long id);
	
	public List<Person> findAll();
	
	public List<Person> findAll(int first, int count);
	
	public Person findByNif(String nif);
	
	public List<Person> findByName(String name, String surname1, String surname2);
	
	public int getTotalPersons();
	
	public boolean personExists(Long id);
	
	public void update(Person person);
	
	public void remove(Person person);	

}
