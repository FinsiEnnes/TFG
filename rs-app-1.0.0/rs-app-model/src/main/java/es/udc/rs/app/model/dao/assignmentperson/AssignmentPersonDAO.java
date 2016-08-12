package es.udc.rs.app.model.dao.assignmentperson;

import java.util.List;

import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;

public interface AssignmentPersonDAO {

	public Long create(AssignmentPerson assignmentPerson);
	
	public AssignmentPerson find(Long id);
	
	public List<AssignmentPerson> findByTask(Task task);
	
	public List<AssignmentPerson> findByPerson(HistoryPerson historyPerson);
				
	public boolean AssignmentPersonExists(Long id);
	
	public void update(AssignmentPerson assignmentPerson);
	
	public void remove(AssignmentPerson assignmentPerson);
}
