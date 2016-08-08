package es.udc.rs.app.model.dao.taskincident;

import java.util.List;

import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.domain.Task;

public interface TaskIncidentDAO {

	public Long create(TaskIncident taskIncident);
	
	public TaskIncident find(Long id);
	
	public List<TaskIncident> findByTask(Task task);
				
	public boolean TaskIncidentExists(Long id);
	
	public void update(TaskIncident taskIncident);
	
	public void remove(TaskIncident taskIncident);
}
