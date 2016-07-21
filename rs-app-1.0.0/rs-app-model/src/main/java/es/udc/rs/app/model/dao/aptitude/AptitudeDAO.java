package es.udc.rs.app.model.dao.aptitude;

import java.util.List;

import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.Aptitude;

public interface AptitudeDAO {
	
	public Long create(Aptitude aptitude);
	
	public Aptitude find(Long id);
	
	public List<Aptitude> findAll();
	
	public List<Aptitude> findByPerson(Person person);
	
	public boolean aptitudeExists(Long id);
	
	public void update(Aptitude aptitude);
	
	public void remove(Aptitude aptitude);

}
