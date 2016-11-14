package es.udc.rs.app.model.service.project;

import java.util.List;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectFreeDay;
import es.udc.rs.app.model.domain.ProjectMgmt;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.domain.TaskLinkType;

public interface ProjectService {

	// ============================ Project operations ============================
	public Long createProject(Project project) throws InstanceNotFoundException, InputValidationException;
	
	public Project findProject(Long id) throws InstanceNotFoundException;
	
	public Project findProjectByName(String name) throws InstanceNotFoundException;
		
	public void updateProject(Project project) throws InputValidationException, InstanceNotFoundException;
	
	public void removeProject(Long id) throws InstanceNotFoundException;
	
	
	// ============================ FreeDay operations ============================
	public Long createFreeDay(FreeDay freeDay) throws InputValidationException;
	
	public FreeDay findFreeDay(Long id) throws InstanceNotFoundException;
	
	public List<FreeDay> findAllFreeDay();
				
	public void updateFreeDay(FreeDay freeDay) throws InputValidationException, InstanceNotFoundException;
	
	public void removeFreeDay(Long id) throws InstanceNotFoundException;	
	
	
	// ======================== ProjectFreeDay operations =========================
	public Long createProjectFreeDay(ProjectFreeDay projectFreeDay) throws InstanceNotFoundException;
	
	public ProjectFreeDay findProjectFreeDay(Long id) throws InstanceNotFoundException;
	
	public List<ProjectFreeDay> findAllProjectFreeDay();
	
	public List<FreeDay> findProjectFreeDayByProject(Project project) throws InstanceNotFoundException;
	
	public void updateProjectFreeDay(ProjectFreeDay projectFreeDay) throws InstanceNotFoundException;
	
	public void removeProjectFreeDay(Long id) throws InstanceNotFoundException;
	
	
	// ========================= ProjectMgmt operations ===========================
	public Long createProjectMgmt(ProjectMgmt projectMgmt) throws InstanceNotFoundException;
	
	public ProjectMgmt findProjectMgmt(Long id) throws InstanceNotFoundException;
	
	public List<ProjectMgmt> findAllProjectMgmt();
	
	public List<ProjectMgmt> findProjectMgmtByProject(Project project) throws InstanceNotFoundException;
				
	public void updateProjectMgmt(ProjectMgmt projectMgmt) throws InstanceNotFoundException;
	
	public void removeProjectMgmt(Long id) throws InstanceNotFoundException;

	
	// ============================= State operations =============================
	public State findState(String id) throws InstanceNotFoundException;
	
	public List<State> findAllState();
	
	
	// ======================== HistoryProject operations =========================
	public Long createHistoryProject(HistoryProject historyProject) throws InstanceNotFoundException;
	
	public HistoryProject findHistoryProject(Long id) throws InstanceNotFoundException;
		
	public List<HistoryProject> findHistoryProjectByProject(Project project) throws InstanceNotFoundException;
	
	public HistoryProject findCurrentHistoryProject(Project project);
	
	public void updateHistoryProject(HistoryProject historyProject) throws InstanceNotFoundException;
	
	public void removeHistoryProject(Long id) throws InstanceNotFoundException;
	
	
	// ============================= Phase operations =============================
	public Long createPhase(Phase phase) throws InstanceNotFoundException;
	
	public Phase findPhase(Long id) throws InstanceNotFoundException;
		
	public List<Phase> findPhaseByProject(Long idProject) throws InstanceNotFoundException;
			
	public void updatePhase(Phase phase) throws InstanceNotFoundException;
	
	public void removePhase(Long id) throws InstanceNotFoundException;
	
	
	// =========================== Milestone operations ===========================
	public Long createMilestone(Milestone milestone) throws InstanceNotFoundException;
	
	public Milestone findMilestone(Long id) throws InstanceNotFoundException;
	
	public List<Milestone> findMilestonesByProject(Long idProject) throws InstanceNotFoundException;
					
	public void updateMilestone(Milestone milestone) throws InstanceNotFoundException;
	
	public void removeMilestone(Long id) throws InstanceNotFoundException; 
	
	
	// ============================ Damage operations =============================
	public Damage findDamage(String id) throws InstanceNotFoundException;
	
	public List<Damage> findAllDamage();
	
	
	// =========================== Incident operations ============================
	public Long createIncident(Incident incident) throws InstanceNotFoundException;
	
	public Incident findIncident(Long id) throws InstanceNotFoundException;
					
	public void updateIncident(Incident incident) throws InstanceNotFoundException;
	
	public void removeIncident(Long id) throws InstanceNotFoundException;	
	
	
	// ========================= TaskIncident operations ==========================
	public Long createTaskIncident(TaskIncident taskIncident)
		throws InputValidationException, InstanceNotFoundException;
	
	public TaskIncident findTaskIncident(Long id) throws InstanceNotFoundException;
	
	public List<TaskIncident> findTaskIncidentByTask(Task task) throws InstanceNotFoundException;
	
	public List<TaskIncident> findTaskIncidentByProject(Project project) throws InstanceNotFoundException;
					
	public void updateTaskIncident(TaskIncident taskIncident)
		throws InputValidationException, InstanceNotFoundException;
	
	public void removeTaskIncident(Long id) throws InstanceNotFoundException;
	
	
	// =========================== Priority operations ============================
	public Priority findPriority(String id) throws InstanceNotFoundException;
	
	public List<Priority> findAllPriority();
	
	
	// ============================= Task operations ==============================
	public Long createTask(Task task) throws InputValidationException, InstanceNotFoundException;
	
	public Task findTask(Long id) throws InstanceNotFoundException;
	
	public List<Task> findProjectTasks(Long idProject) throws InstanceNotFoundException;
					
	public void updateTask(Task task) throws InputValidationException, InstanceNotFoundException;
	
	public void removeTask(Long id) throws InstanceNotFoundException;
	
	
	// ========================= TaskLinkType operations ==========================
	public TaskLinkType findTaskLinkType(String id) throws InstanceNotFoundException;
	
	public List<TaskLinkType> findAllTaskLinkType();
	
	
	// ========================= Predecessor operations ===========================
	public Long createPredecessor(Predecessor predecessor) throws InstanceNotFoundException;
	
	public Predecessor findPredecessor(Long id) throws InstanceNotFoundException;
	
	public List<Predecessor> findPredecessorByTask(Task task) throws InstanceNotFoundException;
	
	public List<Predecessor> findPredecessorByProject(Long idProject) throws InstanceNotFoundException;
					
	public void updatePredecessor(Predecessor predecessor) throws InstanceNotFoundException;
	
	public void removePredecessor(Long id) throws InstanceNotFoundException;
}
