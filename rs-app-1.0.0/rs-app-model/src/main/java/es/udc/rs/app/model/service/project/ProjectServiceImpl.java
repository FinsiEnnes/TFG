package es.udc.rs.app.model.service.project;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.domain.Project;
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
	// ============================ Project operations ============================
	// ============================================================================
	

}
