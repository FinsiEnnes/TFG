package es.udc.rs.app.model.dao.timeoff;

import java.util.List;

import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;

public interface TimeOffDAO {
	
	public Long create(TimeOff timeoff);
	
	public TimeOff find(Long id);
	
	public List<TimeOff> findAll();
	
	public List<TimeOff> findByPerson(Person person);
	
	public boolean timeoffExists(Long id);
	
	public void update(TimeOff timeoff);
	
	public void remove(TimeOff timeoff);
	
}
