package es.udc.rs.app.model.dao.assignmentprofile;

import java.util.List;

import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.Task;

public interface AssignmentProfileDAO {

	public Long create(AssignmentProfile assignmentProfile);
	
	public AssignmentProfile find(Long id);
	
	public List<AssignmentProfile> findByTask(Task task);
				
	public boolean AssignmentProfileExists(Long id);
	
	public void update(AssignmentProfile assignmentProfile);
	
	public void remove(AssignmentProfile assignmentProfile);
}
