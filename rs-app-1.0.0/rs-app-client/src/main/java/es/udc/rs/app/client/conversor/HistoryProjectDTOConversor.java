package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.HistoryProjectDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class HistoryProjectDTOConversor {
	
	private static ProjectService projectService;
	
	@Autowired
    public HistoryProjectDTOConversor(ProjectService projectService) {
		HistoryProjectDTOConversor.projectService = projectService;
    }
	
	public static List<HistoryProjectDTO> toHistoryProjectDTOList(List<HistoryProject> hps) {
		
		List<HistoryProjectDTO> hpsDTO = new ArrayList<HistoryProjectDTO>();
		
		for (HistoryProject p : hps) {
			hpsDTO.add(toHistoryProjectDTO(p));
		}
		return hpsDTO;
	}
	
	
	public static HistoryProjectDTO toHistoryProjectDTO(HistoryProject hp) {
		
		HistoryProjectDTO hpDTO = new HistoryProjectDTO();
		
		hpDTO.setId(hp.getId());
		hpDTO.setIdProject(hp.getProject().getId());
		hpDTO.setIdState(hp.getState().getId());
		hpDTO.setNameState(hp.getState().getName());
		hpDTO.setIni(ClientUtilMethods.convertDateToString(hp.getIni()));
		hpDTO.setComment(hp.getComment());
		
		// If it is the current history project then the end date is null
		if (hp.getEnd() == null) {
			hpDTO.setEnd("Actualidad");
		}
		else {
			hpDTO.setEnd(ClientUtilMethods.convertDateToString(hp.getEnd()));
		}
		
		return hpDTO;
	}
	
	
	public static HistoryProject toHistoryProject(HistoryProjectDTO hpDTO) throws InstanceNotFoundException {
		
		HistoryProject hp = new HistoryProject();
		
		// Get the Project and the State
		Project project = projectService.findProject(hpDTO.getIdProject());
		State state = projectService.findState(hpDTO.getIdState());
		
		// Get the end date
		Date end = ((hpDTO.getEnd().equals("Actualidad")) ? null : ClientUtilMethods.toDate(hpDTO.getEnd()));
		
		// Set the attributes
		hp.setId(hpDTO.getId());
		hp.setProject(project);
		hp.setState(state);
		hp.setIni(ClientUtilMethods.toDate(hpDTO.getIni()));
		hp.setEnd(end);
		hp.setComment(hpDTO.getComment());
		
		return hp;
	}

}
