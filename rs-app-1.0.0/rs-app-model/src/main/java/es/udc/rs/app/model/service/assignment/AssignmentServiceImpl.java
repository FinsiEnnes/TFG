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
import es.udc.rs.app.model.dao.assignmentmaterial.AssignmentMaterialDAO;
import es.udc.rs.app.model.dao.assignmentprofile.AssignmentProfileDAO;
import es.udc.rs.app.model.dao.material.MaterialDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.Material;
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
	
	@Autowired 
	private AssignmentMaterialDAO assigMatDAO;
	
	// Assistant DAOs
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private ProfessionalCategoryDAO profCatgDAO;
	
	@Autowired
	private MaterialDAO materialDAO;
		
	
	// ============================================================================
	// ============================ Validate Instance =============================
	// ============================================================================
	private void validateAssignmentProfile(AssignmentProfile assignmentProfile) throws InputValidationException {	
		PropertyValidator.validatePositiveInt("unitsAssigProf", assignmentProfile.getUnits());
		PropertyValidator.validatePositiveInt("hoursPerPersonAssigProf", assignmentProfile.getHoursPerPerson());		
	}
	
	private void validateAssignmentMaterial(AssignmentMaterial assigMat) throws InputValidationException {
		
		if (assigMat.getUnitsPlan() != null) {
			PropertyValidator.validatePositiveInt("unitsPlanAssigMat", assigMat.getUnitsPlan());
		}
		
		if (assigMat.getUnitsReal() != null) {
			PropertyValidator.validatePositiveInt("unitsRealAssigMat", assigMat.getUnitsReal());
		}		
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

	
	// ============================================================================
	// ======================= AssignmentMaterial operations ======================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createAssignmentMaterial(AssignmentMaterial assignmentMaterial) 
			throws InstanceNotFoundException, InputValidationException {
		
		// Get the ids
		Long idTask = assignmentMaterial.getTask().getId();
		Long idMaterial = assignmentMaterial.getMaterial().getId();
		Long id;
		
		// First check if the Task and the Material exists
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());
		}
		
		if (!materialDAO.MaterialExists(idMaterial)) {
			throw new InstanceNotFoundException(idMaterial, Material.class.getName());
		}
		
		// Validate the data assignmentMaterial
		validateAssignmentMaterial(assignmentMaterial);
		
		// If it is correct, now create the assignmentMaterial
		try{
			id = assigMatDAO.create(assignmentMaterial);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + assignmentMaterial.toString());
		return id;		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public AssignmentMaterial findAssignmentMaterial(Long id) throws InstanceNotFoundException {
		
		AssignmentMaterial assigMaterial = null;
		
		// Find the AssignmentMaterial by id
		try{
			assigMaterial = assigMatDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if it exists
		if (assigMaterial == null) {
			throw new InstanceNotFoundException(id, AssignmentMaterial.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + assigMaterial.toString());
		return assigMaterial;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<AssignmentMaterial> findAssignmentMaterialByTask(Task task) {
		
		List<AssignmentMaterial> assigMaterials = new ArrayList<AssignmentMaterial>();
		
		// Find by task
		try{
			assigMaterials = assigMatDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + assigMaterials.size() + " AssignmentMaterial in the Task"
				+ " with idTask[" + task.getId() + "]");
		return assigMaterials;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<AssignmentMaterial> findAssignmentMaterialByTaskPlan(Task task) {
		
		List<AssignmentMaterial> assigMaterials = new ArrayList<AssignmentMaterial>();
		
		// Find by task
		try{
			assigMaterials = assigMatDAO.findByTaskPlan(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + assigMaterials.size() + " AssignmentMaterial with "
				+ "unitsPlanAssigMat[true] and unitsRealAssigMat[false]" + " in the Task with idTask[" 
				+ task.getId() + "]");
		return assigMaterials;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<AssignmentMaterial> findAssignmentMaterialByTaskReal(Task task) {
		
		List<AssignmentMaterial> assigMaterials = new ArrayList<AssignmentMaterial>();
		
		// Find by task
		try{
			assigMaterials = assigMatDAO.findByTaskReal(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + assigMaterials.size() + " AssignmentMaterial with "
				+ "unitsPlanAssigMat[false] and unitsRealAssigMat[true]" + "in the Task with idTask[" 
				+ task.getId() + "]");
		return assigMaterials;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateAssignmentMaterial(AssignmentMaterial assignmentMaterial) 
		throws InstanceNotFoundException, InputValidationException {
		
		// Get the ids
		Long idTask = assignmentMaterial.getTask().getId();
		Long idMaterial = assignmentMaterial.getMaterial().getId();
		Long id = assignmentMaterial.getId();
		
		// First check if the Task and the Material exists
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());
		}
		
		if (!materialDAO.MaterialExists(idMaterial)) {
			throw new InstanceNotFoundException(idMaterial, Material.class.getName());
		}
		
		// Validate the data assignmentMaterial
		validateAssignmentMaterial(assignmentMaterial);
		
		// Check if the AssignmentMaterial exists
		if (!assigMatDAO.AssignmentMaterialExists(id)) {
			throw new InstanceNotFoundException(id, AssignmentMaterial.class.getName());
		}
		
		// Update
		try{
			assigMatDAO.update(assignmentMaterial);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + assignmentMaterial.toString());
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void remove(Long id) throws InstanceNotFoundException {
		
		AssignmentMaterial assigMat = findAssignmentMaterial(id);
		
		// Remove
		try{
			assigMatDAO.remove(assigMat);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + assigMat.toString());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
