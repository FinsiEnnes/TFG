package es.udc.rs.app.client.util;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Task;

public class JsonConversor {

	//-----------------------------------------------------------------------------------------------------
	// Create a JSON with the data of Phase. 
	//-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private static JSONObject getPhaseJSON(Phase phase) {
		
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
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
	private static JSONObject getTaskJSON(Task task) {
		
		JSONObject jsonObject = new JSONObject();
    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

		String idPhase = "p" + task.getPhase().getId();
		String idTask = "t" + task.getId();
		
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
	private static JSONObject getMilestoneJSON(Milestone milestone) {
		
		JSONObject jsonObject = new JSONObject();
    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

		String idPhase = "p" + milestone.getPhase().getId();
		String idMilestone = "m" + milestone.getId();
		
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
	private static String getTypeLink(String typeLink) {
		
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
	private static JSONObject getLinkJSON(Predecessor link) {
		
		JSONObject jsonObject = new JSONObject();

		String idSource = "t" + link.getTaskPred().getId();
		String idTarget = "t" + link.getTask().getId();
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
	public static JSONObject getDataProjectAsJSON(List<Phase> phases, List<Task> tasks, 
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
	// Create a JSON with the data details of Task. 
	//-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private static JSONObject taskToJSON(Task task) {
		
		JSONObject jsonObject = new JSONObject();
    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		
    	jsonObject.put("name", task.getName());
    	jsonObject.put("state", task.getState().getName());
    	jsonObject.put("progress", task.getProgress());
    	jsonObject.put("idPriority", task.getPriority().getId());
    	jsonObject.put("priority", task.getPriority().getName());
    	jsonObject.put("iniPlan", fmt.format(task.getIniPlan()));
    	jsonObject.put("daysPlan", task.getDaysPlan());
    	
    	// Real data can be null
    	String iniReal = ((task.getIniReal() == null) ? null : fmt.format(task.getIniReal()));
    	String daysReal = ((task.getDaysReal() == null) ? null : String.valueOf(task.getDaysReal()));
    	
    	jsonObject.put("iniReal", iniReal);
    	jsonObject.put("daysReal", daysReal);
    	
    	return jsonObject;
	}
	
	
    @SuppressWarnings("unchecked")
 	public static JSONObject getTaskDetailsAsJSON(List<Task> tasks) {
    	
    	JSONObject mainObj = new JSONObject();
		int nTasks = tasks.size();
		
		// The jsonObject each element of the previous arrays
		JSONObject jsonObject;
		
		// Convert the Tasks info
		for(int i=0; i<nTasks; i++){
			String idTask = "t" + tasks.get(i).getId();
			
			jsonObject = taskToJSON(tasks.get(i));
			mainObj.put(idTask, jsonObject);
		}
    	
    	return mainObj;
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// Create a JSON with the data details of Milestone. 
	//-----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	private static JSONObject milestoneToJSON(Milestone milestone) {
		
		JSONObject jsonObject = new JSONObject();
    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		
    	jsonObject.put("name", milestone.getName());
    	jsonObject.put("datePlan", fmt.format(milestone.getDatePlan()));
    	
    	// Real data can be null
    	String dateReal = ((milestone.getDateReal() == null) ? null : fmt.format(milestone.getDateReal()));
    	String comment = ((milestone.getComment() == null) ? null : fmt.format(milestone.getComment()));
    	
    	jsonObject.put("dateReal", dateReal);
    	jsonObject.put("comment", comment);
    	
    	return jsonObject;
	}
	
	
    @SuppressWarnings("unchecked")
 	public static JSONObject getMilestoneDetailsAsJSON(List<Milestone> milestones) {
    	
    	JSONObject mainObj = new JSONObject();
    	JSONObject jsonObject;
		int nMilestones = milestones.size();
				
		
		// Convert the Milestones info
		for(int i=0; i<nMilestones; i++){
			String idMilestone = "m" + milestones.get(i).getId();
			
			jsonObject = milestoneToJSON(milestones.get(i));
			mainObj.put(idMilestone, jsonObject);
		}
    	
    	return mainObj;
    }
}
