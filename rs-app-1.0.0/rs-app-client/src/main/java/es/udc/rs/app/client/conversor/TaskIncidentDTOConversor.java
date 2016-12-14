package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.TaskIncidentDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class TaskIncidentDTOConversor {

	private static ProjectService projectService;
	
	@Autowired
    public TaskIncidentDTOConversor(ProjectService projectService) {
		TaskIncidentDTOConversor.projectService = projectService;
    }
	
	
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
		taskIncidentDTO.setIdDamage(taskIncident.getIncident().getDamage().getId());
		taskIncidentDTO.setReason(taskIncident.getIncident().getReason());
		taskIncidentDTO.setDamage(taskIncident.getIncident().getDamage().getName());
		taskIncidentDTO.setLoss(taskIncident.getLoss());
		taskIncidentDTO.setResult(taskIncident.getIncident().getResult());
		
		return taskIncidentDTO;
	}
	
	
	public static TaskIncident toTaskIncident(TaskIncidentDTO taskIncidentDTO) throws InstanceNotFoundException {
	
		TaskIncident taskIncident = new TaskIncident();
		
		// Get the object Task and Incident
		Task task = projectService.findTask(taskIncidentDTO.getIdTask());
		Incident incident = projectService.findIncident(taskIncidentDTO.getIdIncident());
		
		// Set the attributes 
		taskIncident.setId(taskIncidentDTO.getId());
		taskIncident.setTask(task);
		taskIncident.setIncident(incident);
		taskIncident.setLoss(taskIncidentDTO.getLoss());
		
		return taskIncident;
	}
	
	
	public static Incident toIncident (TaskIncidentDTO taskIncidentDTO) throws InstanceNotFoundException {
	
		Incident incident = new Incident();
		
		// Get the object damage 
		Damage damage = projectService.findDamage(taskIncidentDTO.getIdDamage());
		
		// Set the attributes 
		incident.setId(taskIncidentDTO.getIdIncident());
		incident.setDamage(damage);
		incident.setReason(taskIncidentDTO.getReason());
		incident.setResult(taskIncidentDTO.getResult().trim());
		
		return incident;
	}
}
