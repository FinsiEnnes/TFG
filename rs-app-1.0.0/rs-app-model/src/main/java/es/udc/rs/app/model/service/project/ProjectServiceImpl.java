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
import es.udc.rs.app.model.dao.freeday.FreeDayDAO;
import es.udc.rs.app.model.dao.historyperson.HistoryPersonDAO;
import es.udc.rs.app.model.dao.historyproject.HistoryProjectDAO;
import es.udc.rs.app.model.dao.incident.DamageDAO;
import es.udc.rs.app.model.dao.incident.IncidentDAO;
import es.udc.rs.app.model.dao.milestone.MilestoneDAO;
import es.udc.rs.app.model.dao.phase.PhaseDAO;
import es.udc.rs.app.model.dao.predecessor.PredecessorDAO;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.projectfreeday.ProjectFreeDayDAO;
import es.udc.rs.app.model.dao.projectmgmt.ProjectMgmtDAO;
import es.udc.rs.app.model.dao.state.StateDAO;
import es.udc.rs.app.model.dao.task.PriorityDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.dao.taskincident.TaskIncidentDAO;
import es.udc.rs.app.model.dao.tasklink.TaskLinkTypeDAO;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectFreeDay;
import es.udc.rs.app.model.domain.ProjectMgmt;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.util.FindInstanceService;
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
	private ProjectFreeDayDAO projectFreeDayDAO;
	
	@Autowired
	private FreeDayDAO freeDayDAO;
	
	@Autowired
	private ProjectMgmtDAO projectMgmtDAO;
	
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
	private TaskIncidentDAO taskIncidentDAO;
	
	@Autowired
	private PriorityDAO priorityDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private HistoryPersonDAO historyPersonDAO;
	
	@Autowired 
	private TaskLinkTypeDAO taskLinkTypeDAO;
	
	@Autowired 
	private PredecessorDAO predecessorDAO;
	
	// Injection of the service FindInstanceService
	@Autowired
	private FindInstanceService findInstanceService; 
		
	
	// ============================================================================
	// ============================ Validate Instance =============================
	// ============================================================================
	private void validateProject(Project project) throws InputValidationException {
		
		if (project.getBudget() != null) {
			PropertyValidator.validatePositiveInt("budgetProject", project.getBudget());
		}
	}
	
	// ============================================================================
	private void validateTask(Task task) throws InputValidationException  {	
		PropertyValidator.validatePositiveInt("daysPlanTask", task.getDaysPlan());
	}
	
	// ============================================================================
	private void validateTaskIncident(TaskIncident taskIncident) throws InputValidationException  {	
		PropertyValidator.validatePositiveInt("lossIncident", taskIncident.getLoss());
	}
	
	// ============================================================================
	private void validateFreeDay(FreeDay freeDay) throws InputValidationException {
		
		if (freeDay.getWeekDay() != null) {
			PropertyValidator.validateIntWithinRange("weekDay", freeDay.getWeekDay(), 0, 6);
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

		// Check if the project exists
		findInstanceService.findProject(project);
		
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
	// ============================ FreeDay operations ============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createFreeDay(FreeDay freeDay) throws InputValidationException {
		
		Long id;
		
		// First, we validate the object
		validateFreeDay(freeDay);
		
		// If the data is correct, we create the freeDay
		try{
			id = freeDayDAO.create(freeDay);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + freeDay.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public FreeDay findFreeDay(Long id)  throws InstanceNotFoundException {
		
		FreeDay freeDay = null;
		
		// Find the freeDay by id
		try{
			freeDay = freeDayDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the freeDay exists
		if (freeDay == null) {
			throw new InstanceNotFoundException(id, FreeDay.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + freeDay.toString());
		return freeDay;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<FreeDay> findAllFreeDay() {
		
		List<FreeDay> freeDays = new ArrayList<FreeDay>();
		
		// Get all the FreeDays
		try{
			freeDays = freeDayDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + freeDays.size() + " registred FreeDays.");
		return freeDays;
	}
				
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateFreeDay(FreeDay freeDay) throws InputValidationException, InstanceNotFoundException {
		
		// Get the id
		Long id = freeDay.getId();
		
		// Check if the FreeDay exists 
		if (!freeDayDAO.FreeDayExists(id)) {
			throw new InstanceNotFoundException(id, FreeDay.class.getName());
		}
		
		// Now validate the updated FreeDay
		validateFreeDay(freeDay);
		
		// Now we can update the object
		try{
			freeDayDAO.update(freeDay);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.UPDATE + freeDay.toString());
		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeFreeDay(Long id) throws InstanceNotFoundException {
		
		FreeDay freeDay = findFreeDay(id);
		
		try{
			freeDayDAO.remove(freeDay);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Log the result
		log.info(ModelConstants.DELETE + freeDay.toString());
		
	}
	
	
	
	// ============================================================================
	// ======================== ProjectFreeDay operations =========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createProjectFreeDay(ProjectFreeDay projectFreeDay) throws InstanceNotFoundException {

		Long id;
		Long idProject = projectFreeDay.getProject().getId();
		Long idFreeDay = projectFreeDay.getFreeDay().getId();
		
		// First we check if the Project and the FreeDay exist.
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
		
		if (!freeDayDAO.FreeDayExists(idFreeDay)) {
			throw new InstanceNotFoundException(idProject, FreeDay.class.getName());
		}
		
		// We create the ProjectFreeDay
		try{
			id = projectFreeDayDAO.create(projectFreeDay);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + projectFreeDay.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public ProjectFreeDay findProjectFreeDay(Long id) throws InstanceNotFoundException {
		
		ProjectFreeDay projectFreeDay = null;
		
		// Find ProjectFreeDay by id
		try{
			projectFreeDay = projectFreeDayDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the state exists
		if (projectFreeDay == null) {
			throw new InstanceNotFoundException(id, ProjectFreeDay.class.getName());
		}
		
		// Return the result		
		log.info(ModelConstants.FIND_ID + projectFreeDay.toString());
		return projectFreeDay;
		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<ProjectFreeDay> findAllProjectFreeDay() {
		
		List<ProjectFreeDay> projectFreeDays = new ArrayList<ProjectFreeDay>();
		
		// Find all projectFreeDays
		try{
			projectFreeDays = projectFreeDayDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the results
		log.info(ModelConstants.FIND_ALL + projectFreeDays.size() + " registred ProjectFreeDays");
		return projectFreeDays;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<FreeDay> findProjectFreeDayByProject(Project project) {
		
		List<FreeDay> freeDays = new ArrayList<FreeDay>();
		
		// Find projectFreeDays by the idProject
		try{
			freeDays = projectFreeDayDAO.findByProject(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the results
		log.info(ModelConstants.FIND_ALL + freeDays.size() + " registred FreeDays for the "
				 + " Project with idProject[" + project.getId() + "]");
		return freeDays;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateProjectFreeDay(ProjectFreeDay projectFreeDay) throws InstanceNotFoundException {
		
		Long id = projectFreeDay.getId();
		
		if (!projectFreeDayDAO.ProjectFreeDayExists(id)) {
			throw new InstanceNotFoundException(id, ProjectFreeDay.class.getName());
		}
		
		try{
			projectFreeDayDAO.update(projectFreeDay);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + projectFreeDay.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeProjectFreeDay(Long id) throws InstanceNotFoundException {
		
		ProjectFreeDay projectFreeDay = findProjectFreeDay(id);
		
		try{
			projectFreeDayDAO.remove(projectFreeDay);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + projectFreeDay.toString());
		
	}
	
	
	// ============================================================================
	// ========================= ProjectMgmt operations ===========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createProjectMgmt(ProjectMgmt projectMgmt) throws InstanceNotFoundException {
		
		Long idProject = projectMgmt.getProject().getId();
		Long idHPerson = projectMgmt.getHistoryPerson().getId();
		Long id;
		
		// First we check if the project and the HistoryPerson exist
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
		
		if (!historyPersonDAO.historyPersonExists(idHPerson)) {
			throw new InstanceNotFoundException(idHPerson, HistoryProject.class.getName());
		}
		
		// Now create the new object 
		try{
			id = projectMgmtDAO.create(projectMgmt);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + projectMgmt.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public ProjectMgmt findProjectMgmt(Long id) throws InstanceNotFoundException {
		
		ProjectMgmt projectMgmt = null;
		
		// Find the projectMgmt
		try{
			projectMgmt = projectMgmtDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the projectMgmt exists
		if (projectMgmt == null) {
			throw new InstanceNotFoundException(id, ProjectMgmt.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + projectMgmt.toString());
		return projectMgmt;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<ProjectMgmt> findAllProjectMgmt() {
		
		List<ProjectMgmt> projectMgmts = new ArrayList<ProjectMgmt>();
		
		// Find all the ProjectMgmts
		try{
			projectMgmts = projectMgmtDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + projectMgmts.size() + " registred ProjectMgmt");
		return projectMgmts;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<ProjectMgmt> findProjectMgmtByProject(Project project) {
		
		List<ProjectMgmt> projectMgmts = new ArrayList<ProjectMgmt>();
		
		// Find the ProjectMgmts by Project
		try{
			projectMgmts = projectMgmtDAO.findByProject(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + projectMgmts.size() + " registred ProjectMgmt "
				+ "for the Project with idProject[" + project.getId() + "]");
		return projectMgmts;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateProjectMgmt(ProjectMgmt projectMgmt) throws InstanceNotFoundException {
		
		Long id = projectMgmt.getId();
		
		// Check if this ProjectMgmt exists
		if (!projectMgmtDAO.ProjectMgmtExists(id)) {
			throw new InstanceNotFoundException(id, ProjectMgmt.class.getName());
		}
		
		try{
			projectMgmtDAO.update(projectMgmt);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + projectMgmt.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeProjectMgmt(Long id) throws InstanceNotFoundException {
		
		ProjectMgmt projectMgmt = findProjectMgmt(id);
		
		try{
			projectMgmtDAO.remove(projectMgmt);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + projectMgmt.toString());
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
		if (!milestoneDAO.milestoneExists(id)) {
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
	// ========================= TaskIncident operations ==========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createTaskIncident(TaskIncident taskIncident)
			throws InputValidationException, InstanceNotFoundException {

		Long id;
		
		// First check if the Task and the Incident exist.
		findInstanceService.findTask(taskIncident.getTask());
		findInstanceService.findIncident(taskIncident.getIncident());
		
		// Now validate the object TaskIncident
		validateTaskIncident(taskIncident);
		
		// Create the object
		try{
			id = taskIncidentDAO.create(taskIncident);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + taskIncident.toString());
		return id;
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public TaskIncident findTaskIncident(Long id) throws InstanceNotFoundException {
		
		TaskIncident taskIncident = null;
		
		// Find the object
		try{
			taskIncident = taskIncidentDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if this taskIncident exists
		findInstanceService.findTaskIncident(taskIncident);
		
		// Return the result
		log.info(ModelConstants.FIND_ID + taskIncident.toString());
		return taskIncident;
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<TaskIncident> findTaskIncidentByTask(Task task) throws InstanceNotFoundException {
		
		List<TaskIncident> taskIncidents = new ArrayList<TaskIncident>();
		
		// Check if the Task exists
		findInstanceService.findTask(task);
		
		// Find the TaskIncident by Task
		try{
			taskIncidents = taskIncidentDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + taskIncidents.size() + " registred TaskIncidents for the"
				+ " Task with idTask[" + task.getId() + "]");
		return taskIncidents;	
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<TaskIncident> findTaskIncidentByProject(Project project) throws InstanceNotFoundException {
		
		List<TaskIncident> taskIncidents = new ArrayList<TaskIncident>();
		
		// Check if the Project exists
		findInstanceService.findProject(project);
		
		// Find the TaskIncident by Project
		try{
			taskIncidents = taskIncidentDAO.findByProject(project);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + taskIncidents.size() + " registred TaskIncidents for the"
				+ " Project with idProject[" + project.getId() + "]");
		return taskIncidents;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateTaskIncident(TaskIncident taskIncident)
			throws InputValidationException, InstanceNotFoundException {
		
		// Check if the object exists
		findInstanceService.findTaskIncident(taskIncident);
		
 		// Validate the object TaskIncident
		validateTaskIncident(taskIncident);
		
		// Update the object
		try{
			taskIncidentDAO.update(taskIncident);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.UPDATE + taskIncident.toString());
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeTaskIncident(Long id) throws InstanceNotFoundException {
		
		TaskIncident taskIncident = findTaskIncident(id);
		
		// Remove the object
		try{
			taskIncidentDAO.remove(taskIncident);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.DELETE + taskIncident.toString());
		
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
	// ============================= Task operations ==============================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createTask(Task task) throws InputValidationException, InstanceNotFoundException {
		
		Long id = null;
		Long idPhase = task.getPhase().getId();
		Long idHPerson = task.getHistoryPerson().getId();
		
		// First validate the Task
		validateTask(task);
		
		// Check if exist the Phase and the responsible of the Task
		if (!phaseDAO.PhaseExists(idPhase)) {
			throw new InstanceNotFoundException(idPhase, Phase.class.getName());
		}
		
		if (!historyPersonDAO.historyPersonExists(idHPerson)) {
			throw new InstanceNotFoundException(idHPerson, HistoryPerson.class.getName());
		}
		
		// Now we create the Task in the db
		try{
			id = taskDAO.create(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + task.toString());
		return id;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Task findTask(Long id) throws InstanceNotFoundException {
		
		Task task = null;
		
		// Find the Task by id
		try{
			task = taskDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if the Task exists
		if (task == null) {
			throw new InstanceNotFoundException(id, Task.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + task.toString());
		return task;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateTask(Task task) throws InputValidationException, InstanceNotFoundException {
		
		Long id = task.getId();
		
		// First validate the Task
		validateTask(task);
		
		// Check if the Task exists
		if (!taskDAO.TaskExists(id)) {
			throw new InstanceNotFoundException(id, Task.class.getName());
		}
		
		// Now we update the Task in the db
		try{
			taskDAO.update(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + task.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeTask(Long id) throws InstanceNotFoundException {
		
		// Get the Task by id
		Task task = findTask(id);
		
		// Remove the Task
		try{
			taskDAO.remove(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + task.toString());
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
	
	
	// ============================================================================
	// ========================= Predecessor operations ===========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createPredecessor(Predecessor predecessor) throws InstanceNotFoundException {
		
		// Get the ids of the Tasks
		Long idTask = predecessor.getTask().getId();
		Long idTaskPred = predecessor.getTaskPred().getId();
		Long idPred;
		
		// Checks if these Tasks exist
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());	
		}
		
		if (!taskDAO.TaskExists(idTaskPred)) {
			throw new InstanceNotFoundException(idTaskPred, Task.class.getName());	
		}
		
		// Now create the Predecessor object
		try{
			idPred = predecessorDAO.create(predecessor);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + predecessor.toString());
		return idPred;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Predecessor findPredecessor(Long id) throws InstanceNotFoundException {
		
		Predecessor predecessor = null;
		
		// Find the object
		try{
			predecessor = predecessorDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if it exists
		if (predecessor == null) {
			throw new InstanceNotFoundException(id, Predecessor.class.getName());	
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + predecessor.toString());
		return predecessor;
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Predecessor> findPredecessorByTask(Task task) {
		
		List<Predecessor> predecessors = new ArrayList<Predecessor>();
		
		// Find Predecessors by Task
		try{
			predecessors = predecessorDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + predecessors.size() + " Predecessors whose main Task has idTask["
				 + task.getId() + "]");
		return predecessors;
	}
					
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updatePredecessor(Predecessor predecessor) throws InstanceNotFoundException {
		
		Long id = predecessor.getId();
		
		// Check if the Predecessor exists
		if (!predecessorDAO.PredecessorExists(id)) {
			throw new InstanceNotFoundException(id, Predecessor.class.getName());
		}
		
		// Now update the Predecessor
		try{
			predecessorDAO.update(predecessor);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.UPDATE + predecessor.toString());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removePredecessor(Long id) throws InstanceNotFoundException {
		
		Predecessor predecessor = findPredecessor(id);
		
		// Now update the Predecessor
		try{
			predecessorDAO.remove(predecessor);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.DELETE + predecessor.toString());	
	}

}
