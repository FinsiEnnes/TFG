package es.udc.rs.app.model.service.project;

import java.util.List;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.State;

public interface ProjectService {

	// ============================ Project operations ============================
	public Long createProject(Project project) throws InputValidationException;
	
	public Project findProject(Long id) throws InstanceNotFoundException;
	
	public Project findProjectByName(String name) throws InstanceNotFoundException;
		
	public void updateProject(Project project) throws InputValidationException, InstanceNotFoundException;
	
	public void removeProject(Long id) throws InstanceNotFoundException;
	
	
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
		
	public List<Phase> findPhaseByProject(Project project);
			
	public void updatePhase(Phase phase) throws InstanceNotFoundException;
	
	public void removePhase(Long id) throws InstanceNotFoundException;
	
	// =========================== Milestone operations ===========================
	public Long createMilestone(Milestone milestone) throws InstanceNotFoundException;
	
	public Milestone findMilestone(Long id) throws InstanceNotFoundException;
					
	public void updateMilestone(Milestone milestone) throws InstanceNotFoundException;
	
	public void removeMilestone(Long id) throws InstanceNotFoundException; 
}
