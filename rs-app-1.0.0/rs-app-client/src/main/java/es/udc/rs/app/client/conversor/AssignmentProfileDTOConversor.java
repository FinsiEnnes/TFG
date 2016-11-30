package es.udc.rs.app.client.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.AssignmentProfileDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class AssignmentProfileDTOConversor {
	
	private static ProjectService projectService;
	private static PersonService personService;
	
	@Autowired
    public AssignmentProfileDTOConversor(ProjectService projectService, PersonService personService) {
		AssignmentProfileDTOConversor.projectService = projectService;
		AssignmentProfileDTOConversor.personService = personService;
    }
	
	public static AssignmentProfile toAssignmentProfile(AssignmentProfileDTO apDTO) throws InstanceNotFoundException {
		
		AssignmentProfile ap = new AssignmentProfile();
		
		// Get the Task and Profile object
		Task task = projectService.findTask(apDTO.getIdTask());
		ProfessionalCategory pc = personService.findProfessionalCategory(apDTO.getIdProfCatg());
		
		// Create the object AssignmentProfile
		ap.setId(apDTO.getId());
		ap.setTask(task);
		ap.setProfCatg(pc);
		ap.setHoursPerPerson(apDTO.getHoursPerPerson());
		ap.setUnits(apDTO.getUnits());
		ap.setCost(apDTO.getCost());
		
		// Return the AssignmentProfile
		return ap;
	}

}
