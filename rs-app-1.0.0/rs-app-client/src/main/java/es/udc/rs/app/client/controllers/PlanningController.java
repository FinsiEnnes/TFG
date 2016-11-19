package es.udc.rs.app.client.controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
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
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
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
	// Create a JSON with the data of Phase. 
	//-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private JSONObject getPhaseJSON(Phase phase) {
		
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		String idPhase = "p" + phase.getId();
		
    	jsonObject.put("id", idPhase);
    	jsonObject.put("text", phase.getName());
    	
    	jsonObject.put("start_date", fmt.format(phase.getIni()));
    	if (phase.getEnd() != null) {
        	jsonObject.put("end", fmt.format(phase.getEnd()));
    	}
    	
    	jsonObject.put("open", true);
    	jsonObject.put("color", "#2bd615");
    	
    	return jsonObject;
	}
	
	//-----------------------------------------------------------------------------------------------------
	// Create a JSON with the data of Task. 
	//-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private JSONObject getTaskJSON(Task task) {
		
		JSONObject jsonObject = new JSONObject();
    	SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

		String idPhase = "p" + task.getPhase().getId();
		String idTask = idPhase + "-t" + task.getId();
		
    	jsonObject.put("id", idTask);
    	jsonObject.put("text", task.getName());
    	jsonObject.put("start_date", fmt.format(task.getIniPlan()));
    	jsonObject.put("end", fmt.format(task.getEndPlan()));
    	jsonObject.put("duration", task.getDaysPlan());
    	jsonObject.put("parent", idPhase);
    	
    	return jsonObject;
	}
	
	//-----------------------------------------------------------------------------------------------------
	// Create a JSON with the data of Milestone. 
	//-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private JSONObject getMilestoneJSON(Milestone milestone) {
		
		JSONObject jsonObject = new JSONObject();
    	SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

		String idPhase = "p" + milestone.getPhase().getId();
		String idMilestone = idPhase + "-m" + milestone.getId();
		
		String start = fmt.format(milestone.getDatePlan());
		String end = fmt.format(ClientUtilMethods.plusDay(milestone.getDatePlan()));
		
    	jsonObject.put("id", idMilestone);
    	jsonObject.put("text", milestone.getName());
    	jsonObject.put("start_date", start);
    	jsonObject.put("duration", 1);
    	jsonObject.put("end", end);
    	jsonObject.put("parent", idPhase);
    	jsonObject.put("color", "#ff1ac6");
    	
    	return jsonObject;
	}
	
	//-----------------------------------------------------------------------------------------------------
	// Create a JSON with the data of Predecessors. 
	//-----------------------------------------------------------------------------------------------------
	private String getTypeLink(String typeLink) {
		
		String type = "0";
		
		if (typeLink.equals("FC")) {
			type = "0";
		} else if (typeLink.equals("CC")) {
			type = "1";
		} else if (typeLink.equals("FF")) {
			type = "2";
		} else if (typeLink.equals("CF")) {
			type = "3";
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getLinkJSON(Predecessor link) {
		
		JSONObject jsonObject = new JSONObject();

		String idSource = "p" + link.getTaskPred().getPhase().getId() + "-t" + link.getTaskPred().getId();
		String idTarget = "p" + link.getTask().getPhase().getId() + "-t" + link.getTask().getId();
		String typeLink = getTypeLink(link.getLinkType().getId());
				
    	jsonObject.put("id", link.getId());
    	jsonObject.put("source", idSource);
    	jsonObject.put("target", idTarget);
    	jsonObject.put("type", typeLink);
    	
    	return jsonObject;
	}
	
	//-----------------------------------------------------------------------------------------------------
	// Here it is formed the JSON with all the data project. This JSON will be processed by the chart  
	//-----------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
	private JSONObject getDataProjectAsJSON(List<Phase> phases, List<Task> tasks, 
											List<Milestone> milestones, List<Predecessor> links) {
		
    	// Get the size of each list and initialize the JSON object
		JSONObject mainObj = new JSONObject();
		int nPhases = phases.size();
		int nTasks = tasks.size();
		int nMiles = milestones.size();
		int nLinks = links.size();
		
		// In this jsonArray we add the data of phases, tasks and milestones as jsonObjects
		JSONArray jsonArrayData = new JSONArray();
		
		// This is for the link task data
		JSONArray jsonArrayLink = new JSONArray();
		
		// The jsonObject each element of the previous arrays
		JSONObject jsonObject;
		
		// Convert the phases info
		for(int i=0; i<nPhases; i++){
			jsonObject = getPhaseJSON(phases.get(i));
	    	jsonArrayData.add(jsonObject);
		}
		
		// Convert the Tasks info
		for(int i=0; i<nTasks; i++){
			jsonObject = getTaskJSON(tasks.get(i));
	    	jsonArrayData.add(jsonObject);
		}
		
		// Convert the Milestone info
		for(int i=0; i<nMiles; i++){
			jsonObject = getMilestoneJSON(milestones.get(i));
	    	jsonArrayData.add(jsonObject);
		}
		
		// Convert the Predecessor info
		for(int i=0; i<nLinks; i++){
			jsonObject = getLinkJSON(links.get(i));
	    	jsonArrayLink.add(jsonObject);
		}
		
		// Create and return the result
		mainObj.put("data", jsonArrayData);
		mainObj.put("links", jsonArrayLink);
		return mainObj;
	}
	
	
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
    	
    	// Convert the previous data to JSON format
    	JSONObject mainObj = getDataProjectAsJSON(phases, tasks, milestones, links);
    	
    	// Convert the list Phase object to PhaseDTO because we need show this info
    	List<PhaseDTO> phasesDTO = PhaseDTOConversor.toPhaseDTOList(phases);
    	
    	// We also need some historyPerson info
    	List<HistoryPerson> hps = personService.findCurrentHistoryPersons();
    	List<HistoryPersonDTO> hpsDTO = HistoryPersonDTOConversor.toHistoryPersonDTOs(hps);
    	
    	log.info(mainObj.toString());

    	// Send the data out to the model
    	model.addAttribute("idProject", id);
    	model.addAttribute("dataProject", mainObj);
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
