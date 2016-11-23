package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.ProjectMgmtDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectMgmt;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class ProjectMgmtDTOConversor {
	
	private static ProjectService projectService;
	private static PersonService personService;
	
	@Autowired
    public ProjectMgmtDTOConversor(ProjectService projectService, PersonService personService) {
		ProjectMgmtDTOConversor.projectService = projectService;
		ProjectMgmtDTOConversor.personService = personService;
    }
	
	public static List<ProjectMgmtDTO> toProjectMgmtDTOList (List<ProjectMgmt> projectMgmtList) {
		
		List<ProjectMgmtDTO> projectMgmtDTOList = new ArrayList<ProjectMgmtDTO>();
		
		for (ProjectMgmt pm : projectMgmtList) {
			projectMgmtDTOList.add(toProjectMgmtDTO(pm));
		}
		
		return projectMgmtDTOList;
	}
	
	
	public static ProjectMgmtDTO toProjectMgmtDTO(ProjectMgmt projectMgmt) {
		
		ProjectMgmtDTO projectMgmtDTO = new ProjectMgmtDTO();
		
		Person person = projectMgmt.getHistoryPerson().getPerson();
		String fullNameManager = person.getName() + " " + person.getSurname1() + " " + person.getSurname2();
		
		projectMgmtDTO.setId(projectMgmt.getId());
		projectMgmtDTO.setIdProject(projectMgmt.getProject().getId());
		projectMgmtDTO.setIdHistoryPerson(projectMgmt.getHistoryPerson().getId());
		projectMgmtDTO.setNameManager(fullNameManager);
		projectMgmtDTO.setProfCatgManager(projectMgmt.getHistoryPerson().getProfcatg().getName());
		projectMgmtDTO.setLevelManager(projectMgmt.getHistoryPerson().getProfcatg().getLevel().getName());
		projectMgmtDTO.setIni(ClientUtilMethods.convertDateToString(projectMgmt.getIni()));
		
		// If the end date is null, then this person is currently a manager
		String end = ((projectMgmt.getEnd() == null) ? "Actualidad" : ClientUtilMethods.convertDateToString(projectMgmt.getEnd()));
		projectMgmtDTO.setEnd(end);
		

		return projectMgmtDTO;
	}
	
	public static ProjectMgmt toProjectMgmt(ProjectMgmtDTO projectMgmtDTO) throws InstanceNotFoundException {
		
		ProjectMgmt projectMgmt = new ProjectMgmt();
		
		// Get the Project and HistoryPerson
		Project project = projectService.findProject(projectMgmtDTO.getIdProject());
		HistoryPerson hp = personService.findHistoryPerson(projectMgmtDTO.getIdHistoryPerson());
		
		// Get the date end
		Date end = ((projectMgmtDTO.getEnd().equals("Actualidad") ? null : 
						ClientUtilMethods.toDate(projectMgmtDTO.getEnd())));
		
		// Create the object
		projectMgmt.setId(projectMgmtDTO.getId());
		projectMgmt.setProject(project);
		projectMgmt.setHistoryPerson(hp);
		projectMgmt.setIni(ClientUtilMethods.toDate(projectMgmtDTO.getIni()));
		projectMgmt.setEnd(end);
		
		return projectMgmt;
	}

}
