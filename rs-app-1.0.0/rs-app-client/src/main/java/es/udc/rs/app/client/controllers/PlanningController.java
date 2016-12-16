package es.udc.rs.app.client.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.HistoryPersonDTOConversor;
import es.udc.rs.app.client.conversor.MilestoneDTOConversor;
import es.udc.rs.app.client.conversor.PhaseDTOConversor;
import es.udc.rs.app.client.conversor.TaskDTOConversor;
import es.udc.rs.app.client.dto.HistoryPersonDTO;
import es.udc.rs.app.client.dto.MilestoneDTO;
import es.udc.rs.app.client.dto.PhaseDTO;
import es.udc.rs.app.client.dto.TaskDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.client.util.JsonConversor;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class PlanningController {
	
	static Logger log = Logger.getLogger("project");
	
	@Autowired 
	private ProjectService projectService;
	
	@Autowired
	private PersonService personService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /project/id/planning || Show the project planning.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/project/{idProject}/planning",  method=RequestMethod.GET)
    public String projectPlanning(@PathVariable String idProject, Model model) throws InstanceNotFoundException {
    	
    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);
    	
    	// Get the Phases, Tasks and Milestones of this Project
    	List<Phase> phases = projectService.findPhaseByProject(id);
    	List<Task>  tasks  = projectService.findProjectTasks(id);
    	List<Milestone> milestones = projectService.findMilestonesByProject(id);
    	List<Predecessor> links = projectService.findPredecessorByProject(id);
    	
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
    	
    	log.info(mainObj.toString());

    	// Find the first task
		Task task = projectService.findFirstTask(id);
		
    	// Send the data out to the model
    	model.addAttribute("idProject", id);
    	model.addAttribute("idPhase", task.getPhase().getId());
		model.addAttribute("idTask", task.getId());
    	model.addAttribute("dataProject", mainObj);
    	model.addAttribute("taskDetails", taskObj);
    	model.addAttribute("msDetails", msObj);
    	model.addAttribute("phases", phasesDTO);
    	model.addAttribute("persons", hpsDTO);
    	
    	return "planning/projectPlan";
    }
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/phase || Add a new Phase at the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/project/{idProject}/phase",  method=RequestMethod.POST)
    public String createPhase(@Valid @ModelAttribute("phase")PhaseDTO phaseDTO, 
   		 BindingResult result, @PathVariable String idProject, Model model, HttpServletRequest request) 
   				 throws InstanceNotFoundException {
		
		if (result.hasErrors()) {
            return "error";
        }
    	
		// We need catch the date like this because is a text in the html
		String iniPhase = request.getParameter("ini");
		phaseDTO.setIni(ClientUtilMethods.toDate(iniPhase));
		
    	// Convert the PhaseDTO to Phase
    	Phase phase = PhaseDTOConversor.toPhase(phaseDTO);
    	
    	// Create the new phase
    	projectService.createPhase(phase);
    	
    	// Send the data project	
		return projectPlanning(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/task || Add a new Task at the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/project/{idProject}/task",  method=RequestMethod.POST)
    public String createTask(@Valid @ModelAttribute("task") TaskDTO taskDTO, 
   		 BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
    	
		// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);
		
		// Get the current project state
		Project project = projectService.findProject(id);
		HistoryProject currentHP = projectService.findCurrentHistoryProject(project);
		String state = currentHP.getState().getId();
		
		// Now set the state to the task
		taskDTO.setIdState(state);
    	
		// Convert the TaskDTO to Task
		Task task = TaskDTOConversor.toTask(taskDTO);
		
		// Create the task
		projectService.createTask(task);
    	
    	// Send the data project	
		return projectPlanning(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/task/id/update || Update a Task of the project.   
	//-----------------------------------------------------------------------------------------------------
	private void updateTaskAttributes(Task task, HttpServletRequest request) throws InstanceNotFoundException, InputValidationException {
		
		// The user ever can update the name and priority task
		String name =  request.getParameter("name");
		Priority priority =  projectService.findPriority(request.getParameter("idPriority"));
		
		task.setName(name);
		task.setPriority(priority);
		
		// Update in function of the state
		String state =  request.getParameter("state");
		
		if (state.equals("Planificacion")) {	
			String iniPlan =  request.getParameter("iniPlan");
			Integer daysPlan =  Integer.parseInt(request.getParameter("daysPlan"));
			
			task.setIniPlan(ClientUtilMethods.toDate(iniPlan));
			task.setDaysPlan(daysPlan);	
		} 
		else if (state.equals("Preparado")) {
			String iniReal =  request.getParameter("iniReal");
			task.setIniReal(ClientUtilMethods.toDate(iniReal));
		}
		
		// Now update the task
		projectService.updateTask(task);
	}
	
	@RequestMapping(value="/project/{idProject}/task/{idTask}/update",  method=RequestMethod.POST)
    public String updateTask (@Valid @ModelAttribute("task") TaskDTO taskDTO, HttpServletRequest request, BindingResult result, 
    						  @PathVariable String idProject, @PathVariable String idTask, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the string id to long
    	Long idTaskL = Long.parseLong(idTask, 10);
    	
    	// Find this task and update its attributes
		Task task = projectService.findTask(idTaskL);
		updateTaskAttributes(task, request);

		
		// Convert the TaskDTO to Task
		//Task task = TaskDTOConversor.toTask(taskDTO);
		
		// Send the data project	
		return projectPlanning(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/milestone || Add a new Milestone at the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/project/{idProject}/milestone",  method=RequestMethod.POST)
    public String createMilestone(@Valid @ModelAttribute("milestone") MilestoneDTO milestoneDTO, 
   		 BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the MilestoneDTO to Milestone
		Milestone milestone = MilestoneDTOConversor.toMilestone(milestoneDTO);
		
		// Create the milestone
		projectService.createMilestone(milestone);
		
		// Send the data project	
		return projectPlanning(idProject, model);
	}

}
