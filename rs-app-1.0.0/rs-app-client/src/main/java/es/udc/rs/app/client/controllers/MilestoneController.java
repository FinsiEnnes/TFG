package es.udc.rs.app.client.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.MilestoneDTOConversor;
import es.udc.rs.app.client.conversor.PhaseDTOConversor;
import es.udc.rs.app.client.dto.MilestoneDTO;
import es.udc.rs.app.client.dto.PhaseDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class MilestoneController {

	@Autowired 
	private ProjectService projectService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/milestones || Get the milestones information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/milestones",  method=RequestMethod.GET)
    public String showMilestones(@PathVariable String idProject, Model model) throws InstanceNotFoundException {
		
		// Convert the string id to long
		Long longIdProject =  Long.parseLong(idProject, 10);
		
		// Find the project milestones 
		List<Milestone> milestones = projectService.findMilestonesByProject(longIdProject);
		
		// Convert the object to DTO
		List<MilestoneDTO> milestonesDTO = MilestoneDTOConversor.toMilestoneDTOList(milestones);
		
		// Find the first task of this project and the phases
		Task task = projectService.findFirstTask(longIdProject);
		List<Phase> phases = projectService.findPhaseByProject(longIdProject);
		List<PhaseDTO> phasesDTO = PhaseDTOConversor.toPhaseDTOList(phases);
		
		// Create the model
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", task.getPhase().getId());
		model.addAttribute("idTask", task.getId());
		model.addAttribute("milestones", milestonesDTO);
		model.addAttribute("phases", phasesDTO);
		
		return "milestone/milestones";
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/milestone || Add a new Milestone at the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/milestones",  method=RequestMethod.POST)
    public String createMilestone(@Valid @ModelAttribute("milestone") MilestoneDTO milestoneDTO, 
   		 BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the MilestoneDTO to Milestone
		Milestone milestone = MilestoneDTOConversor.toMilestone(milestoneDTO);
		
		// Create the milestone
		projectService.createMilestone(milestone);
		
		return showMilestones(idProject, model);
	}
}
