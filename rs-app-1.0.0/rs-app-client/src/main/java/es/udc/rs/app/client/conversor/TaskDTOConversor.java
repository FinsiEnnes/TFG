package es.udc.rs.app.client.conversor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.TaskDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class TaskDTOConversor {

	private static ProjectService projectService;
	private static PersonService personService;
	
	@Autowired
    public TaskDTOConversor(ProjectService projectService, PersonService personService) {
		TaskDTOConversor.projectService = projectService;
		TaskDTOConversor.personService = personService;
    }
	
	public static Task toTask(TaskDTO taskDTO) throws InstanceNotFoundException {
		
		// First get the complex object 
		Phase phase = projectService.findPhase(taskDTO.getIdPhase());
		HistoryPerson manager = personService.findHistoryPerson(taskDTO.getIdManager());
		Priority priority = projectService.findPriority(taskDTO.getIdPriority());
		State state = projectService.findState(taskDTO.getIdState());
		
		// Create the Task object
		Task task = new Task();
		
		task.setId(taskDTO.getId());
		task.setPhase(phase);
		task.setName(taskDTO.getName());
		task.setComment(taskDTO.getComment());
		task.setState(state);
		task.setPriority(priority);
		task.setHistoryPerson(manager);
		task.setDaysPlan(taskDTO.getDaysPlan());
		task.setDaysReal(taskDTO.getDaysReal());
		task.setIniPlan(ClientUtilMethods.toDate(taskDTO.getIniPlan()));
		task.setIniReal(ClientUtilMethods.toDate(taskDTO.getIniReal()));
		task.setHoursPlan(taskDTO.getHoursPlan());
		task.setHoursReal(taskDTO.getHoursReal());
		task.setEndPlan(ClientUtilMethods.toDate(taskDTO.getEndPlan()));
		task.setEndReal(ClientUtilMethods.toDate(taskDTO.getEndReal()));
		task.setCostPlan(taskDTO.getCostPlan());
		task.setCostReal(taskDTO.getCostReal());
		task.setProgress(taskDTO.getProgress());

		// Return the created object
		return task;
	}
	
}
