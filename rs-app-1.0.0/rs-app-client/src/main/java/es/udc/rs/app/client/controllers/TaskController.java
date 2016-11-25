package es.udc.rs.app.client.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.TaskDTOConversor;
import es.udc.rs.app.client.dto.TaskDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class TaskController {
	
	@Autowired 
	private ProjectService projectService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/phases/id/tasks/id || Getting of the principal Task information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/phases/{idPhase}/tasks/{idTask}",  method=RequestMethod.GET)
    public String showTask(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long longIdTask = Long.parseLong(idTask, 10);
    	
		// First we get the selected task
		Task task = projectService.findTask(longIdTask);
		TaskDTO taskDTO = TaskDTOConversor.toTaskDTO(task);
		
		//Create the model
    	model.addAttribute("task", taskDTO);
		
		return "task/info";
	}

}
