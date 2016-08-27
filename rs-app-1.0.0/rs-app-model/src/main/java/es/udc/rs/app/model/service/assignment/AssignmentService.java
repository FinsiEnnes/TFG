package es.udc.rs.app.model.service.assignment;

import java.util.List;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.Workload;

public interface AssignmentService {

	// ======================= AssignmentProfile operations =======================
	public Long createAssignmentProfile(AssignmentProfile assignmentProfile)
		throws InstanceNotFoundException, InputValidationException;
	
	public AssignmentProfile findAssignmentProfile(Long id) throws InstanceNotFoundException;
	
	public List<AssignmentProfile> findAssignmentProfileByTask(Task task) throws InstanceNotFoundException;
	
	public void updateAssignmentProfile(AssignmentProfile assignmentProfile)
		 throws InstanceNotFoundException, InputValidationException;
	
	public void removeAssignmentProfile(Long id) throws InstanceNotFoundException;
	
	
	// ======================= AssignmentMaterial operations ======================
	public Long createAssignmentMaterial(AssignmentMaterial assignmentMaterial)
		throws InstanceNotFoundException, InputValidationException;
	
	public AssignmentMaterial findAssignmentMaterial(Long id) throws InstanceNotFoundException;
	
	public List<AssignmentMaterial> findAssignmentMaterialByTask(Task task) throws InstanceNotFoundException;
	
	public List<AssignmentMaterial> findAssignmentMaterialByTaskPlan(Task task) throws InstanceNotFoundException;
	
	public List<AssignmentMaterial> findAssignmentMaterialByTaskReal(Task task) throws InstanceNotFoundException;
					
	public void updateAssignmentMaterial(AssignmentMaterial assignmentMaterial)
		throws InstanceNotFoundException, InputValidationException;
	
	public void removeAssignmentMaterial(Long id) throws InstanceNotFoundException;
	
	
	// ======================== AssignmentPerson operations =======================
	public Long createAssignmentPerson(AssignmentPerson assignmentPerson) throws InstanceNotFoundException;
	
	public AssignmentPerson findAssignmentPerson(Long id) throws InstanceNotFoundException;
	
	public List<AssignmentPerson> findAssignmentPersonByTask(Task task) throws InstanceNotFoundException;
	
	public List<AssignmentPerson> findAssignmentPersonByPerson(HistoryPerson historyPerson)
			throws InstanceNotFoundException;
					
	public void updateAssignmentPerson(AssignmentPerson assignmentPerson) throws InstanceNotFoundException;
	
	public void removeAssignmentPerson(Long id) throws InstanceNotFoundException;
	
	
	// ============================ Workload operations ===========================
	public Long createWorkload(Workload workload) throws InstanceNotFoundException, InputValidationException;
	
	public Workload findWorkload(Long id) throws InstanceNotFoundException;
	
	public List<Workload> findWorkloadByTask(Task task) throws InstanceNotFoundException;
	
	public List<Workload> findWorkloadByHistoryPerson(HistoryPerson historyPerson) throws InstanceNotFoundException;
					
	public void updateWorkload(Workload workload) throws InstanceNotFoundException, InputValidationException;
	
	public void removeWorkload(Long id) throws InstanceNotFoundException;
}
