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

import es.udc.rs.app.client.conversor.HistoryProjectDTOConversor;
import es.udc.rs.app.client.conversor.ProjectDTOConversor;
import es.udc.rs.app.client.conversor.ProjectMgmtDTOConversor;
import es.udc.rs.app.client.conversor.ProvinceDTOConversor;
import es.udc.rs.app.client.dto.HistoryProjectDTO;
import es.udc.rs.app.client.dto.ProjectDTO;
import es.udc.rs.app.client.dto.ProjectMgmtDTO;
import es.udc.rs.app.client.dto.ProvinceDTO;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectMgmt;
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
	// [GET]-> /projects/id || Getting of the principal project information.   
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
	// [POST]-> /projects/id/update || Update only the basic project information.   
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
	// [POST]-> /projects/id/delete || Delete the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/delete",  method=RequestMethod.POST)
    public String deleteProject(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);

    	// Now delete the project
		projectService.removeProject(id);
		
		return "myGantt";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/states || Show info about project states and it allows several operations.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/states",  method=RequestMethod.GET)
    public String showHistoryProject(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);

    	// Get the current state
    	HistoryProject hp = projectService.findCurrentHistoryProject(project);
    	HistoryProjectDTO currentState = HistoryProjectDTOConversor.toHistoryProjectDTO(hp);
    	String stateDesc = hp.getState().getDescription();
    	
    	// Now find the project history states and convert it to DTO
    	List<HistoryProject> hps = projectService.findHistoryProjectByProject(project);
    	List<HistoryProjectDTO> hpsDTO = HistoryProjectDTOConversor.toHistoryProjectDTOList(hps);
    	
    	// Create the model
    	model.addAttribute("idProject", id);
    	model.addAttribute("currentState", currentState);
    	model.addAttribute("stateDescription", stateDesc);
    	model.addAttribute("historyProject", hpsDTO);
		
		return "project/states";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/states/id/update || Update a history project   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/states/{idState}/update",  method=RequestMethod.POST)
    public String updateHistoryProject(@Valid @ModelAttribute("historyProject") HistoryProjectDTO historyProjectDTO, 
    		BindingResult result, @PathVariable String idProject, @PathVariable String idState, Model model) throws InstanceNotFoundException, InputValidationException {
    	
    	// Convert the object DTO to HistoryProject
    	HistoryProject hp = HistoryProjectDTOConversor.toHistoryProject(historyProjectDTO);
    	
    	// Update the data
    	projectService.updateHistoryProject(hp);
		
		return showHistoryProject(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/statics || Get all the statics of the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/statics",  method=RequestMethod.GET)
    public String showProjectStatics(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Convert the string id to long
    	Long id = Long.parseLong(idProject, 10);

    	// Find this Project and convert it to DTO
    	Project project = projectService.findProject(id);
    	ProjectDTO projectDTO = ProjectDTOConversor.toProjectDTO(project);
    	
    	// Create the model
    	model.addAttribute("project", projectDTO);
		
		return "project/statics";
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/managers || Get the project managers.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/managers",  method=RequestMethod.GET)
    public String showProjectManagers(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// // Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
		// Find the managers of this Project
		List<ProjectMgmt> managers = projectService.findProjectMgmtByProject(project);
		
		// Convert the object to DTO
		List<ProjectMgmtDTO> managersDTO = ProjectMgmtDTOConversor.toProjectMgmtDTOList(managers);
    	
    	// Create the model
    	model.addAttribute("managers", managersDTO);
		
		return "project/managers";
	}
}
