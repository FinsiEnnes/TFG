package es.udc.rs.app.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class MilestoneController {

	@Autowired 
	private ProjectService projectService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/milestones || Get the milestones information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/milestones",  method=RequestMethod.GET)
    public String showMilestones(@PathVariable String idProject, @PathVariable String idPhase,
    					   @PathVariable String idTask, Model model) throws InstanceNotFoundException {
		
		return "milestone/milestones";
	}
}
