package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

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
		milestone.setComment(milestoneDTO.getComment().trim());
		
		return milestone;		
	}
	
	public static List<MilestoneDTO> toMilestoneDTOList(List<Milestone> milestoneList) throws InstanceNotFoundException {
		
		List<MilestoneDTO> milestoneDTOList = new ArrayList<MilestoneDTO>();
		
		for (Milestone m : milestoneList) {
			milestoneDTOList.add(toMilestoneDTO(m));
		}
		
		return milestoneDTOList;
		
	}
	
	public static MilestoneDTO toMilestoneDTO(Milestone milestone) throws InstanceNotFoundException {
		
		MilestoneDTO milestoneDTO = new MilestoneDTO();
		
		milestoneDTO.setId(milestone.getId());
		milestoneDTO.setIdPhase(milestone.getPhase().getId());
		milestoneDTO.setNamePhase(milestone.getPhase().getName());
		milestoneDTO.setName(milestone.getName());
		milestoneDTO.setDatePlan(ClientUtilMethods.convertDateToString(milestone.getDatePlan()));
		milestoneDTO.setDateReal(ClientUtilMethods.convertDateToString(milestone.getDateReal()));
		milestoneDTO.setComment(milestone.getComment());
		
		return milestoneDTO;
	}
}
