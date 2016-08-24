package es.udc.rs.app.model.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.incident.IncidentDAO;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;

@Service
public class FindInstanceServiceImpl implements FindInstanceService {

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired 
	private TaskDAO taskDAO;
	
	@Autowired
	private IncidentDAO incidentDAO;
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProject(Project project) throws InstanceNotFoundException {
		
		Long idProject = project.getId();
		
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findTask(Task task)  throws InstanceNotFoundException {
		
		Long idTask = task.getId();
		
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findIncident(Incident incident) throws InstanceNotFoundException {
		
		Long idIncident = incident.getId();
		
		if (!incidentDAO.IncidentExists(idIncident)) {
			throw new InstanceNotFoundException(idIncident, Incident.class.getName());
		}
	}
}
