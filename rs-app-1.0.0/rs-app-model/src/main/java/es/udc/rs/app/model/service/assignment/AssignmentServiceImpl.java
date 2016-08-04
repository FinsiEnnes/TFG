package es.udc.rs.app.model.service.assignment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.assignmentprofile.AssignmentProfileDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.util.ModelConstants;
import es.udc.rs.app.validation.PropertyValidator;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	static Logger log = Logger.getLogger("project");
	
	// ============================================================================
	// ============================== DAO Injection ===============================
	// ============================================================================
	@Autowired
	private AssignmentProfileDAO assigProfDAO;
	
	// Assistant DAOs
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private ProfessionalCategoryDAO profCatgDAO;
	
	
	// ============================================================================
	// ============================ Validate Instance =============================
	// ============================================================================
	private void validateAssignmentProfile(AssignmentProfile assignmentProfile) throws InputValidationException {
		
		PropertyValidator.validatePositiveInt("unitsAssigProf", assignmentProfile.getUnits());
		PropertyValidator.validatePositiveInt("hoursPerPersonAssigProf", assignmentProfile.getHoursPerPerson());		
	}
	
	
	// ============================================================================
	// ======================= AssignmentProfile operations =======================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createAssignmentProfile(AssignmentProfile assignmentProfile)
			throws InstanceNotFoundException, InputValidationException {
		
		Long id;
		Long idTask = assignmentProfile.getTask().getId();
		Long idProfCatg = assignmentProfile.getProfCatg().getId();
		
		// First we validate the AssignmentProfile
		validateAssignmentProfile(assignmentProfile);
		
		// Check if the referenced Task and ProfCatg exists
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());
		}
		
		if (!profCatgDAO.ProfessionalCategoryExists(idProfCatg)) {
			throw new InstanceNotFoundException(idProfCatg, ProfessionalCategory.class.getName());
		}
		
		// If the data is correct, we create the project
		try{
			id = assigProfDAO.create(assignmentProfile);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + assignmentProfile.toString());
		return id;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public AssignmentProfile findAssignmentProfile(Long id) throws InstanceNotFoundException {
		
		AssignmentProfile assigProf = null;
		
		// Find the assignmentProfile by id
		try{
			assigProf = assigProfDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if that exists
		if (assigProf == null) {
			throw new InstanceNotFoundException(id, AssignmentProfile.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + assigProf.toString());
		return assigProf;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<AssignmentProfile> findAssignmentProfileByTask(Task task) {
		
		List<AssignmentProfile> assigProfs = new ArrayList<AssignmentProfile>();
		
		// Find assignmentProfiles by task
		try{
			assigProfs = assigProfDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}

		// Return the result
		log.info(ModelConstants.FIND_ALL + assigProfs.size() + " registred AssignmentProfile"
				 + " associated at the task with idTask[" + task.getId() + "]");
		return assigProfs;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateAssignmentProfile(AssignmentProfile assignmentProfile)
			throws InstanceNotFoundException, InputValidationException {
		
		Long id = assignmentProfile.getId();
		
		// First check if the AssignmentProfile exists
		if (!assigProfDAO.AssignmentProfileExists(id)) {
			throw new InstanceNotFoundException(id, AssignmentProfile.class.getName());
		}
		
		// If it exist, we must check the attributes
		validateAssignmentProfile(assignmentProfile);
		
		// Now update 
		try{
			assigProfDAO.update(assignmentProfile);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Inform by log
		log.info(ModelConstants.UPDATE + assignmentProfile.toString());	
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeAssignmentProfile(Long id) throws InstanceNotFoundException {
		
		AssignmentProfile assigProf = findAssignmentProfile(id);
		
		// Delete the found AssignmentProfile
		try{
			assigProfDAO.remove(assigProf);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Inform by log
		log.info(ModelConstants.DELETE + assigProf.toString());	
	}

}
