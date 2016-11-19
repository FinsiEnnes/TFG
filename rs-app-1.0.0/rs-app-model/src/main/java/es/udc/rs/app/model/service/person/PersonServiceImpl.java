package es.udc.rs.app.model.service.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.FirstPageElementException;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.aptitude.AptitudeDAO;
import es.udc.rs.app.model.dao.aptitude.AptitudeTypeDAO;
import es.udc.rs.app.model.dao.historyperson.HistoryPersonDAO;
import es.udc.rs.app.model.dao.person.PersonDAO;
import es.udc.rs.app.model.dao.profctg.LevelProfCatgDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.timeoff.TimeOffDAO;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.util.FindInstanceService;
import es.udc.rs.app.model.util.ModelConstants;
import es.udc.rs.app.validation.PropertyValidator;
import es.udc.rs.app.others.AssistantMethods;

@Service
public class PersonServiceImpl implements PersonService{

	static Logger log = Logger.getLogger("project");
	
	// ============================================================================
	// ============================== DAO Injection ===============================
	// ============================================================================
	@Autowired
	private PersonDAO personDAO;
	
	@Autowired
	private TimeOffDAO timeoffDAO;
	
	@Autowired
	private AptitudeDAO aptitudeDAO;
	
	@Autowired
	private AptitudeTypeDAO aptitudeTypeDAO;
	
	@Autowired
	private LevelProfCatgDAO levelProfCatgDAO;
	
	@Autowired
	private ProfessionalCategoryDAO profCatgDAO;
	
	@Autowired
	private HistoryPersonDAO historyPersonDAO;
	
	// Attendance service
	@Autowired
	private FindInstanceService findInstanceService;
	
	
	// ============================================================================
	// ============================ Validate Instance =============================
	// ============================================================================
	private void validatePerson(Person person) throws InputValidationException {

		// Check if the email and nif format is correct.
		PropertyValidator.validateNif("nifPerson", person.getNif());
		PropertyValidator.validateEmail(person.getEmail());
	}
	
	private void validateAptitude(Aptitude aptitude) throws InputValidationException {

		// If the valueApt is not null, it must be between 0 and 10.
		if (aptitude.getValue() != null) {
			PropertyValidator.validateIntWithinRange("valueApt", aptitude.getValue(), 0, 10);
		}
	}
	
	private void validateProfessionalCategory(ProfessionalCategory pc) throws InputValidationException {
		PropertyValidator.validateIntWithinRange("minExpProfCatg", pc.getMinExp(), 0, 50);
		PropertyValidator.validatePositiveInt("salProfCatg", pc.getSal());
		
		if (pc.getSalExtra() != null) {
			PropertyValidator.validatePositiveInt("salExtraProfCatg", pc.getSalExtra());
		}	
	}
	
	private void validateHistoryPerson(HistoryPerson historyPerson) throws InputValidationException {
		PropertyValidator.validatePositiveInt("salHPerson", historyPerson.getSal());
		
		if (historyPerson.getSalExtra() != null) {
			PropertyValidator.validatePositiveInt("salExtraHPerson", historyPerson.getSalExtra());
		}
	}
		
	
	// ============================================================================
	// ============================ Person operations =============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createPerson(Person person) throws InputValidationException {
		
		Long idPerson = null;
		
		// First, we validate the data Person.
		validatePerson(person);
		
		// If the data person is correct, we create the person.
		try{
			idPerson = personDAO.create(person);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.CREATE + person.toString());
		return idPerson;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Person findPerson(Long id) throws InstanceNotFoundException {
		
		Person person = null;
		
		// Find the Person by id.
		try {
			person = personDAO.find(id);
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		// If this Person does not exist, an InstanceNotFoundException is thrown.
		if (person==null) {
			throw new InstanceNotFoundException(id, Person.class.getName());
		}
		
		log.info(ModelConstants.FIND_ID + person.toString());
		return person;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Person> findAllPersons() {

		List<Person> persons = new ArrayList<Person>();
		
		// We get all the Persons in the database.
		try {
			persons = personDAO.findAll();
		} 
		catch (DataAccessException e){
			throw e;
		}

		log.info(ModelConstants.FIND_ALL + persons.size() + " registred persons");
		return persons;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Person> findAllPersons(int pageNumber, int pageSize) throws FirstPageElementException {

		List<Person> persons = new ArrayList<Person>();
		
		// First we need to get the index
		int totalElements = getTotalPersons();
		int firstElement = AssistantMethods.getFirstPageElement(pageNumber, pageSize, totalElements);
		int count = Math.min(pageSize, totalElements - firstElement);
		
		// We get all the Persons in the database.
		try {
			persons = personDAO.findAll(firstElement, count);
		} 
		catch (DataAccessException e){
			throw e;
		}

		log.info(ModelConstants.PAGE + pageNumber + ModelConstants.PAGE_ITEMS + count);
		return persons;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Person findPersonByNif(String nif) throws InputValidationException, InstanceNotFoundException {
		
		Person person = null;
		
		// Check if the nif of the person was defined and is correct.
		PropertyValidator.validateNif("nifPerson", nif);
		
		// We search the persons by the name.
		try {
			person = personDAO.findByNif(nif);
		} 
		catch (DataAccessException e){
			throw e;
		}

		// If this Person does not exist, an InstanceNotFoundException is thrown.
		if (person==null) {
			throw new InstanceNotFoundException("nif="+nif, Person.class.getName());
		}
		
		log.info(ModelConstants.FIND_NIF + person.toString());
		return person;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Person> findPersonsByName(String name, String surname1,
			String surname2) throws InputValidationException {
		
		List<Person> persons = new ArrayList<Person>();
		
		// Check if the name of the person was defined.
		PropertyValidator.validateMandatoryString("name", name);
		
		// We search the persons by the name.
		try {
			persons = personDAO.findByName(name, surname1, surname2);
		} 
		catch (DataAccessException e){
			throw e;
		}

		String fullName = name;
		if (surname1!=null) { fullName=fullName+" "+surname1; }
		if (surname2!=null) { fullName=fullName+" "+surname2; }
		
		log.info("There are a total of persons "+persons.size()+ " with this name ["+fullName+"].");
		return persons;
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public int getTotalPersons() {
		
		int total = 0;
		
		// Get the total
		try {
			total = personDAO.getTotalPersons();
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + total + " registred persons");
		return total;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updatePerson(Person person) throws InstanceNotFoundException, InputValidationException {
		
		// First we must check if this person is registered.
		findInstanceService.findPerson(person);

		// Validate the data Person.
		validatePerson(person);
		
		// Now, we update the data.
		try {
			personDAO.update(person);			
		} 
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + person.toString());
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removePerson(Long id) throws InstanceNotFoundException {
		
		// First we must check if this person is registered.
		Person person = findPerson(id);
		
		// Now, we remove the data.
		try {
			personDAO.remove(person);			
		} 
		catch (DataAccessException e){
			throw e;
		}	
		
		log.info(ModelConstants.DELETE + person.toString());
	}
	
	
	// ============================================================================
	// ============================ TimeOff operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createTimeOff(TimeOff timeoff) throws InstanceNotFoundException {
		
		Long idTimeOff = null;
		
		// First, we must check if the Person exits.
		findInstanceService.findPerson(timeoff.getPerson());
	
		// In this case we don't need to validate the data, so we create the TimeOff.
		try{
			idTimeOff = timeoffDAO.create(timeoff);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.CREATE + timeoff.toString());
		return idTimeOff;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public TimeOff findTimeOff(Long id) throws InstanceNotFoundException {
		
		TimeOff timeoff = null; 
		
		// Find the TimeOff by id.
		try{
			timeoff = timeoffDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the TimeOff exists.
		if (timeoff == null) {
			throw new InstanceNotFoundException(id, TimeOff.class.getName());
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ID + timeoff.toString());
		return timeoff;
		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<TimeOff> findAllTimeOffs() {
		
		List<TimeOff> timeoffs = new ArrayList<TimeOff>();

		// Find all the TimeOff.
		try{
			timeoffs = timeoffDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ALL + timeoffs.size()+ " registred times off.");
		return timeoffs;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<TimeOff> findTimeOffByPerson(Person person) throws InstanceNotFoundException{
		
		List<TimeOff> timeoffs = new ArrayList<TimeOff>();
		
		// First, we must check if the Person exits.
		findInstanceService.findPerson(person);

		// Find TimeOffs by the id person.
		try{
			timeoffs = timeoffDAO.findByPerson(person);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ALL + timeoffs.size()+ " times off for the Person "
				+ "with idPerson[" + person.getId() + "]");
		return timeoffs;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateTimeOff(TimeOff timeoff) throws InstanceNotFoundException {
			
		// Checks if the instance and the Person exist.
		findInstanceService.findTimeoff(timeoff);
		findInstanceService.findPerson(timeoff.getPerson());
		
		try{
			timeoffDAO.update(timeoff);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + timeoff.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeTimeOff(Long id) throws InstanceNotFoundException {
		
		// First we must check if this TimeOff is registered.
		TimeOff timeoff = findTimeOff(id);
		
		// Now, we remove the data.
		try {
			timeoffDAO.remove(timeoff);
		} 
		catch (DataAccessException e){
			throw e;
		}	

		log.info(ModelConstants.DELETE + timeoff.toString());	
	}

	
	// ============================================================================
	// ========================== AptitudeType operations =========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public AptitudeType findAptitudeType(String id) throws InstanceNotFoundException {
		
		AptitudeType aptitudeType = null; 
		
		// Find the AptitudeType by id.
		try{
			aptitudeType = aptitudeTypeDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the AptitudeType exists.
		if (aptitudeType == null) {
			throw new InstanceNotFoundException(id, AptitudeType.class.getName());
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ID + aptitudeType.toString());
		return aptitudeType;
	}
	
	@Override
	@Transactional(value="myTransactionManager")
	public List<AptitudeType> findAllAptitudeTypes() {
		
		List<AptitudeType> aptitudeTypes = new ArrayList<AptitudeType>();
		
		// Find all the AptitudeTypes.
		try{
			aptitudeTypes =  aptitudeTypeDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ALL + aptitudeTypes.size()+ " aptitude types.");
		return aptitudeTypes;
		
	}
	
	
	// ============================================================================
	// ============================ Aptitude operations ===========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createAptitude(Aptitude aptitude) throws InstanceNotFoundException, InputValidationException {
		
		Long idAptitude = null;
		
		// First, we check the Aptitude data
		validateAptitude(aptitude);
		
		// We must check if the Person exits.
		findInstanceService.findPerson(aptitude.getPerson());
	
		// We create the Aptitude.
		try{
			idAptitude = aptitudeDAO.create(aptitude);				
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.CREATE + aptitude.toString());
		return idAptitude;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Aptitude findAptitude(Long id) throws InstanceNotFoundException {
		
		Aptitude aptitude = null; 
		
		// Find the TimeOff by id.
		try{
			aptitude = aptitudeDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the Aptitude exists.
		if (aptitude == null) {
			throw new InstanceNotFoundException(id, Aptitude.class.getName());
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ID + aptitude.toString());
		return aptitude;
		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Aptitude> findAllAptitudes() {
		
		List<Aptitude> aptitudes = new ArrayList<Aptitude>();

		// Find all the Aptitude.
		try{
			aptitudes = aptitudeDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ALL + aptitudes.size()+ " registred aptitudes");
		return aptitudes;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Aptitude> findAptitudeByPerson(Person person) throws InstanceNotFoundException{
		
		List<Aptitude> aptitudes = new ArrayList<Aptitude>();
		
		// First, we must check if the Person exits.
		findInstanceService.findPerson(person);

		// Find aptitudes by the id person.
		try{
			aptitudes = aptitudeDAO.findByPerson(person);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ALL + aptitudes.size()+ " aptitudes for the Person "
				+ "with idPerson[" + person.getId() + "]");
		return aptitudes;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateAptitude(Aptitude aptitude) throws InstanceNotFoundException, InputValidationException {
		
		// Check if the Aptitude and the Person exist.
		findInstanceService.findAptitude(aptitude);
		findInstanceService.findPerson(aptitude.getPerson());
		
		// Now, we check the Aptitude data
		validateAptitude(aptitude);
		
		try{
			aptitudeDAO.update(aptitude);
		}
		catch (DataAccessException e){
			throw e;
		} 

		log.info(ModelConstants.UPDATE + aptitude.toString());
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeAptitude(Long id) throws InstanceNotFoundException {
		
		// First we must check if this TimeOff is registered.
		Aptitude aptitude = findAptitude(id);
		
		// Now, we remove the data.
		try {
			aptitudeDAO.remove(aptitude);
			
		} catch (DataAccessException e){
			throw e;
		}	
		
		log.info(ModelConstants.DELETE + aptitude.toString());
	}
	
	// ============================================================================
	// ========================== LevelProfCatg operations ========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public LevelProfCatg findLevelProfCatg(String id) throws InstanceNotFoundException{
		
		LevelProfCatg lpc = null; 
		
		// Find the LevelProfCatg by id.
		try{
			lpc = levelProfCatgDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the LevelProfCatg exists.
		if (lpc == null) {
			throw new InstanceNotFoundException(id, LevelProfCatg.class.getName());
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ID + lpc.toString());
		return lpc;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<LevelProfCatg> findAllLevelsProfCatg() {
		
		List<LevelProfCatg> lpcs = new ArrayList<LevelProfCatg>();
		
		try{
			lpcs = levelProfCatgDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}

		log.info(ModelConstants.FIND_ALL + lpcs.size() + " aptitude types.");
		return lpcs;
	}
	
	
	// ============================================================================
	// ====================== ProfessionalCategory operations =====================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createProfessionalCategory(ProfessionalCategory profCtg)  
			throws InputValidationException, InstanceNotFoundException {
		
		Long id = null;
		
		// Check if the LevelProfCatg exists
		findInstanceService.findLevelProfCatg(profCtg.getLevel());
		
		// We validate the data.
		validateProfessionalCategory(profCtg);
		
		// Now, we create the ProfessionalCategory.
		try{
			id = profCatgDAO.create(profCtg);			
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.CREATE + profCtg.toString());
		return id;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public ProfessionalCategory findProfessionalCategory(Long id) throws InstanceNotFoundException {
		
		ProfessionalCategory pc = null;
		
		try{
			pc = profCatgDAO.find(id);		
		}
		catch (DataAccessException e){
			throw e;
		}
		
		if (pc == null) {
			throw new InstanceNotFoundException(id, ProfessionalCategory.class.getName());
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ID + pc.toString());
		return pc;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<ProfessionalCategory> findAllProfessionalCategories() {
		
		List<ProfessionalCategory> pcs = new ArrayList<ProfessionalCategory>();
		
		try{
			pcs = profCatgDAO.findAll();	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + pcs.size()+ " registred professional categories.");
		return pcs;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<ProfessionalCategory> findProfessionalCategoryByNameAndLevel(String name, LevelProfCatg level)
		throws InputValidationException, InstanceNotFoundException {
		
		List<ProfessionalCategory> pcs = new ArrayList<ProfessionalCategory>();
		
		// Check the name and level
		if (name == null && level == null) {
			throw new InputValidationException("At least one of the params (name or level) must be not null.");
		}
		
		if (level!=null) {
			findInstanceService.findLevelProfCatg(level);
		}
		
		try{
			pcs = profCatgDAO.findByNameAndLevel(name, level);	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Create the message for the log.
		String msg;
		if (name == null) {
			msg = "level["+level.getName()+"]";
		} else if (level == null) {
			msg = "name["+name+"]";
		} else {
			msg = "name["+name+"] and level["+level.getName()+"]";
		}
		
		log.info(ModelConstants.FIND_ALL + pcs.size()+ " professional categories with "+msg+".");
		return pcs;
	}
		
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateProfessionalCategory(ProfessionalCategory profCtg)  
			throws InstanceNotFoundException, InputValidationException {
		
		// First we check if this professional category exists.
		findInstanceService.findProfessionalCategory(profCtg);
		
		validateProfessionalCategory(profCtg);
		
		// If it exists, we update the object.
		try{
			profCatgDAO.update(profCtg);	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + profCtg.toString());	
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeProfessionalCategory(Long id) throws InstanceNotFoundException {
		
		// First we check if this professional category exists.
		ProfessionalCategory pc = findProfessionalCategory(id);
		
		try{
			profCatgDAO.remove(pc);	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + pc.toString());		
	}
	
	
	// ============================================================================
	// ========================= HistoryPerson operations =========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createHistoryPerson(HistoryPerson historyPerson) 
			throws InputValidationException, InstanceNotFoundException {
		
		Long id;
		validateHistoryPerson(historyPerson);
		
		// Check if the Person exists
		findInstanceService.findPerson(historyPerson.getPerson());
		
		// Now, we create the HistoryPerson.
		try{
			id = historyPersonDAO.create(historyPerson);	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result.
		log.info(ModelConstants.CREATE + historyPerson.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public HistoryPerson findHistoryPerson(Long id) throws InstanceNotFoundException {
		
		HistoryPerson historyPerson = null;
		
		// Find the history person by it id.
		try{
			historyPerson = historyPersonDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Checks if this history person exits.
		if (historyPerson == null) {
			throw new InstanceNotFoundException(id, HistoryPerson.class.getName());
		}
		
		// Return the result.
		log.info(ModelConstants.FIND_ID + historyPerson.toString());
		return historyPerson;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<HistoryPerson> findAllHistoryPerson() {
		
		List<HistoryPerson> hps = new ArrayList<HistoryPerson>();
		
		// Find all person histories.
		try{
			hps = historyPersonDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + hps.size()+ " registred person histories.");
		return hps;		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<HistoryPerson> findCurrentHistoryPersons() {
		
		List<HistoryPerson> hps = new ArrayList<HistoryPerson>();
		
		// Find all person histories.
		try{
			hps = historyPersonDAO.findCurrents();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + hps.size()+ " registred person histories that currently work.");
		return hps;		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager") 
	public List<HistoryPerson> findHistoryPersonByPerson(Person person) throws InstanceNotFoundException {
		
		List<HistoryPerson> hps = new ArrayList<HistoryPerson>();
		
		// Check if the Person exists
		findInstanceService.findPerson(person);
		
		try{
			hps = historyPersonDAO.findByPerson(person);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + hps.size()+ " person histories for the Person"
				+ " with idPerson[" + person.getId() + "]"); 
		return hps;		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager") 
	public List<HistoryPerson> findHistoryPersonByProfCatg(ProfessionalCategory profcatg) 
			throws InstanceNotFoundException {
		
		List<HistoryPerson> hps = new ArrayList<HistoryPerson>();
		
		// Check if the ProfessionalCategory exists
		findInstanceService.findProfessionalCategory(profcatg);
		
		try{
			hps = historyPersonDAO.findByProfCatg(profcatg);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + hps.size()+ 
				 " person histories with this ProfessionalCategory[id:"+profcatg.getId()+"].");
		return hps;	
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager") 
	public void updateHistoryPerson(HistoryPerson historyPerson) throws InstanceNotFoundException {
			
		// Checks if the historyPerson exists.
		findInstanceService.findHistoryPerson(historyPerson);
		
		// Now, we update the HistoryPerson.
		try{
			historyPersonDAO.update(historyPerson);	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Log the result.
		log.info(ModelConstants.UPDATE + historyPerson.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager") 
	public void removeHistoryPerson(Long id) throws InstanceNotFoundException {
		
		HistoryPerson historyPerson = findHistoryPerson(id);
		
		// Now, we delete the HistoryPerson.
		try{
			historyPersonDAO.remove(historyPerson);	
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + historyPerson.toString());
	}

}
