package es.udc.rs.app.client.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.ProjectDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;


@Component
public class ProjectDTOConversor {

	private static CustomerService customerService;
	
	@Autowired
    public ProjectDTOConversor(CustomerService customerService) {
		ProjectDTOConversor.customerService = customerService;
    }
	
	public static ProjectDTO toProjectDTO(Project project) {
		
		ProjectDTO projectDTO = new ProjectDTO();
		
		// Get the type of project
		String type = ((project.isInner()) ? "Interno" : "Externo");
		
		// Create the object ProjectDTO
		projectDTO.setId(project.getId());
		projectDTO.setName(project.getName());
		projectDTO.setDescription(project.getDescription());
		projectDTO.setStateDate(ClientUtilMethods.convertDateToString(project.getStateDate()));
		projectDTO.setType(type);
		projectDTO.setComment(project.getComment());
		projectDTO.setIdProvince(project.getProvince().getId());
		projectDTO.setProvince(project.getProvince().getName());
		projectDTO.setCountry(project.getProvince().getCountry().getName());
		projectDTO.setBudget(project.getBudget());
		projectDTO.setIniPlan(ClientUtilMethods.convertDateToString(project.getIniPlan()));
		
		// Return the converted object
		return projectDTO;
	}
	
	
	public static Project toProject(ProjectDTO projectDTO) throws InstanceNotFoundException {
		
		Project project = new Project();
		
		// Check if the project is inner
		boolean inner = (projectDTO.getType().equals("Interno"));
		
		// Get the Province
		Province province = customerService.findProvince(projectDTO.getIdProvince());
		
		// Create the object ProjectDTO
		project.setId(projectDTO.getId());
		project.setName(projectDTO.getName());
		project.setDescription(projectDTO.getDescription());
		project.setStateDate(ClientUtilMethods.toDate(projectDTO.getStateDate()));
		project.setInner(inner);
		project.setComment(projectDTO.getComment());
		project.setProvince(province);
		project.setBudget(projectDTO.getBudget());
		project.setIniPlan(ClientUtilMethods.toDate(projectDTO.getIniPlan()));
		
		// Return the converted object
		return project;
	}
}
