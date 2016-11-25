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
		
		// We set the customer attributes if there is a project customer
		if (project.getCustomer() != null) {
			projectDTO.setIdCustomer(project.getCustomer().getId());
			projectDTO.setNameContact(project.getNameContact());
			projectDTO.setSurnameContact(project.getSurnameContact());
			projectDTO.setNifContact(project.getNifContact());
			projectDTO.setEmailContact(project.getEmailContact());
		}
		
		// Statics of the project. Calculate the variation only if the statics aren't null
		// Days
		projectDTO.setDaysPlan(project.getDaysPlan());
		projectDTO.setDaysReal(project.getDaysReal());
		if (project.getDaysPlan()!=null && project.getDaysReal()!=null) {
			projectDTO.setDaysVar(Math.abs(project.getDaysPlan() - project.getDaysReal()));
		}
		
		// Initial date project
		projectDTO.setIniPlan(ClientUtilMethods.convertDateToString(project.getIniPlan()));
		projectDTO.setIniReal(ClientUtilMethods.convertDateToString(project.getIniReal()));
		if (project.getIniPlan()!=null && project.getIniReal()!=null) {
			projectDTO.setIniVar(betweenDates(project.getIniPlan(),project.getIniReal()));
		}
		
		// End date project
		projectDTO.setEndPlan(ClientUtilMethods.convertDateToString(project.getEndPlan()));
		projectDTO.setEndReal(ClientUtilMethods.convertDateToString(project.getEndReal()));
		if (project.getEndPlan()!=null && project.getEndReal()!=null) {
			projectDTO.setEndVar(betweenDates(project.getEndPlan(), project.getEndReal()));
		}

		// Hours 
		projectDTO.setHoursPlan(project.getHoursPlan());
		projectDTO.setHoursReal(project.getHoursReal());
		if (project.getHoursPlan()!=null && project.getHoursReal()!=null) {
			projectDTO.setHoursVar(Math.abs(project.getHoursPlan() - project.getHoursReal()));
		}
		
		// Cost
		projectDTO.setCostPlan(project.getCostPlan());
		projectDTO.setCostReal(project.getCostReal());
		if (project.getCostPlan()!=null && project.getCostReal()!=null) {
			projectDTO.setCostVar(Math.abs(project.getCostPlan() - project.getCostReal()));
		}
		
		// Profit
		projectDTO.setProfitPlan(project.getProfitPlan());
		projectDTO.setProfitReal(project.getProfitReal());
		if (project.getProfitPlan()!=null && project.getProfitReal()!=null) {
			projectDTO.setProfitVar(Math.abs(project.getProfitPlan() - project.getProfitReal()));
		}
		
		// Losses project and progress
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
