package es.udc.rs.app.model.util;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;

public interface FindInstanceService {

	public void findProject(Project project) throws InstanceNotFoundException;
	
	public void findTask(Task task) throws InstanceNotFoundException;
	
	public void findIncident(Incident incident) throws InstanceNotFoundException;
}
