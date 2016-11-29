package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.HistoryPersonDTOConversor;
import es.udc.rs.app.client.conversor.PhaseDTOConversor;
import es.udc.rs.app.client.conversor.PredecessorDTOConversor;
import es.udc.rs.app.client.conversor.ProfessionalCategoryDTOConversor;
import es.udc.rs.app.client.conversor.TaskDTOConversor;
import es.udc.rs.app.client.dto.AssignmentProfileDTO;
import es.udc.rs.app.client.dto.HistoryPersonDTO;
import es.udc.rs.app.client.dto.PhaseDTO;
import es.udc.rs.app.client.dto.PredecessorDTO;
import es.udc.rs.app.client.dto.ProfessionalCategoryDTO;
import es.udc.rs.app.client.dto.TaskDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.client.util.JsonConversor;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class TaskController {
	
	@Autowired 
	private ProjectService projectService;
	
	@Autowired 
	private PersonService personService;
	
	@Autowired 
	private AssignmentService assignmentService;
	
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	//------------------------------------------ TASK INFORMATION -----------------------------------------  
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id || Getting of the principal Task information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}",  method=RequestMethod.GET)
    public String showTask(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
		Long longIdProject =  Long.parseLong(idProject, 10);
    	Long longIdTask = Long.parseLong(idTask, 10);
    	
		// First we get the selected task
		Task task = projectService.findTask(longIdTask);
		TaskDTO taskDTO = TaskDTOConversor.toTaskDTO(task);
		
		// We also need the phases information to show it to the user
		List<Phase> phases = projectService.findPhaseByProject(longIdProject);
		
		// Remove the Phase task of the List and convert this in DTO
		phases.remove(task.getPhase());
		List<PhaseDTO> phasesDTO = PhaseDTOConversor.toPhaseDTOList(phases);
		
		// Get the different priorities
		List<Priority> priorities = projectService.findAllPriority();
		priorities.remove(task.getPriority());
		
		// Get the current HistoryPerson to show it in the select responsible modal
		List<HistoryPerson> persons = personService.findCurrentHistoryPersons();
		List<HistoryPersonDTO> personsDTO = HistoryPersonDTOConversor.toHistoryPersonDTOs(persons);
		
		//Create the model
    	model.addAttribute("task", taskDTO);
    	model.addAttribute("phases", phasesDTO);
    	model.addAttribute("priorities", priorities);
    	model.addAttribute("persons", personsDTO);
		
		return "task/info";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/update/basic || Update the basic Task information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/update/basic",  
					method=RequestMethod.POST)
    public String updateBasicInfoTask(@Valid @ModelAttribute("task") TaskDTO taskDTO,  BindingResult result,
    		@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long longIdTask = Long.parseLong(idTask, 10);
		
		// Convert the TaskDTO to Task
		Task taskUpdatedBasicInfo = TaskDTOConversor.toTask(taskDTO);
		
		// Get the original task
		Task originalTask = projectService.findTask(longIdTask);
		
		// Update the basic info
		originalTask.setName(taskUpdatedBasicInfo.getName());
		originalTask.setPriority(taskUpdatedBasicInfo.getPriority());
		originalTask.setHistoryPerson(taskUpdatedBasicInfo.getHistoryPerson());
		originalTask.setComment(taskUpdatedBasicInfo.getComment().trim());
		
		// Update the Task in the database
		projectService.updateTask(originalTask);
		
		return showTask(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/update/responsible || Update the responsible Task info.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/update/responsible",  
					method=RequestMethod.POST)
    public String updateResponsibleTask(@Valid @ModelAttribute("historyPerson") HistoryPersonDTO historyPersonDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long longIdTask = Long.parseLong(idTask, 10);
		
		// Find HistoryPerson of the Person that represents at the new responsible
    	HistoryPerson responsible = personService.findHistoryPerson(historyPersonDTO.getId());
		
		// Get the original task
		Task originalTask = projectService.findTask(longIdTask);
		
		// Update the responsible info
		originalTask.setHistoryPerson(responsible);
		projectService.updateTask(originalTask);
		
		return showTask(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/state/newState || Change the task State   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/state/{newState}", 
					method=RequestMethod.POST)
    public String changeTaskState(@Valid @ModelAttribute("task") TaskDTO taskDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String newState, Model model) throws InstanceNotFoundException, InputValidationException {
		
    	// Convert the string id to long
    	Long id = Long.parseLong(idTask, 10);
    	
		// Get the state
		State state = projectService.findState(newState);
		
		// Find the Task and set the new state
		Task task = projectService.findTask(id);
		task.setState(state);
		
		// If the next state is Execution, we also need update the iniReal attribute
		if (newState.equals("EJEC")) {
			task.setIniReal(ClientUtilMethods.toDate(taskDTO.getIniReal()));
		}
		
		// Update the Task
		try {
			projectService.updateTask(task);
		} catch (DataAccessException e) {
			String msg = e.getCause().getCause().getMessage();
	    	model.addAttribute("msg", msg);
	    	model.addAttribute("feedback", "active");
		}
		
		// Show the task information
		return showTask(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/update/statics || Update the planned statics of the task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/update/statics",  
					method=RequestMethod.POST)
    public String updateStaticsTask(@Valid @ModelAttribute("task") TaskDTO taskDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long longIdTask = Long.parseLong(idTask, 10);
		
		// Get the original task
		Task task = projectService.findTask(longIdTask);
		
		// Update the statics info
		task.setIniPlan(ClientUtilMethods.toDate(taskDTO.getIniPlan()));
		task.setDaysPlan(taskDTO.getDaysPlan());
		projectService.updateTask(task);
		
		return showTask(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/update/progress || Update the progress of the task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/update/progress",  
					method=RequestMethod.POST)
    public String updateProgressTask(@Valid @ModelAttribute("task") TaskDTO taskDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long longIdTask = Long.parseLong(idTask, 10);
		
		// Get the original task
		Task task = projectService.findTask(longIdTask);
		
		// Update the statics info
		task.setProgress(taskDTO.getProgress());
		projectService.updateTask(task);
		
		return showTask(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/delete || Delete the task selected 
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/delete",  
					method=RequestMethod.POST)
    public String deleteTask(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
    	// Convert the string id to long
    	Long idT = Long.parseLong(idTask, 10);
		
		// Delete the task
		projectService.removeTask(idT);
		
    	// Convert the string id to long
    	Long idP = Long.parseLong(idProject, 10);
    	
    	// Get the Phases, Tasks and Milestones of this Project
    	List<Phase> phases = projectService.findPhaseByProject(idP);
    	List<Task>  tasks  = projectService.findProjectTasks(idP);
    	List<Milestone> milestones = projectService.findMilestonesByProject(idP);
    	List<Predecessor> links = projectService.findPredecessorByProject(idP);
    	
    	// Create a JSON with the global project information that forms the chart
    	JSONObject mainObj = JsonConversor.getDataProjectAsJSON(phases, tasks, milestones, links);
    	
    	// Create JSONs with the necessary info for when a user clicks on a Phase, Task or Milestone
    	JSONObject taskObj = JsonConversor.getTaskDetailsAsJSON(tasks);
    	JSONObject msObj = JsonConversor.getMilestoneDetailsAsJSON(milestones);
    	
    	// Convert the list Phase object to PhaseDTO because we need show this info
    	List<PhaseDTO> phasesDTO = PhaseDTOConversor.toPhaseDTOList(phases);
    	
    	// We also need some historyPerson info
    	List<HistoryPerson> hps = personService.findCurrentHistoryPersons();
    	List<HistoryPersonDTO> hpsDTO = HistoryPersonDTOConversor.toHistoryPersonDTOs(hps);

    	// Send the data out to the model
    	model.addAttribute("idProject", idP);
    	model.addAttribute("dataProject", mainObj);
    	model.addAttribute("taskDetails", taskObj);
    	model.addAttribute("msDetails", msObj);
    	model.addAttribute("phases", phasesDTO);
    	model.addAttribute("persons", hpsDTO);
    	
    	return "planning/projectPlan";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	//------------------------------------------ TASK PREDECESSOR -----------------------------------------  
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/predecessors || Get the predecessor task of the selected task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/predecessors",  
					method=RequestMethod.GET)
    public String showPredecessors(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
		
    	// Convert the string id to long
    	Long longIdProject = Long.parseLong(idProject, 10);
    	Long longIdTask = Long.parseLong(idTask, 10);
    	
    	// Get the current Task
    	Task task = projectService.findTask(longIdTask);
    	
    	// Get the Phases and Task of the Project and the Predecessors of this Task
		List<Task> tasks  = projectService.findProjectTasks(longIdProject);
		List<Phase> phases = projectService.findPhaseByProject(longIdProject);
		List<Predecessor> predecessors = projectService.findPredecessorByTask(task);
		
		// We delete those tasks that are already predecessors		
		for (Predecessor p : predecessors) {
			tasks.remove(p.getTaskPred());
		}
		
		// Convert the tasks info in JSON because the table and the phases to DTO
		JSONArray taskJSON = JsonConversor.getTaskAsJSONForTables(tasks);
		List<PhaseDTO> phasesDTO = PhaseDTOConversor.toPhaseDTOList(phases);
		
		// Convert the Predecessors to DTO
		List<PredecessorDTO> predecessorsDTO = PredecessorDTOConversor.toPredecessorDTOList(predecessors);
		
		// Create the model
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		model.addAttribute("firstIdPhase", phasesDTO.get(0).getId());
		model.addAttribute("phases", phasesDTO);
    	model.addAttribute("taskjson", taskJSON);
    	model.addAttribute("predecessors", predecessorsDTO);
    	
		return "task/predecessors";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/predecessors || Add a predecessor task to the selected task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/predecessors",  
					method=RequestMethod.POST)
    public String addPredecessors(@Valid @ModelAttribute("predecessor") PredecessorDTO predDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the object DTO to object
		Predecessor pred = PredecessorDTOConversor.toPredecessor(predDTO);
		
		// Add this new Predecessor
		projectService.createPredecessor(pred);
		
		// Return the main predecessor interface
		return showPredecessors(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/predecessors/id/update || Update a type link of a predecessor task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/predecessors/{idPred}/update",  
					method=RequestMethod.POST)
    public String updatePredecessors(@Valid @ModelAttribute("predecessor") PredecessorDTO predDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idPred, Model model) throws InstanceNotFoundException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long longIdPred = Long.parseLong(idPred, 10);
    	
		// We find the Predecessor object and change the link type
    	Predecessor pred = projectService.findPredecessor(longIdPred);
		TaskLinkType tlt = projectService.findTaskLinkType(predDTO.getLinkType());
		pred.setLinkType(tlt);
		
		// Now update the Predecessor
		projectService.updatePredecessor(pred);
		
		// Return the main predecessor interface
		return showPredecessors(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/predecessors/id/delete || Delete a predecessor task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/predecessors/{idPred}/delete",  
					method=RequestMethod.POST)
    public String deletePredecessors(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idPred, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdPred = Long.parseLong(idPred, 10);
    	
		// Delete the Predecessor task
    	projectService.removePredecessor(longIdPred);
		
		// Return the main predecessor interface
		return showPredecessors(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	//-------------------------------------------- TASK PERSONS -------------------------------------------  
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	
	private List<ProfessionalCategoryDTO> getProfCatgDTO(List<AssignmentProfile> aps) throws InstanceNotFoundException {
		
		List<ProfessionalCategory> profCatgs = new ArrayList<ProfessionalCategory>();
    	ProfessionalCategory pc;
    	
    	for (AssignmentProfile ap : aps) {
    		pc = personService.findProfessionalCategory(ap.getProfCatg().getId());
    		profCatgs.add(pc);
    	}
    	
    	// Convert this object to DTO
    	return	ProfessionalCategoryDTOConversor.toProfessionalCategoryDTOList(profCatgs);	 
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/persons || Get the profiles and persons linked with this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/persons",  
					method=RequestMethod.GET)
    public String showPersons(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdProject = Long.parseLong(idProject, 10);
    	Long longIdTask = Long.parseLong(idTask, 10);
 
    	// Get the assigned profiles at this task
    	Task task = projectService.findTask(longIdTask);
    	List<AssignmentProfile> aps = assignmentService.findAssignmentProfileByTask(task);
    	
    	// Convert the previous list to JSON
    	JSONArray asignmtProfJson = JsonConversor.getAssignmentProfileAsJSON(aps);
    	
    	// Get the ProfessionalCategoryDTO of each assignment profile
    	List<ProfessionalCategoryDTO> pcsDTO = getProfCatgDTO(aps); 
    	
    	// Create the model
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
    	model.addAttribute("profcatgs", pcsDTO);
    	model.addAttribute("assignmtProfJson", asignmtProfJson);
    	
		return "task/persons";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/profiles || Add a new AssignmentProfile.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/profiles/{idAP}/update",  
					method=RequestMethod.POST)
    public String addAsignmentProfile(@Valid @ModelAttribute("asignmntProf") AssignmentProfileDTO apDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAP, Model model) throws InstanceNotFoundException {
		
		
		// Return the main persons interface
		return showPersons(idProject,idPhase,idTask,model);	
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/profiles/id/update || Update an AssignmentProfile.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/profiles/{idAP}/update",  
					method=RequestMethod.POST)
    public String updateAsignmentProfile(@Valid @ModelAttribute("asignmntProf") AssignmentProfileDTO apDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAP, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long longIdAP = Long.parseLong(idAP, 10);
    	
		// Get the AsignmentProfile by the id
		AssignmentProfile ap = assignmentService.findAssignmentProfile(longIdAP);
		
		// Update the object
		ap.setUnits(apDTO.getUnits());
		ap.setHoursPerPerson(apDTO.getHoursPerPerson());
		assignmentService.updateAssignmentProfile(ap);
		
		// Return the main persons interface
		return showPersons(idProject,idPhase,idTask,model);		
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/profiles/id/delete || Delete an AssignmentProfile.
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/profiles/{idAP}/delete",  
					method=RequestMethod.POST)
    public String deleteAsignmentProfile(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAP, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long id = Long.parseLong(idAP, 10);
    	
    	// Delete the assignment profile
		assignmentService.removeAssignmentProfile(id);
		
		// Return the main persons interface
		return showPersons(idProject,idPhase,idTask,model);
	}
	
}
