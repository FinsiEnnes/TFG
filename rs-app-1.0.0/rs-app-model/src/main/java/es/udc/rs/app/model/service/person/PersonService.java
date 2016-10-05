package es.udc.rs.app.model.service.person;

import java.util.List;

import es.udc.rs.app.exceptions.FirstPageElementException;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.TimeOff;

public interface PersonService {
	
	// ============================ Person operations =============================
	public Long createPerson(Person person) throws InputValidationException;

	public Person findPerson(Long id) throws InstanceNotFoundException;
	
	public List<Person> findAllPersons();
	
	public List<Person> findAllPersons(int pageNumber, int pageSize) throws FirstPageElementException;
	
	public Person findPersonByNif(String nif) throws InstanceNotFoundException, InputValidationException;
	
	public List<Person> findPersonsByName(String name, String surname1, String surname2) 
			throws InputValidationException;
	
	public int getTotalPersons();
	
	public void updatePerson(Person person) throws InstanceNotFoundException, InputValidationException;
	
	public void removePerson(Long id) throws InstanceNotFoundException;	
	
	
	// ============================ TimeOff operations ============================
	public Long createTimeOff(TimeOff timeoff) throws InstanceNotFoundException;
	
	public TimeOff findTimeOff(Long id) throws InstanceNotFoundException;
	
	public List<TimeOff> findAllTimeOffs();
	
	public List<TimeOff> findTimeOffByPerson(Person person) throws InstanceNotFoundException;
	
	public void updateTimeOff(TimeOff timeoff) throws InstanceNotFoundException;
	
	public void removeTimeOff(Long id) throws InstanceNotFoundException;
	
	
	// ========================== AptitudeType operations =========================
	public AptitudeType findAptitudeType(String id) throws InstanceNotFoundException;
	
	public List<AptitudeType> findAllAptitudeTypes();
	
	
	// ============================ Aptitude operations ===========================
	public Long createAptitude(Aptitude aptitude) throws InstanceNotFoundException, InputValidationException;
	
	public Aptitude findAptitude(Long id) throws InstanceNotFoundException;
	
	public List<Aptitude> findAllAptitudes();
	
	public List<Aptitude> findAptitudeByPerson(Person person) throws InstanceNotFoundException;
	
	public void updateAptitude(Aptitude aptitude) throws InstanceNotFoundException, InputValidationException;
	
	public void removeAptitude(Long id) throws InstanceNotFoundException;
	
	
	// ========================= LevelProfCatg operations =========================
	public LevelProfCatg findLevelProfCatg(String id) throws InstanceNotFoundException;
	
	public List<LevelProfCatg> findAllLevelsProfCatg();
	
	
	// ====================== ProfessionalCategory operations =====================
	public Long createProfessionalCategory(ProfessionalCategory profCtg)
			throws InstanceNotFoundException, InputValidationException;
	
	public ProfessionalCategory findProfessionalCategory(Long id) throws InstanceNotFoundException;
	
	public List<ProfessionalCategory> findAllProfessionalCategories();
	
	public List<ProfessionalCategory> findProfessionalCategoryByNameAndLevel(String name, LevelProfCatg level)
			throws InstanceNotFoundException, InputValidationException;
		
	public void updateProfessionalCategory(ProfessionalCategory profCtg)  
			throws InstanceNotFoundException, InputValidationException;
	
	public void removeProfessionalCategory(Long id) throws InstanceNotFoundException;
	
	
	// ========================= HistoryPerson operations =========================
	public Long createHistoryPerson(HistoryPerson historyPerson) 
			throws InstanceNotFoundException, InputValidationException;
	
	public HistoryPerson findHistoryPerson(Long id) throws InstanceNotFoundException;
	
	public List<HistoryPerson> findAllHistoryPerson();
	
	public List<HistoryPerson> findHistoryPersonByPerson(Person person) throws InstanceNotFoundException;
	
	public List<HistoryPerson> findHistoryPersonByProfCatg(ProfessionalCategory profcatg) throws InstanceNotFoundException;
	
	public void updateHistoryPerson(HistoryPerson historyPerson) throws InstanceNotFoundException;
	
	public void removeHistoryPerson(Long id) throws InstanceNotFoundException;	

}
