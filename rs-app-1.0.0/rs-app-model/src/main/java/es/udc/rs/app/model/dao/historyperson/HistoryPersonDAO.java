package es.udc.rs.app.model.dao.historyperson;

import java.util.List;

import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.ProfessionalCategory;

public interface HistoryPersonDAO {
	
	public Long create(HistoryPerson historyPerson);
	
	public HistoryPerson find(Long id);
	
	public List<HistoryPerson> findAll();
	
	public List<HistoryPerson> findCurrents();
	
	public List<HistoryPerson> findByPerson(Person person);
	
	public List<HistoryPerson> findByProfCatg(ProfessionalCategory profcatg);
	
	public boolean historyPersonExists(Long id);
	
	public void update(HistoryPerson historyPerson);
	
	public void remove(HistoryPerson historyPerson);	

}
