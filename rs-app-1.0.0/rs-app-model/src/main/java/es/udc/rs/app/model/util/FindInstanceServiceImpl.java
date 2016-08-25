package es.udc.rs.app.model.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.assignmentprofile.AssignmentProfileDAO;
import es.udc.rs.app.model.dao.incident.IncidentDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.dao.taskincident.TaskIncidentDAO;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;

@Service
public class FindInstanceServiceImpl implements FindInstanceService {

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired 
	private TaskDAO taskDAO;
	
	@Autowired
	private IncidentDAO incidentDAO;
	
	@Autowired
	private TaskIncidentDAO taskIncidentDAO;
	
	@Autowired 
	private ProfessionalCategoryDAO profCatgDAO;
	
	@Autowired
	private AssignmentProfileDAO assigProfDAO;
	
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


	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findTaskIncident(TaskIncident taskIncident) throws InstanceNotFoundException {
		
		Long idTaskIncident = taskIncident.getId();
		
		if (!taskIncidentDAO.TaskIncidentExists(idTaskIncident)) {
			throw new InstanceNotFoundException(idTaskIncident, TaskIncident.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProfessionalCategory(ProfessionalCategory pf) throws InstanceNotFoundException {
		
		Long idProfCatg = pf.getId();
		
		if (!profCatgDAO.ProfessionalCategoryExists(idProfCatg)) {
			throw new InstanceNotFoundException(idProfCatg, ProfessionalCategory.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findAssignmentProfile(AssignmentProfile ap) throws InstanceNotFoundException {
		
		Long id = ap.getId();
		
		if (!assigProfDAO.AssignmentProfileExists(id)) {
			throw new InstanceNotFoundException(id, AssignmentProfile.class.getName());
		}
	}
}
