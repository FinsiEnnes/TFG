package es.udc.rs.app.model.service.person;

import java.util.List;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;

public interface PersonService {
	
	// ============================ Person operations =============================
	public Long createPerson(Person person) throws InputValidationException;

	public Person findPerson(Long id) throws InstanceNotFoundException;
	
	public List<Person> findAllPersons();
	
	public Person findPersonByNif(String nif) throws InstanceNotFoundException, InputValidationException;
	
	public List<Person> findPersonsByName(String name, String surname1, String surname2) 
			throws InputValidationException;
	
	public void updatePerson(Person person) throws InstanceNotFoundException, InputValidationException;
	
	public void removePerson(Long id) throws InstanceNotFoundException;	
	
	
	// ============================ TimeOff operations ============================
	public Long createTimeOff(TimeOff timeoff) throws InstanceNotFoundException;
	
	public TimeOff findTimeOff(Long id) throws InstanceNotFoundException;
	
	public List<TimeOff> findAllTimeOffs();
	
	public List<TimeOff> findTimeOffByPerson(Person person) throws InstanceNotFoundException;
	
	public void updateTimeOff(TimeOff timeoff) throws InstanceNotFoundException;
	
	public void removeTimeOff(Long id) throws InstanceNotFoundException;
	
	
	// ============================ Aptitude operations ===========================
	public AptitudeType findAptitudeType(String id) throws InstanceNotFoundException;
	
	public List<AptitudeType> findAllAptitudeTypes();
	
	public Long createAptitude(Aptitude aptitude) throws InstanceNotFoundException, InputValidationException;
	
	public Aptitude findAptitude(Long id) throws InstanceNotFoundException;
	
	public List<Aptitude> findAllAptitudes();
	
	public List<Aptitude> findAptitudeByPerson(Person person) throws InstanceNotFoundException;
	
	public void updateAptitude(Aptitude aptitude) throws InstanceNotFoundException, InputValidationException;
	
	public void removeAptitude(Long id) throws InstanceNotFoundException;
	
	// ====================== ProfessionalCategory operations =====================
	public LevelProfCatg findLevelProfCatg(String id) throws InstanceNotFoundException;
	
	public List<LevelProfCatg> findAllLevelsProfCatg();

}
