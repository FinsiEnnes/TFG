package es.udc.rs.app.client.controllers;


import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.GenericJDBCException;
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
import es.udc.rs.app.client.conversor.TaskDTOConversor;
import es.udc.rs.app.client.dto.HistoryPersonDTO;
import es.udc.rs.app.client.dto.PhaseDTO;
import es.udc.rs.app.client.dto.TaskDTO;
import es.udc.rs.app.client.util.JsonConversor;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class TaskController {
	
	@Autowired 
	private ProjectService projectService;
	
	@Autowired 
	private PersonService personService;
	
	
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
		originalTask.setPhase(taskUpdatedBasicInfo.getPhase());
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
	// [POST]-> /projects/id/phases/id/tasks/id/prepare || Change the task status to prepare.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/prepare",  
					method=RequestMethod.POST)
    public String prepareTask(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
    	// Convert the string id to long
    	Long id = Long.parseLong(idTask, 10);
    	
		// Get the prepare state
		State prepare = projectService.findState("PRPD");
		
		// Find the Task and set the new state
		Task task = projectService.findTask(id);
		task.setState(prepare);
		
		// Update the Task
		try {
			projectService.updateTask(task);
		} catch (DataAccessException e) {
			String msg = e.getCause().getCause().getMessage();
	    	model.addAttribute("msg", msg);
	    	model.addAttribute("feedback", "active");
		}
		
		return showTask(idProject, idPhase, idTask, model);
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/phases/id/tasks/id/cancel || Change the task status to cancel.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}/cancel",  
					method=RequestMethod.POST)
    public String cancelTask(@PathVariable String idProject, @PathVariable String idPhase,
    		@PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
    	// Convert the string id to long
    	Long id = Long.parseLong(idTask, 10);
    	
		// Get the state cancel
		State cancel = projectService.findState("CANC");
		
		// Find the Task and set the new state
		Task task = projectService.findTask(id);
		task.setState(cancel);
		
		// Update the Task
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

}
