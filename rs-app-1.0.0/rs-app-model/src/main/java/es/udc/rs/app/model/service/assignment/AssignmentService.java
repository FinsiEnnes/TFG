package es.udc.rs.app.model.service.assignment;

import java.util.List;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.Task;

public interface AssignmentService {

	// ======================= AssignmentProfile operations =======================
	public Long createAssignmentProfile(AssignmentProfile assignmentProfile)
		throws InstanceNotFoundException, InputValidationException;
	
	public AssignmentProfile findAssignmentProfile(Long id)  throws InstanceNotFoundException;
	
	public List<AssignmentProfile> findAssignmentProfileByTask(Task task);
	
	public void updateAssignmentProfile(AssignmentProfile assignmentProfile)
		 throws InstanceNotFoundException, InputValidationException;
	
	public void removeAssignmentProfile(Long id) throws InstanceNotFoundException;
	
	
	// ======================= AssignmentMaterial operations ======================
	public Long createAssignmentMaterial(AssignmentMaterial assignmentMaterial)
		throws InstanceNotFoundException, InputValidationException;
	
	public AssignmentMaterial findAssignmentMaterial(Long id) throws InstanceNotFoundException;
	
	public List<AssignmentMaterial> findAssignmentMaterialByTask(Task task);
	
	public List<AssignmentMaterial> findAssignmentMaterialByTaskPlan(Task task);
	
	public List<AssignmentMaterial> findAssignmentMaterialByTaskReal(Task task);
					
	public void updateAssignmentMaterial(AssignmentMaterial assignmentMaterial)
		throws InstanceNotFoundException, InputValidationException;
	
	public void remove(Long id) throws InstanceNotFoundException;
}
