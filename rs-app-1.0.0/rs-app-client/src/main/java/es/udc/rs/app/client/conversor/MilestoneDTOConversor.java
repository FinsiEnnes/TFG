package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.Date;
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
		
		// Get the datas
		Date planDate = (milestoneDTO.getDatePlan() == null) ? null : ClientUtilMethods.toDate(milestoneDTO.getDatePlan());
		Date realDate = (milestoneDTO.getDateReal() == null) ? null : ClientUtilMethods.toDate(milestoneDTO.getDateReal());
		
		// Get the comment
		String comment = (milestoneDTO.getComment() == null) ? null : milestoneDTO.getComment().trim();

		// Create the Milestone object
		milestone.setId(milestoneDTO.getId());
		milestone.setPhase(phase);
		milestone.setName(milestoneDTO.getName());
		milestone.setDatePlan(planDate);
		milestone.setDateReal(realDate);
		milestone.setComment(comment);
		
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
		
		// Get the datas
		String planDate = (milestone.getDatePlan() == null) ? null : ClientUtilMethods.convertDateToString(milestone.getDatePlan());
		String realDate = (milestone.getDateReal() == null) ? null : ClientUtilMethods.convertDateToString(milestone.getDateReal());
				
		milestoneDTO.setId(milestone.getId());
		milestoneDTO.setIdPhase(milestone.getPhase().getId());
		milestoneDTO.setNamePhase(milestone.getPhase().getName());
		milestoneDTO.setName(milestone.getName());
		milestoneDTO.setDatePlan(planDate);
		milestoneDTO.setDateReal(realDate);
		milestoneDTO.setComment(milestone.getComment());
		
		return milestoneDTO;
	}
}
