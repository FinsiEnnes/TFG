package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.PhaseDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class PhaseDTOConversor {

	private static ProjectService projectService;
	
	@Autowired
    public PhaseDTOConversor(ProjectService projectService) {
		PhaseDTOConversor.projectService = projectService;
    }
	
	
	public static List<PhaseDTO> toPhaseDTOList(List<Phase> phases) {
		
		List<PhaseDTO> phasesDTO = new ArrayList<PhaseDTO>();
		
		for (Phase p : phases) {
			phasesDTO.add(toPhaseDTO(p));
		}
		return phasesDTO;
	}
	
	public static PhaseDTO toPhaseDTO(Phase phase) {
		
		PhaseDTO phaseDTO = new PhaseDTO();
		
		phaseDTO.setId(phase.getId());
		phaseDTO.setIdProject(phase.getProject().getId());
		phaseDTO.setName(phase.getName());
		phaseDTO.setIni(phase.getIni());
		phaseDTO.setEnd(phase.getEnd());
		
		return phaseDTO;
	}


	public static Phase toPhase(PhaseDTO phaseDTO) throws InstanceNotFoundException {
		
		Phase phase = new Phase();
		
		// Get the object Project
		Project project = projectService.findProject(phaseDTO.getIdProject());
		
		// Now we create the object Phase
		phase.setId(phaseDTO.getId());
		phase.setProject(project);
		phase.setName(phaseDTO.getName());
		phase.setIni(phaseDTO.getIni());
		phase.setEnd(phaseDTO.getEnd());
		
		return phase;
	}
}
