package es.udc.rs.app.client.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class PlanningController {
	
	static Logger log = Logger.getLogger("project");
	
	@Autowired 
	private ProjectService projectService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons/id || Show the person information included the aptitudes and times off.   
	//-----------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/project/{idProject}/planning",  method=RequestMethod.GET)
    public String projectPlanning(@PathVariable String idProject, Model model) throws InstanceNotFoundException {
    	

    	// Get the Person and convert it in PersonDTO
    	Long id = Long.parseLong(idProject, 10);
    	
    	// Get the Phases of this Project
    	List<Phase> phases = projectService.findPhaseByProject(id);
    	
    	// Get the Tasks of this Project
    	List<Task> tasks = projectService.findProjectTasks(id);
    	
    	
    	JSONObject jo = new JSONObject();
    	jo.put("id", 2);
    	jo.put("text", "Phase 1");

    	JSONObject je = new JSONObject();
    	je.put("id", 3);
    	je.put("text", "Task 1");
    	je.put("start_date", "02-11-2016");
    	je.put("duration", 3);
    	je.put("parent", 2);
    	

    	JSONArray ja = new JSONArray();
    	ja.add(jo);
    	
    	jo = new JSONObject();
    	jo.put("id", 5);
    	jo.put("text", "Phase 2");
    	
    	ja.add(jo);
    	ja.add(je);
    	
    	je = new JSONObject();
    	je.put("id", 4);
    	je.put("text", "Task 2");
    	je.put("start_date", "07-11-2016");
    	je.put("duration", 2);
    	je.put("parent", 2);
    	
    	ja.add(je);
    	
    	je = new JSONObject();
    	je.put("id", 6);
    	je.put("text", "Task 3");
    	je.put("start_date", "05-11-2016");
    	je.put("duration", 5);
    	je.put("parent", 5);
    	
    	ja.add(je);

    	JSONObject mainObj = new JSONObject();
    	mainObj.put("data", ja);
    	
    	log.info(mainObj.toString());

    	model.addAttribute("tasks", mainObj);
    	
    	return "planning/projectPlan";
    }

}
