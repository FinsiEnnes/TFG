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
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.state.StateDAO;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.State;
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

}
