package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.PredecessorDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class PredecessorDTOConversor {
	
	private static ProjectService projectService;
	
	@Autowired
    public PredecessorDTOConversor(ProjectService projectService, PersonService personService) {
		PredecessorDTOConversor.projectService = projectService;
    }
	
	public static List<PredecessorDTO> toPredecessorDTOList(List<Predecessor> predecessors) throws InstanceNotFoundException {
	
		List<PredecessorDTO> predecessorDTOList = new ArrayList<PredecessorDTO>();
		
		for (Predecessor p : predecessors) {
			predecessorDTOList.add(toPredecessorDTO(p));
		}
		
		return predecessorDTOList;
	}
	
	public static PredecessorDTO toPredecessorDTO(Predecessor predecessor) throws InstanceNotFoundException {
		
		PredecessorDTO predecessorDTO = new PredecessorDTO();
		
		// Set the new attributes
		predecessorDTO.setId(predecessor.getId());
		predecessorDTO.setTask(predecessor.getTask().getId());
		predecessorDTO.setTaskPred(predecessor.getTaskPred().getId());
		predecessorDTO.setTaskPredName(predecessor.getTaskPred().getName());
		predecessorDTO.setLinkType(predecessor.getLinkType().getId());
		
		return predecessorDTO;
	}
	
	
	public static Predecessor toPredecessor(PredecessorDTO predecessorDTO) throws InstanceNotFoundException {
		
		Predecessor predecessor = new Predecessor();
				
		// Get the objects Task and the TaskLinkType
		Task task = projectService.findTask(predecessorDTO.getTask());
		Task taskPred = projectService.findTask(predecessorDTO.getTaskPred());
		TaskLinkType link = projectService.findTaskLinkType(predecessorDTO.getLinkType());
		
		// Set the new attributes
		predecessor.setId(predecessorDTO.getId());
		predecessor.setTask(task);
		predecessor.setTaskPred(taskPred);
		predecessor.setLinkType(link);
		
		return predecessor;
	}

}
