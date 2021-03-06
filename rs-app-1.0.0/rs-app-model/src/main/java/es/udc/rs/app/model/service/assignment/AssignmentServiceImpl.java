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
import es.udc.rs.app.model.dao.assignmentperson.AssignmentPersonDAO;
import es.udc.rs.app.model.dao.assignmentprofile.AssignmentProfileDAO;
import es.udc.rs.app.model.dao.historyperson.HistoryPersonDAO;
import es.udc.rs.app.model.dao.material.MaterialDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.dao.workload.WorkloadDAO;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.Workload;
import es.udc.rs.app.model.util.FindInstanceService;
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
	
	@Autowired
	private AssignmentPersonDAO assigPersonDAO;
	
	@Autowired 
	private WorkloadDAO workloadDAO;
	
	// Assistant DAOs
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private ProfessionalCategoryDAO profCatgDAO;
	
	@Autowired
	private MaterialDAO materialDAO;
	
	@Autowired
	private HistoryPersonDAO historyPersonDAO;
	
	// Assistant service
	@Autowired
	private FindInstanceService findInstanceService;
		
	
	// ============================================================================
	// ============================ Validate Instance =============================
	// ============================================================================
	private void validateAssignmentProfile(AssignmentProfile assignmentProfile) 
			throws InputValidationException {	
		PropertyValidator.validatePositiveInt("unitsAssigProf", assignmentProfile.getUnits());
		PropertyValidator.validatePositiveInt("hoursPerPersonAssigProf", assignmentProfile.getHoursPerPerson());		
	}
	
	// ============================================================================
	private void validateAssignmentMaterial(AssignmentMaterial assigMat) throws InputValidationException {
		
		if (assigMat.getUnitsPlan() != null) {
			PropertyValidator.validatePositiveInt("unitsPlanAssigMat", assigMat.getUnitsPlan());
		}
		
		if (assigMat.getUnitsReal() != null) {
			PropertyValidator.validatePositiveInt("unitsRealAssigMat", assigMat.getUnitsReal());
		}		
	}
	
	// ============================================================================
	private void validateWorkload(Workload workload) throws InputValidationException {	
		PropertyValidator.validatePositiveInt("hoursWorkload", workload.getHours());
		PropertyValidator.validatePositiveInt("extraHoursWorkload", workload.getExtraHours());		
	}
	
	// ============================================================================
	// ======================= AssignmentProfile operations =======================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createAssignmentProfile(AssignmentProfile assignmentProfile)
			throws InstanceNotFoundException, InputValidationException {
		
		Long id;
		
		// First we validate the AssignmentProfile
		validateAssignmentProfile(assignmentProfile);
		
		// Check if the referenced Task and ProfCatg exists
		findInstanceService.findTask(assignmentProfile.getTask());
		findInstanceService.findProfessionalCategory(assignmentProfile.getProfCatg());
		
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
	public List<AssignmentProfile> findAssignmentProfileByTask(Task task) throws InstanceNotFoundException {
		
		List<AssignmentProfile> assigProfs = new ArrayList<AssignmentProfile>();
		
		// Check if the Task exists
		findInstanceService.findTask(task);
		
		// Find assignmentProfiles by task
		try{
			assigProfs = assigProfDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}

		// Return the result
		log.info(ModelConstants.FIND_ALL + assigProfs.size() + " registred AssignmentProfile"
				 + " associated at the Task with idTask[" + task.getId() + "]");
		return assigProfs;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateAssignmentProfile(AssignmentProfile assignmentProfile)
			throws InstanceNotFoundException, InputValidationException {
				
		// First check if the AssignmentProfile exists
		findInstanceService.findAssignmentProfile(assignmentProfile);
		
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
		
		Long id;
		
		// Validate the data assignmentMaterial
		validateAssignmentMaterial(assignmentMaterial);
		
		// Check if the Task and the Material exists
		findInstanceService.findTask(assignmentMaterial.getTask());
		findInstanceService.findMaterial(assignmentMaterial.getMaterial());
		
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
	public List<AssignmentMaterial> findAssignmentMaterialByTask(Task task) throws InstanceNotFoundException {
		
		List<AssignmentMaterial> assigMaterials = new ArrayList<AssignmentMaterial>();
		
		// Check if the Task exists
		findInstanceService.findTask(task);
		
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
	public List<AssignmentMaterial> findAssignmentMaterialByTaskPlan(Task task) 
			throws InstanceNotFoundException {
		
		List<AssignmentMaterial> assigMaterials = new ArrayList<AssignmentMaterial>();
		
		// Check if the Task exists
		findInstanceService.findTask(task);
		
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
	public List<AssignmentMaterial> findAssignmentMaterialByTaskReal(Task task) 
			throws InstanceNotFoundException {
		
		List<AssignmentMaterial> assigMaterials = new ArrayList<AssignmentMaterial>();
		
		// Check if the Task exists
		findInstanceService.findTask(task);
		
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
		
		// First check if the updated Material and the AssignmentMaterial exist
		findInstanceService.findMaterial(assignmentMaterial.getMaterial());
		findInstanceService.findAssignmentMaterial(assignmentMaterial);
		
		// Validate the data assignmentMaterial
		validateAssignmentMaterial(assignmentMaterial);
		
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
	public void removeAssignmentMaterial(Long id) throws InstanceNotFoundException {
		
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
	
	
	// ============================================================================
	// ======================== AssignmentPerson operations =======================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createAssignmentPerson(AssignmentPerson assignmentPerson) throws InstanceNotFoundException {
		
		Long id;
		
		// Check if the Task and the HistoryPerson exist
		findInstanceService.findTask(assignmentPerson.getTask());
		findInstanceService.findHistoryPerson(assignmentPerson.getHistoryPerson());
		
		// Now create the AssignmentPerson 
		try{
			id = assigPersonDAO.create(assignmentPerson);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + assignmentPerson.toString());
		return id;
		
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public AssignmentPerson findAssignmentPerson(Long id) throws InstanceNotFoundException {
		
		AssignmentPerson assigPerson = null;
		
		// Find the assignmentPerson
		try{
			assigPerson = assigPersonDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if it exists
		if (assigPerson == null) {
			throw new InstanceNotFoundException(id, AssignmentPerson.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + assigPerson.toString());
		return assigPerson;
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<AssignmentPerson> findAssignmentPersonByTask(Task task) throws InstanceNotFoundException {
		
		List<AssignmentPerson> assigPersons = new ArrayList<AssignmentPerson>();
		
		// Check if the Task exists 
		findInstanceService.findTask(task);
		
		// Search by Task
		try{
			assigPersons = assigPersonDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + assigPersons.size() + " AssignmentPerson in the Task "
				 + "with the idTask[" + task.getId() + "]");
		return assigPersons;
	}

	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<AssignmentPerson> findAssignmentPersonByPerson(HistoryPerson historyPerson) 
			throws InstanceNotFoundException {
		
		List<AssignmentPerson> assigPersons = new ArrayList<AssignmentPerson>();
		
		// Check if the HistoryPerson exists
		findInstanceService.findHistoryPerson(historyPerson);
		
		// Search by HistoryPerson
		try{
			assigPersons = assigPersonDAO.findByPerson(historyPerson);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + assigPersons.size() + " AssignmentPerson for the HistoryPerson "
				 + "with the idHPerson[" + historyPerson.getId() + "]");
		return assigPersons;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateAssignmentPerson(AssignmentPerson assignmentPerson) throws InstanceNotFoundException {
		
		// Check if the AssignmentPerson exists
		findInstanceService.findAssignmentPerson(assignmentPerson);

		// Update
		try{
			assigPersonDAO.update(assignmentPerson);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Log the result
		log.info(ModelConstants.UPDATE + assignmentPerson.toString());
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeAssignmentPerson(Long id) throws InstanceNotFoundException {
		
		AssignmentPerson assignmentPerson = findAssignmentPerson(id);
		
		// Remove
		try{
			assigPersonDAO.remove(assignmentPerson);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Log the result
		log.info(ModelConstants.DELETE + assignmentPerson.toString());
	}

	// ============================================================================
	// ============================ Workload operations ===========================
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createWorkload(Workload workload) throws InstanceNotFoundException, InputValidationException {
		
		Long id;
		
		// Check if the Task and the HistoryPerson exist
		findInstanceService.findTask(workload.getTask());
		findInstanceService.findHistoryPerson(workload.getHistoryPerson());
		
		// Now validate the data
		validateWorkload(workload);
		
		// If all is correct, create the Workload
		try{
			id = workloadDAO.create(workload);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.CREATE + workload.toString());
		return id;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Workload findWorkload(Long id) throws InstanceNotFoundException {
		
		Workload workload = null;
		
		// Find the workload
		try{
			workload = workloadDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Check if it exists
		if (workload == null) {
			throw new InstanceNotFoundException(id, Workload.class.getName());
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ID + workload.toString());
		return workload;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Workload> findWorkloadByTask(Task task) throws InstanceNotFoundException {
		
		List<Workload> workloads = new ArrayList<Workload>();
		
		// Check if the Task exists
		findInstanceService.findTask(task);
		
		// Find the Workloads by Task
		try{
			workloads = workloadDAO.findByTask(task);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + workloads.size() + " registred Workloads for the "
				 + "Task with idTask[" + task.getId() + "]");
		return workloads;
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Workload> findWorkloadByHistoryPerson(HistoryPerson historyPerson) 
			throws InstanceNotFoundException {
		
		List<Workload> workloads = new ArrayList<Workload>();
		
		// Check if the historyPerson exists
		findInstanceService.findHistoryPerson(historyPerson);
		
		// Find the Workloads by Task
		try{
			workloads = workloadDAO.findByHistoryPerson(historyPerson);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.FIND_ALL + workloads.size() + " registred Workloads for the "
				 + "HistoryPerson with idHistoryPerson[" + historyPerson.getId() + "]");
		return workloads;
	}
					
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateWorkload(Workload workload) throws InstanceNotFoundException, InputValidationException {
				
		// Check if this workload exists 
		findInstanceService.findWorkload(workload);
		
		// Validate the updated data
		validateWorkload(workload);
		
		// Update the object
		try{
			workloadDAO.update(workload);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.UPDATE + workload.toString());
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeWorkload(Long id) throws InstanceNotFoundException {
		
		Workload workload = findWorkload(id);
		
		// Remove the object
		try{
			workloadDAO.remove(workload);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Return the result
		log.info(ModelConstants.DELETE + workload.toString());	
	}

	
}
