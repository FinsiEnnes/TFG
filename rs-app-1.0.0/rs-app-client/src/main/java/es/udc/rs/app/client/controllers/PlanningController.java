package es.udc.rs.app.client.controllers;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlanningController {
	
	static Logger log = Logger.getLogger("project");
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons/id || Show the person information included the aptitudes and times off.   
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/project/{idProject}/planning",  method=RequestMethod.GET)
    public String projectPlanning(@PathVariable String idProject, Model model) {
    	
    	String tasks = "{data : [ {id : '2',	text : 'Phase #1', color : '#2eb82e'},"
    	+ "{id : '3', text : 'Task #1', start_date : '02-11-2016', duration : '3', parent : '2',progress : '0.2'}"
    	+ "{id : '4', text : 'Task #2', start_date : '07-11-2016', duration : '3', parent : '2',progress : '0.1'}"
    	+ " ]}";
    	
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
    	ja.add(je);
    	
    	je = new JSONObject();
    	je.put("id", 4);
    	je.put("text", "Task 2");
    	je.put("start_date", "07-11-2016");
    	je.put("duration", 2);
    	je.put("parent", 2);
    	
    	ja.add(je);

    	JSONObject mainObj = new JSONObject();
    	mainObj.put("data", ja);
    	
    	log.info(mainObj.toString());

    	model.addAttribute("tasks", mainObj);
    	
    	return "planning/projectPlan";
    }

}
