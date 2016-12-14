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

import es.udc.rs.app.client.conversor.AssignmentMaterialDTOConversor;
import es.udc.rs.app.client.conversor.AssignmentPersonDTOConversor;
import es.udc.rs.app.client.conversor.AssignmentProfileDTOConversor;
import es.udc.rs.app.client.conversor.HistoryPersonDTOConversor;
import es.udc.rs.app.client.conversor.MaterialDTOConversor;
import es.udc.rs.app.client.conversor.PhaseDTOConversor;
import es.udc.rs.app.client.conversor.PredecessorDTOConversor;
import es.udc.rs.app.client.conversor.ProfessionalCategoryDTOConversor;
import es.udc.rs.app.client.conversor.TaskDTOConversor;
import es.udc.rs.app.client.conversor.TaskIncidentDTOConversor;
import es.udc.rs.app.client.conversor.WorkloadDTOConversor;
import es.udc.rs.app.client.dto.AssignmentMaterialDTO;
import es.udc.rs.app.client.dto.AssignmentPersonDTO;
import es.udc.rs.app.client.dto.AssignmentProfileDTO;
import es.udc.rs.app.client.dto.HistoryPersonDTO;
import es.udc.rs.app.client.dto.MaterialDTO;
import es.udc.rs.app.client.dto.PhaseDTO;
import es.udc.rs.app.client.dto.PredecessorDTO;
import es.udc.rs.app.client.dto.ProfessionalCategoryDTO;
import es.udc.rs.app.client.dto.TaskDTO;
import es.udc.rs.app.client.dto.TaskIncidentDTO;
import es.udc.rs.app.client.dto.WorkloadDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.client.util.JsonConversor;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.domain.Workload;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.material.MaterialService;
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
	
	@Autowired 
	private MaterialService materialService;
	
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
	
	private List<ProfessionalCategoryDTO> getProfCatgOfThisAssignment(List<AssignmentProfile> aps) 
			throws InstanceNotFoundException {
		
		List<ProfessionalCategory> profCatgs = new ArrayList<ProfessionalCategory>();
    	ProfessionalCategory pc;
    	
    	for (AssignmentProfile ap : aps) {
    		pc = personService.findProfessionalCategory(ap.getProfCatg().getId());
    		profCatgs.add(pc);
    	}
    	
    	// Convert this object to DTO
    	return	ProfessionalCategoryDTOConversor.toProfessionalCategoryDTOList(profCatgs);	 
	}
	
	private List<HistoryPersonDTO> getHPersonOfThisAssignment(List<AssignmentPerson> aps) 
			throws InstanceNotFoundException {
		
		List<HistoryPerson> hPersons = new ArrayList<HistoryPerson>();
		
		for (AssignmentPerson ap : aps) {
			hPersons.add(personService.findHistoryPerson(ap.getHistoryPerson().getId()));
		}
		
		return HistoryPersonDTOConversor.toHistoryPersonDTOs(hPersons);
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/persons || Get the profiles and persons linked with this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/persons",  
					method=RequestMethod.GET)
    public String showPersons(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
    	// Find the Task
    	Long longIdTask = Long.parseLong(idTask, 10);
    	Task task = projectService.findTask(longIdTask);
 
    	
    	// PROFILES
    	//------------------------------------------------	
      	// Get all the ProfessionalCategories
    	List<ProfessionalCategory> profCatgs = personService.findAllProfessionalCategories();
    	List<ProfessionalCategoryDTO> allProfCatgsDTO = ProfessionalCategoryDTOConversor.toProfessionalCategoryDTOList(profCatgs);
    	
    	// Get the assigned at the current Task
    	List<AssignmentProfile> assigProfs = assignmentService.findAssignmentProfileByTask(task);
    	
    	// Convert the previous list to JSON
    	JSONArray assignmtProfJson = JsonConversor.getAssignmentProfileAsJSON(assigProfs);
    	
    	// Get the ProfessionalCategoryDTO of each assignment profile
    	List<ProfessionalCategoryDTO> taskProfCatgsDTO = getProfCatgOfThisAssignment(assigProfs); 
    	
  
    	// PERSONS
    	//------------------------------------------------
    	// Get all the HistoryPersons
    	List<HistoryPerson> hPersons = personService.findAllHistoryPerson();
    	List<HistoryPersonDTO> allHPersonsDTO = HistoryPersonDTOConversor.toHistoryPersonDTOs(hPersons);
    	
    	List<AssignmentPerson> assigPersons = assignmentService.findAssignmentPersonByTask(task);
    	
    	// Convert the list of AssignmentPerson in JSON
    	JSONArray assignmtPersonJson = JsonConversor.getAssignmentPersonAsJSON(assigPersons);
    	
    	// Get the HistoryPersonDTO of each assignment person
    	List<HistoryPersonDTO> taskHPersonsDTO = getHPersonOfThisAssignment(assigPersons);
    	
    	
    	// CREATE THE MODEL
    	//------------------------------------------------
    	// Basic information of the Task
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		
		// ProfessionalCategories and HPersons to select in the additions
    	model.addAttribute("profcatgs", allProfCatgsDTO);
    	model.addAttribute("hpersons", allHPersonsDTO);
    	
    	// Planned and Real assignments in the Task
    	model.addAttribute("hPersonsAssigned", taskHPersonsDTO);
    	model.addAttribute("profcatgsAssigned", taskProfCatgsDTO);
    	
    	// Details of the assignments in JSON format
    	model.addAttribute("assignmtProfJson", assignmtProfJson);
    	model.addAttribute("assignmtPersonJson", assignmtPersonJson);
    	
		return "task/persons";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/profiles || Add a new AssignmentProfile.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/profiles",  
					method=RequestMethod.POST)
    public String addAsignmentProfile(@Valid @ModelAttribute("asignmtProf") AssignmentProfileDTO apDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the DTO to object
		AssignmentProfile ap = AssignmentProfileDTOConversor.toAssignmentProfile(apDTO);
		
		// Create the AssignmentProfile
		assignmentService.createAssignmentProfile(ap);
		
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
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/persons || Add a new Person to assign in the Task.
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/persons",  
					method=RequestMethod.POST)
    public String addAsignmentPerson(@Valid @ModelAttribute("asignmtPerson") AssignmentPersonDTO apDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the DTO to object
		AssignmentPerson ap = AssignmentPersonDTOConversor.toAssignmentPerson(apDTO);
		
		// Create the AssignmentProfile
		assignmentService.createAssignmentPerson(ap);
		
		// Return the main persons interface
		return showPersons(idProject,idPhase,idTask,model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/persons/id/conclude || Conclude the work of AssignmentPerson.
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/persons/{idAP}/conclude",  
					method=RequestMethod.POST)
    public String concludeAsignmentPerson(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAP, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdAP = Long.parseLong(idAP, 10);
    	
    	// Find the AssignmentPerson
    	AssignmentPerson ap = assignmentService.findAssignmentPerson(longIdAP);
    	
    	// Update the AssignmentPerson and conclude the job
    	ap.setConclude(true);
    	assignmentService.updateAssignmentPerson(ap);
		
		// Return the main persons interface
		return showPersons(idProject,idPhase,idTask,model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/persons/id/delete || Delete an AssignmentPerson.
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/persons/{idAP}/delete",  
					method=RequestMethod.POST)
    public String deleteAsignmentPerson(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAP, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdAP = Long.parseLong(idAP, 10);
    	
    	// Delete the AssignmentPerson
    	assignmentService.removeAssignmentPerson(longIdAP);
		
		// Return the main persons interface
		return showPersons(idProject,idPhase,idTask,model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	//------------------------------------------- TASK WORKLOAD -------------------------------------------  
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/workload || Get the workload assigned at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/workloads",
					method=RequestMethod.GET)
	public String showWorkload(@PathVariable String idProject, @PathVariable String idPhase,
				   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
    	// Find the Task
    	Long longIdTask = Long.parseLong(idTask, 10);
    	Task task = projectService.findTask(longIdTask);
    	
  
    	// PERSONS
    	//------------------------------------------------
    	// Get all the HistoryPersons assigned at this task
    	List<AssignmentPerson> assigPersons = assignmentService.findAssignmentPersonByTask(task);    	
    	List<HistoryPersonDTO> assigPersonsDTO = getHPersonOfThisAssignment(assigPersons);
    	
    	// WORKLOAD 
    	//------------------------------------------------
    	// Get the workload assigned at this task
    	List<Workload> workloads = assignmentService.findWorkloadByTask(task);
    	JSONArray workloadJson = JsonConversor.geWorkloadAsJSON(workloads);
    	
    	
    	// CREATE THE MODEL
    	//------------------------------------------------
    	// Basic information of the Task
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		
		model.addAttribute("assignedPersons", assigPersonsDTO);
		model.addAttribute("workloads", workloadJson);
		
		
		return "task/workloads";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/workload || Get the workload assigned at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/workloads",
					method=RequestMethod.POST)
	public String addWorkload(@Valid @ModelAttribute("workload") WorkloadDTO workloadDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
				   @PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the DTO to object
		Workload workload = WorkloadDTOConversor.toWorkload(workloadDTO);
		
		// Create the workload
		assignmentService.createWorkload(workload);		
		
		return showWorkload(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	//------------------------------------------- TASK MATERIALS ------------------------------------------  
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/materials || Get the materials assigned at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/materials",  
					method=RequestMethod.GET)
    public String showMaterials(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
		Long longIdTask = Long.parseLong(idTask, 10);
		
		// Find all the materials
		List<Material> materials = materialService.findAllMaterials();
		
		// Convert the previous object to DTO
		List<MaterialDTO> materialsDTO = MaterialDTOConversor.toMaterialDTOList(materials);
		
		// Find the assignment of materials
		Task task = projectService.findTask(longIdTask);
		
		List<AssignmentMaterial> amPlan = assignmentService.findAssignmentMaterialByTaskPlan(task);
		List<AssignmentMaterialDTO> amPlanDTO = AssignmentMaterialDTOConversor.toAssignmentMaterialDTOList(amPlan);
		
		List<AssignmentMaterial> amReal = assignmentService.findAssignmentMaterialByTaskReal(task);
		List<AssignmentMaterialDTO> amRealDTO = AssignmentMaterialDTOConversor.toAssignmentMaterialDTOList(amReal);
		
		// Create the model
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		model.addAttribute("materials", materialsDTO);
		model.addAttribute("assignedPlanMaterials", amPlanDTO);
		model.addAttribute("assignedRealMaterials", amRealDTO);
    	
		return "task/materials";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/materials || Assignment of planned materials to the task.   
	//-----------------------------------------------------------------------------------------------------
	private Long existsThisAssignment(Long idTask, Long idMaterial) throws InstanceNotFoundException {
		
		Long idAssignmentMaterial = null;
		
		Task task = projectService.findTask(idTask);
		List<AssignmentMaterial> ams = assignmentService.findAssignmentMaterialByTask(task);
		
		for (AssignmentMaterial am : ams) {
			if (am.getMaterial().getId() == idMaterial) {
				idAssignmentMaterial = am.getId();
				break;
			}
		}
		
		return idAssignmentMaterial;
	}
	
	
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/materials",  
					method=RequestMethod.POST)
    public String assignPlanMaterials(@Valid @ModelAttribute("assignMaterial") AssignmentMaterialDTO amDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Check if the assignment is in the planning or execution
		if (amDTO.isPlan()) {
			
			AssignmentMaterial am = AssignmentMaterialDTOConversor.toAAssignmentMaterial(amDTO);
			assignmentService.createAssignmentMaterial(am);
			
		} else if (amDTO.isReal()) {
			
			// Check if the assignment already exits 
			Long idExistedAssigMat = existsThisAssignment(amDTO.getIdTask(), amDTO.getIdMaterial());
			
			if (idExistedAssigMat != null) {
				
				// Get the AssignmentMaterial
				AssignmentMaterial am = assignmentService.findAssignmentMaterial(idExistedAssigMat);
				
				// Update the data
				am.setReal(true);
				am.setUnitsReal(amDTO.getUnitsPlan());
				assignmentService.updateAssignmentMaterial(am);				
			} 
			
			else {
				
				// Set the realUnits
				amDTO.setUnitsReal(amDTO.getUnitsPlan());
				amDTO.setUnitsPlan(null);
				
				// Create the assignment
				AssignmentMaterial am = AssignmentMaterialDTOConversor.toAAssignmentMaterial(amDTO);
				assignmentService.createAssignmentMaterial(am);
			}
		}

		
		return showMaterials(idProject, idPhase, idTask, model); 
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/materials/id/delete || Update an assignment materials.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/materials/{idAM}/update",  
					method=RequestMethod.POST)
	public String updateAssignedMaterial(@Valid @ModelAttribute("assignMaterial") AssignmentMaterialDTO amDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAM, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		Long longIdAM = Long.parseLong(idAM, 10);
		
		AssignmentMaterial am = assignmentService.findAssignmentMaterial(longIdAM);
			
		// Update the units in function of the task state
		if (amDTO.isPlan()) {
			am.setUnitsPlan(amDTO.getUnitsPlan());
		}
		else if (amDTO.isReal()) {
			am.setUnitsReal(amDTO.getUnitsPlan());
		}
			
		// Update the object in the database
		assignmentService.updateAssignmentMaterial(am);
		
		// Return the main material menu
		return showMaterials(idProject, idPhase, idTask, model); 
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/materials/id/delete || Delete of an assignment materials.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/materials/{idAM}/delete",  
					method=RequestMethod.POST)
	public String deleteAssignedMaterial(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idAM, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdAM = Long.parseLong(idAM, 10);
    	
    	// Remove the assignment
    	assignmentService.removeAssignmentMaterial(longIdAM);
    	
    	// Return to the main material menu
		return showMaterials(idProject, idPhase, idTask, model); 
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	//------------------------------------------- TASK INCIDENTS ------------------------------------------  
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id/incidents || Get the incidents assigned at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/incidents",
					method=RequestMethod.GET)
    public String showIncidents(@PathVariable String idProject, @PathVariable String idPhase,
    					   		@PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdTask = Long.parseLong(idTask, 10);
    	
    	// Get the task
    	Task task = projectService.findTask(longIdTask);
    	
    	// Get the incidents of this task
		List<TaskIncident> taskIncidents = projectService.findTaskIncidentByTask(task);
		List<TaskIncidentDTO> taskIncidentsDTO = TaskIncidentDTOConversor.toTaskIncidentDTOList(taskIncidents);
		
		// Get the material description information as JSON
		JSONArray incidentResults = JsonConversor.geIncidentResultAsJSON(taskIncidentsDTO);
		
		// Create the model
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		model.addAttribute("incidents", taskIncidentsDTO);
		model.addAttribute("results", incidentResults);
		
		return "task/incidents";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/incidents || Add a new incident at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/incidents",
					method=RequestMethod.POST)
    public String addIncidents(@Valid @ModelAttribute("incident") TaskIncidentDTO taskIncidentDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
	
		if (result.hasErrors()) {
            return "error";
        }
		
		// First we must create the Incident object 
		Incident incident = TaskIncidentDTOConversor.toIncident(taskIncidentDTO);
		projectService.createIncident(incident);
		
		// Now create the TaskIncident object 
		taskIncidentDTO.setIdIncident(incident.getId());
		TaskIncident taskIncident = TaskIncidentDTOConversor.toTaskIncident(taskIncidentDTO);
		
		projectService.createTaskIncident(taskIncident);
		
		// Return the main task incident menu
		return showIncidents(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/incidents/update || Get the incidents assigned at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/incidents/{idTaskIncident}/update",
					method=RequestMethod.POST)
    public String updateIncidents(@Valid @ModelAttribute("incident") TaskIncidentDTO taskIncidentDTO,  
    		BindingResult result, @PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idTaskIncident, Model model) throws InstanceNotFoundException, InputValidationException {
	
		if (result.hasErrors()) {
            return "error";
        }
		
		// Update the Incident object 
		Incident incident = TaskIncidentDTOConversor.toIncident(taskIncidentDTO);
		projectService.updateIncident(incident);
		
		// Now update the TaskIncident object 
		TaskIncident taskIncident = TaskIncidentDTOConversor.toTaskIncident(taskIncidentDTO);
		projectService.updateTaskIncident(taskIncident);
		
		// Return the main task incident menu
		return showIncidents(idProject, idPhase, idTask, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/incidents/delete || Get the incidents assigned at this task.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/incidents/{idTaskIncident}/delete",
					method=RequestMethod.POST)
    public String deleteIncidents(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, @PathVariable String idTaskIncident, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdTaskIncident = Long.parseLong(idTaskIncident, 10);
    	
		// Get the id of the incident
		TaskIncident taskIncident = projectService.findTaskIncident(longIdTaskIncident);
		Long idIncident = taskIncident.getIncident().getId();
		
		// Delete the TaskIncident and the Incident
		projectService.removeTaskIncident(longIdTaskIncident);
		projectService.removeIncident(idIncident);
		
		// Return the main task incident menu
		return showIncidents(idProject, idPhase, idTask, model);
	}
}
