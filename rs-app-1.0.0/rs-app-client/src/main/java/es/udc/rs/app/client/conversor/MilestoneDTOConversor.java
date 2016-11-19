package es.udc.rs.app.client.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.MilestoneDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class MilestoneDTOConversor {
	
	private static ProjectService projectService;
	
	@Autowired
    public MilestoneDTOConversor(ProjectService projectService) {
		MilestoneDTOConversor.projectService = projectService;
    }

	public static Milestone toMilestone(MilestoneDTO milestoneDTO) throws InstanceNotFoundException {
		
		Milestone milestone = new Milestone();
		
		// Get the Phase object
		Phase phase = projectService.findPhase(milestoneDTO.getIdPhase());
		
		// Create the Milestone object
		milestone.setId(milestoneDTO.getId());
		milestone.setPhase(phase);
		milestone.setName(milestoneDTO.getName());
		milestone.setDatePlan(ClientUtilMethods.toDate(milestoneDTO.getDatePlan()));
		milestone.setDateReal(ClientUtilMethods.toDate(milestoneDTO.getDateReal()));
		milestone.setComment(milestoneDTO.getComment());
		
		return milestone;		
	}
}
