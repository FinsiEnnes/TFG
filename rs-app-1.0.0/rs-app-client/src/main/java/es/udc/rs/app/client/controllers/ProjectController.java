package es.udc.rs.app.client.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.ProjectDTOConversor;
import es.udc.rs.app.client.conversor.ProvinceDTOConversor;
import es.udc.rs.app.client.dto.ProjectDTO;
import es.udc.rs.app.client.dto.ProvinceDTO;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class ProjectController {

	@Autowired 
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	
	private Project updateBasicProjectInfo(Project originalProj, Project updatedProj) {
		
		// Get the current project state
	   	HistoryProject currentHP = projectService.findCurrentHistoryProject(originalProj);
	   	String currentState = currentHP.getState().getId();

		// Change only the basic information
		originalProj.setName(updatedProj.getName());
		originalProj.setInner(updatedProj.isInner());
		originalProj.setProvince(updatedProj.getProvince());
		originalProj.setStateDate(updatedProj.getStateDate());
		originalProj.setBudget(updatedProj.getBudget());
		originalProj.setDescription(updatedProj.getDescription());
		originalProj.setComment(updatedProj.getComment());
		
		// Only if the project is in PLAN we can update the initial project data
		if (currentState.equals("PLAN")) {
			originalProj.setIniPlan(updatedProj.getIniPlan());
		}
		
		return originalProj;
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /project/id || Getting of the principal project information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}",  method=RequestMethod.GET)
    public String showProject(@PathVariable String idProject, Model model) throws InstanceNotFoundException {
		
    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);
    	
    	// Find this Project and convert it to DTO
    	Project project = projectService.findProject(id);
    	ProjectDTO projectDTO = ProjectDTOConversor.toProjectDTO(project);
    	
    	// Set the plan/real begin of the project
    	String iniProject = projectDTO.getIniPlan();
    	
    	HistoryProject currentState = projectService.findCurrentHistoryProject(project);
    	if (currentState.getState().getId().equals("EJEC")) {
    		iniProject = projectDTO.getIniReal();
    	}
    	
    	// Find the provinces
    	List<Province> provinces = customerService.findAllProvinces();
    	Long idProvinceProject =  projectDTO.getIdProvince();
    	List<ProvinceDTO> provincesDTO = ProvinceDTOConversor.toProvincesDTOExceptOne(provinces,idProvinceProject);
    	
    	// Create the model
    	model.addAttribute("project", projectDTO);
    	model.addAttribute("iniProject", iniProject);
    	model.addAttribute("provinces", provincesDTO);
    	
		
		return "project/info";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/update || Update only the basic project information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/update",  method=RequestMethod.POST)
    public String updateProject(@Valid @ModelAttribute("project") ProjectDTO projectDTO, BindingResult result,
    							@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {
		
		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);
		
		// Convert the projectDTO and get the original project
		Project updatedProj = ProjectDTOConversor.toProject(projectDTO);
		Project originalProj = projectService.findProject(id);
		
		// Update only the basic information
		Project originalProjUpdated = updateBasicProjectInfo(originalProj, updatedProj);
		projectService.updateProject(originalProjUpdated);
		
		return showProject(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /project/id/update || Update only the basic project information.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/delete",  method=RequestMethod.POST)
    public String updateProject(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);

		projectService.removeProject(id);
		
		return "myGantt";
	}
}
