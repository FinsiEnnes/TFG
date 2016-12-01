package es.udc.rs.app.client.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.AssignmentPersonDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class AssignmentPersonDTOConversor {
	
	private static ProjectService projectService;
	private static PersonService personService;
	
	@Autowired
    public AssignmentPersonDTOConversor(ProjectService projectService, PersonService personService) {
		AssignmentPersonDTOConversor.projectService = projectService;
		AssignmentPersonDTOConversor.personService = personService;
    }
	
	public static AssignmentPerson toAssignmentPerson(AssignmentPersonDTO apDTO) throws InstanceNotFoundException {
		
		AssignmentPerson ap = new AssignmentPerson();
		
		// Get the Task and HistoryPerson
		Task task = projectService.findTask(apDTO.getIdTask());
		HistoryPerson hPerson = personService.findHistoryPerson(apDTO.getIdHistoryPerson());
		
		// Create the object AssignmentPerson
		ap.setId(apDTO.getId());
		ap.setTask(task);
		ap.setHistoryPerson(hPerson);
		ap.setConclude(apDTO.isConclude());
		ap.setTotalHours(apDTO.getTotalHours());
		ap.setTotalExtraHours(apDTO.getTotalExtraHours());
		ap.setTotalCost(apDTO.getTotalCost());
		
		return ap;
	}

}
