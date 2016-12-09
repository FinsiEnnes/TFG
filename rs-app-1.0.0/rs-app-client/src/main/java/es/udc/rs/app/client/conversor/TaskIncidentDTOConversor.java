package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.TaskIncidentDTO;
import es.udc.rs.app.model.domain.TaskIncident;

public class TaskIncidentDTOConversor {

	public static List<TaskIncidentDTO> toTaskIncidentDTOList(List<TaskIncident> tiList) {
		
		List<TaskIncidentDTO> tiDTOList = new ArrayList<TaskIncidentDTO>();
		
		for (TaskIncident ti :  tiList) {
			tiDTOList.add(toTaskIncidentDTO(ti));
		}
		
		return tiDTOList;
	}
	
	
	public static TaskIncidentDTO toTaskIncidentDTO(TaskIncident taskIncident) {
		
		TaskIncidentDTO taskIncidentDTO = new TaskIncidentDTO();
		
		// Set the attributes 
		taskIncidentDTO.setId(taskIncident.getId());
		taskIncidentDTO.setIdIncident(taskIncident.getIncident().getId());
		taskIncidentDTO.setIdTask(taskIncident.getTask().getId());
		taskIncidentDTO.setReason(taskIncident.getIncident().getReason());
		taskIncidentDTO.setDamage(taskIncident.getIncident().getDamage().getName());
		taskIncidentDTO.setLoss(taskIncident.getLoss());
		taskIncidentDTO.setResult(taskIncident.getIncident().getResult());
		
		return taskIncidentDTO;
	}
}
