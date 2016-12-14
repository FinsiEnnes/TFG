package es.udc.rs.app.client.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.WorkloadDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.Workload;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class WorkloadDTOConversor {

	private static ProjectService projectService;
	private static PersonService personService;
	
	@Autowired
    public WorkloadDTOConversor(ProjectService projectService, PersonService personService) {
		WorkloadDTOConversor.projectService = projectService;
		WorkloadDTOConversor.personService = personService;
    }
	
	public static Workload toWorkload (WorkloadDTO workloadDTO) throws InstanceNotFoundException {
		
		Workload workload = new Workload();
		
		// Get the task and the historyPerson
		Task task = projectService.findTask(workloadDTO.getIdTask());
		HistoryPerson hPerson = personService.findHistoryPerson(workloadDTO.getIdHPerson());
		
		// Create the object Workload
		workload.setId(workloadDTO.getId());
		workload.setTask(task);
		workload.setHistoryPerson(hPerson);
		workload.setDayDate(ClientUtilMethods.toDate(workloadDTO.getDayDate()));
		workload.setHours(workloadDTO.getHours());
		workload.setExtraHours(workloadDTO.getExtraHours());
		
		// Return the result
		return workload;
	}
}
