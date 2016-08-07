package es.udc.rs.app.model.dao.assignmentMaterial;

import java.util.List;

import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.Task;

public interface AssignmentMaterialDAO {

	public Long create(AssignmentMaterial assignmentMaterial);
	
	public AssignmentMaterial find(Long id);
	
	public List<AssignmentMaterial> findByTask(Task task);
	
	public List<AssignmentMaterial> findByTaskPlan(Task task);
	
	public List<AssignmentMaterial> findByTaskReal(Task task);
				
	public boolean AssignmentMaterialExists(Long id);
	
	public void update(AssignmentMaterial assignmentMaterial);
	
	public void remove(AssignmentMaterial assignmentMaterial);
}
