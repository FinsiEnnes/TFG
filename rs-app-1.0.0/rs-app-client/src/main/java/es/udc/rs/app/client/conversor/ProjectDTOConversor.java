package es.udc.rs.app.client.conversor;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.ProjectDTO;
import es.udc.rs.app.client.util.ClientConstants;
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
	
	private static String betweenDates(Date firstDate, Date secondDate) {
		
		if (firstDate==null || secondDate==null) {
			return ClientConstants.NOT_AVAILABLE;
		}
		
	    long daysDiff = ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
	    return Long.toString(Math.abs(daysDiff));
	}
	
	public static ProjectDTO toProjectDTO(Project project) {
		
		ProjectDTO projectDTO = new ProjectDTO();
		
		// Get the type of project
		String type = ((project.isInner()) ? "Interno" : "Externo");
		
		// Basic project information
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
		
		// Statics of the project
		projectDTO.setIniPlan(ClientUtilMethods.convertDateToString(project.getIniPlan()));
		projectDTO.setIniReal(ClientUtilMethods.convertDateToString(project.getIniReal()));
		projectDTO.setIniVar(betweenDates(project.getIniPlan(),project.getIniReal()));
		projectDTO.setEndPlan(ClientUtilMethods.convertDateToString(project.getEndPlan()));
		projectDTO.setEndReal(ClientUtilMethods.convertDateToString(project.getEndReal()));
		projectDTO.setEndVar(betweenDates(project.getEndPlan(), project.getEndReal()));
		projectDTO.setDaysPlan(project.getDaysPlan());
		projectDTO.setDaysReal(project.getDaysReal());
		projectDTO.setDaysVar(Math.abs(project.getDaysPlan() - project.getDaysReal()));
		projectDTO.setHoursPlan(project.getHoursPlan());
		projectDTO.setHoursReal(project.getHoursReal());
		projectDTO.setHoursVar(Math.abs(project.getHoursPlan() - project.getHoursReal()));
		projectDTO.setCostPlan(project.getCostPlan());
		projectDTO.setCostReal(project.getCostReal());
		projectDTO.setCostVar(Math.abs(project.getCostPlan() - project.getCostReal()));
		projectDTO.setProfitPlan(project.getProfitPlan());
		projectDTO.setProfitReal(project.getProfitReal());
		projectDTO.setProfitVar(Math.abs(project.getProfitPlan() - project.getProfitReal()));
		projectDTO.setLoss(project.getLoss());
		projectDTO.setProgress(project.getProgress());
		
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
