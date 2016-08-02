package es.udc.rs.app.model.service.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.historyproject.HistoryProjectDAO;
import es.udc.rs.app.model.dao.incident.DamageDAO;
import es.udc.rs.app.model.dao.incident.IncidentDAO;
import es.udc.rs.app.model.dao.milestone.MilestoneDAO;
import es.udc.rs.app.model.dao.phase.PhaseDAO;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.state.StateDAO;
import es.udc.rs.app.model.dao.task.PriorityDAO;
import es.udc.rs.app.model.dao.tasklink.TaskLinkTypeDAO;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.util.ModelConstants;
import es.udc.rs.app.validation.PropertyValidator;

@Service
public class ProjectServiceImpl implements ProjectService {

	static Logger log = Logger.getLogger("project");
	
	// ============================================================================
	// ============================== DAO Injection ===============================
	// ============================================================================
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private StateDAO stateDAO;
	
	@Autowired
	private HistoryProjectDAO historyProjectDAO;
	
	@Autowired 
	private PhaseDAO phaseDAO;
	
	@Autowired
	private MilestoneDAO milestoneDAO;
	
	@Autowired
	private DamageDAO damageDAO;
	
	@Autowired
	private IncidentDAO incidentDAO;
	
	@Autowired
	private PriorityDAO priorityDAO;
	
	@Autowired 
	private TaskLinkTypeDAO taskLinkTypeDAO;
	
	
	// ============================================================================
	// ============================ Validate Instance =============================
	// ============================================================================
	private void validateProject(Project project) throws InputValidationException {
		
		if (project.getBudget() != null) {
			PropertyValidator.validatePositiveInt("budgetProject", project.getBudget());
		}
	}

	
	// ============================================================================
	// =========================== Project operations =============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createProject(Project project) throws InputValidationException {
		
		Long id;
		
		// Validate the project data
		validateProject(project);
		
		// If the data is correct, we create the project
		try{
			id = projectDAO.create(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + project.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Project findProject(Long id) throws InstanceNotFoundException {
		
		Project project = null;
		
		try{
			project = projectDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		if (project == null) {
			throw new InstanceNotFoundException(id, Project.class.getName());
		}
		
		log.info(ModelConstants.FIND_ID + project.toString());
		return project;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Project findProjectByName(String name) throws InstanceNotFoundException {
		Project project = null;
		
		try{
			project = projectDAO.findByName(name);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		if (project == null) {
			throw new InstanceNotFoundException("name:"+name, Project.class.getName());
		}
		
		log.info(ModelConstants.FIND_NAME + project.toString());
		return project;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateProject(Project project) throws InputValidationException, InstanceNotFoundException {

		// Validate the project data
		validateProject(project);

		if (!projectDAO.ProjectExists(project.getId())) {
			throw new InstanceNotFoundException(project.getId(), Project.class.getName());
		}
		
		// If the data is correct, we update the project
		try{
			projectDAO.update(project);
		}
		catch (DataAccessException e){
			throw e;
		}

		log.info(ModelConstants.UPDATE + project.toString());
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeProject(Long id) throws InstanceNotFoundException {
		
		Project project = findProject(id);
		
		try{
			projectDAO.remove(project);
		}
		catch (DataAccessException e){
			throw e;
		}

		log.info(ModelConstants.DELETE + project.toString());	
	}
	
	
	// ============================================================================
	// ============================= State operations =============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public State findState(String id) throws InstanceNotFoundException {
		
		State state = null;
		
		// Find state by id
		try{
			state = stateDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the state exists
		if (state == null) {
			throw new InstanceNotFoundException(id, State.class.getName());
		}
		
		// Return the result		
		log.info(ModelConstants.FIND_ID + state.toString());
		return state;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<State> findAllState() {
		
		List<State> states = new ArrayList<State>();
		
		// Find all states
		try{
			states = stateDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the results
		log.info(ModelConstants.FIND_ALL + states.size() + " registred states");
		return states;
	}
	
	// ============================================================================
	// ======================== HistoryProject operations =========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createHistoryProject(HistoryProject historyProject) throws InstanceNotFoundException {
		
		Long idProject = historyProject.getProject().getId();
		Long idHistoryProject = null;
		
		// Check if the selected project exists
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
		
		// We create the history project
		try{
			idHistoryProject = historyProjectDAO.create(historyProject);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + historyProject.toString());
		return idHistoryProject;		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public HistoryProject findHistoryProject(Long id) throws InstanceNotFoundException {
		
		HistoryProject historyProject = null;
		
		// Find the history project by id
		try{
			historyProject = historyProjectDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Checks if exists 
		if (historyProject == null) {
			throw new InstanceNotFoundException(id, HistoryProject.class.getName());
		}
		
		log.info(ModelConstants.FIND_ID + historyProject.toString());
		return historyProject;
	}
		
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<HistoryProject> findHistoryProjectByProject(Project project) throws InstanceNotFoundException {
		
		List<HistoryProject> historiesProject = new ArrayList<HistoryProject>();
		Long idProject = project.getId();
		
		// Check if the selected project exists
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
		
		try{
			historiesProject = historyProjectDAO.findByProject(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + historiesProject.size() + " HistoryProject's with idProject["
				 + project.getId() + "]");
		return historiesProject;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public HistoryProject findCurrentHistoryProject(Project project) {
		
		HistoryProject historyProject = null;
		
		// Find the history project by id
		try{
			historyProject = historyProjectDAO.findCurrentHistoryProject(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info("Current HistoryProject for idProject[" + project.getId() + "]:" + historyProject.toString());
		return historyProject;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateHistoryProject(HistoryProject historyProject) throws InstanceNotFoundException {
		
		if (!historyProjectDAO.HistoryProjectExists(historyProject.getId())) {
			throw new InstanceNotFoundException(historyProject.getId(), HistoryProject.class.getName());
		}
		
		try{
			historyProjectDAO.update(historyProject);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + historyProject.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeHistoryProject(Long id) throws InstanceNotFoundException {
		
		HistoryProject historyProject = findHistoryProject(id);
		
		try{
			historyProjectDAO.remove(historyProject);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + historyProject.toString());
	}
	
	
	// ============================================================================
	// ============================ Phase operations ==============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createPhase(Phase phase) throws InstanceNotFoundException {
		
		Long idProject = phase.getProject().getId();
		Long idPhase = null;
		
		// Check if the selected project exists
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
		
		// Now we create the new project phase
		try{
			idPhase = phaseDAO.create(phase);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + phase.toString());
		return idPhase;	
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Phase findPhase(Long id) throws InstanceNotFoundException {
		
		Phase phase = null;
		
		// Find the phase by id
		try{
			phase = phaseDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if there is any result
		if (phase == null) {
			throw new InstanceNotFoundException(id, Phase.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + phase.toString());
		return phase;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Phase> findPhaseByProject(Project project) {
		
		// Initialize the phase list
		List<Phase> phases = new ArrayList<Phase>();
		
		// Get the phases of the project
		try{
			phases = phaseDAO.findByProject(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + phases.size() + " phases in the project with idProject[" 
				 + project.getId() + "]");
		return phases;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updatePhase(Phase phase) throws InstanceNotFoundException {
		
		Long id = phase.getId();
		
		// Check if the phase exists
		if (!phaseDAO.PhaseExists(id)) {
			throw new InstanceNotFoundException(id, Phase.class.getName());
		}
		
		// Update
		try{
			phaseDAO.update(phase);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + phase.toString());
		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removePhase(Long id) throws InstanceNotFoundException {
		
		// Get the Phase
		Phase phase = findPhase(id);
		
		// Remove the phase
		try{
			phaseDAO.remove(phase);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + phase.toString());
	}
	
	
	// ============================================================================
	// =========================== Milestone operations ===========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createMilestone(Milestone milestone) throws InstanceNotFoundException {
		
		Long idPhase = milestone.getPhase().getId();
		Long idMilestone = null;
		
		// Check if the selected phase exists
		if (!phaseDAO.PhaseExists(idPhase)) {
			throw new InstanceNotFoundException(idPhase, Phase.class.getName());
		}
		
		// Now we create the new milestone
		try{
			idMilestone = milestoneDAO.create(milestone);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + milestone.toString());
		return idMilestone;	
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")	
	public Milestone findMilestone(Long id) throws InstanceNotFoundException {
		
		Milestone milestone = null; 
		
		// Find the milestone
		try{
			milestone = milestoneDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the milestone exists
		if (milestone == null) {
			throw new InstanceNotFoundException(id, Milestone.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + milestone.toString());
		return milestone;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateMilestone(Milestone milestone) throws InstanceNotFoundException {
		
		Long id = milestone.getId();
		
		// First we check if the milestone exists
		if (!milestoneDAO.MilestoneExists(id)) {
			throw new InstanceNotFoundException(id, Milestone.class.getName());
		}
		
		// Update the milestone
		try{
			milestoneDAO.update(milestone);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + milestone.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeMilestone(Long id) throws InstanceNotFoundException {
		
		// Find the milestone by id
		Milestone milestone = milestoneDAO.find(id);
		
		// Remove the milestone
		try{
			milestoneDAO.remove(milestone);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + milestone.toString());
	}
	
	
	// ============================================================================
	// ============================ Damage operations =============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Damage findDamage(String id) throws InstanceNotFoundException {
		
		Damage damage = null;
		
		// Find the Damage by id
		try{
			damage = damageDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Checks if the Damage exits
		if (damage == null) {
			throw new InstanceNotFoundException(id, Damage.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + damage.toString());
		return damage;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Damage> findAllDamage() {
		
		List<Damage> damages = new ArrayList<Damage>();
		
		// Find all the Damages
		try{
			damages = damageDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + damages.size() + " registred Damages");
		return damages;		
	}
	
	
	// ============================================================================
	// =========================== Incident operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createIncident(Incident incident) {
		
		Long id = null;
		
		// Create the incident
		try{
			id = incidentDAO.create(incident);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + incident.toString());
		return id;
		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Incident findIncident(Long id) throws InstanceNotFoundException {
		
		Incident incident = null;
		
		// Find the incident 
		try{
			incident = incidentDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the incident exists
		if (incident == null) {
			throw new InstanceNotFoundException(id, Incident.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + incident.toString());
		return incident;
		
	}
		
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateIncident(Incident incident) throws InstanceNotFoundException {
		
		Long id = incident.getId();
		
		// Check if the Incident exists
		if (!incidentDAO.IncidentExists(id)) {
			throw new InstanceNotFoundException(id, Incident.class.getName());
		}
		
		// If the incident exists, we update it
		try{
			incidentDAO.update(incident);
		}
		catch (DataAccessException e){
			throw e;
		}
				
		log.info(ModelConstants.UPDATE + incident.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeIncident(Long id) throws InstanceNotFoundException {
		
		// Get the incident
		Incident incident = findIncident(id);
		
		// Remove the incident
		try{
			incidentDAO.remove(incident);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + incident.toString());	
	}
	
	
	// ============================================================================
	// =========================== Priority operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Priority findPriority(String id) throws InstanceNotFoundException {
		
		Priority priority = null;
		
		// Find the Priority
		try{
			priority = priorityDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the Priority exists
		if (priority == null) {
			throw new InstanceNotFoundException(id, Priority.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + priority.toString());
		return priority;	
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Priority> findAllPriority() {
		
		List<Priority> priorities = new ArrayList<Priority>();
		
		// Find all the Priorities
		try{
			priorities = priorityDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + priorities.size() + " registred Priorities");
		return priorities;	
	}
	
	// ============================================================================
	// ========================= TaskLinkType operations ==========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public TaskLinkType findTaskLinkType(String id) throws InstanceNotFoundException {

		TaskLinkType tlt = null;
		
		// Find the TaskLinkType
		try{
			tlt = taskLinkTypeDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the TaskLinkType exists
		if (tlt == null) {
			throw new InstanceNotFoundException(id, TaskLinkType.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + tlt.toString());
		return tlt;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<TaskLinkType> findAllTaskLinkType() {
		
		List<TaskLinkType> tlts = new ArrayList<TaskLinkType>();
		
		// Find all the TaskLinkTypes
		try{
			tlts = taskLinkTypeDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + tlts.size() + " registred TaskLinkTypes");
		return tlts;	
	}

}
