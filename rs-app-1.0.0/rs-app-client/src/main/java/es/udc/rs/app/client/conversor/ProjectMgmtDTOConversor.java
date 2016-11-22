package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.ProjectMgmtDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.ProjectMgmt;

public class ProjectMgmtDTOConversor {
	
	
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
		projectMgmtDTO.setEnd(ClientUtilMethods.convertDateToString(projectMgmt.getEnd()));

		return projectMgmtDTO;
	}

}
