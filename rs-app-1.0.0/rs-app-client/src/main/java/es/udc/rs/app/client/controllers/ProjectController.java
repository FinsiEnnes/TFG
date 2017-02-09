package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
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

import es.udc.rs.app.client.conversor.CustomerDTOConversor;
import es.udc.rs.app.client.conversor.FreeDayDTOConversor;
import es.udc.rs.app.client.conversor.HistoryPersonDTOConversor;
import es.udc.rs.app.client.conversor.HistoryProjectDTOConversor;
import es.udc.rs.app.client.conversor.ProjectDTOConversor;
import es.udc.rs.app.client.conversor.ProjectMgmtDTOConversor;
import es.udc.rs.app.client.conversor.ProvinceDTOConversor;
import es.udc.rs.app.client.dto.CustomerDTO;
import es.udc.rs.app.client.dto.FreeDayDTO;
import es.udc.rs.app.client.dto.HistoryPersonDTO;
import es.udc.rs.app.client.dto.HistoryProjectDTO;
import es.udc.rs.app.client.dto.ProjectDTO;
import es.udc.rs.app.client.dto.ProjectMgmtDTO;
import es.udc.rs.app.client.dto.ProvinceDTO;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Customer;
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectFreeDay;
import es.udc.rs.app.model.domain.ProjectMgmt;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class ProjectController {

	@Autowired 
	private ProjectService projectService;
	
	@Autowired 
	private PersonService personService;
	
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
    	
		// Find the first task of this project
		Task task = projectService.findFirstTask(id);
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
		
    	// Create the model
    	model.addAttribute("project", projectDTO);
    	model.addAttribute("idProject", idProject);
    	model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
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
    	
		// Find the first task of this project
		Task task = projectService.findFirstTask(id);
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
    	
    	// Create the model
    	model.addAttribute("idProject", id);
    	model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
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
    	
		// Find the first task of this project
		Task task = projectService.findFirstTask(id);
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
		
    	// Create the model
		model.addAttribute("project", projectDTO);
		model.addAttribute("idProject", id);
    	model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);   	
		
		return "project/statics";
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/managers || Get the project managers.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/managers",  method=RequestMethod.GET)
    public String showProjectManagers(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
		// Find the managers of this Project and the current workers
		List<ProjectMgmt> managers = projectService.findProjectMgmtByProject(project);
		List<HistoryPerson> persons = personService.findCurrentHistoryPersons();
		
		// Convert the objects to DTO
		List<ProjectMgmtDTO> managersDTO = ProjectMgmtDTOConversor.toProjectMgmtDTOList(managers);
		List<HistoryPersonDTO> personsDTO = HistoryPersonDTOConversor.toHistoryPersonDTOs(persons);
		
		// Find the first task of this project
		Task task = projectService.findFirstTask(id);
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
		
    	// Create the model
		model.addAttribute("idProject", id);
    	model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
    	model.addAttribute("managers", managersDTO);
    	model.addAttribute("persons", personsDTO);
		
		return "project/managers";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/managers || Add a new manager to the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/managers",  method=RequestMethod.POST)
    public String addProjectManagers(@Valid @ModelAttribute("projectMgmt") ProjectMgmtDTO projectMgmtDTO, 
    		BindingResult result,@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the DTO to object
		ProjectMgmt projectMgmt = ProjectMgmtDTOConversor.toProjectMgmt(projectMgmtDTO);
		
		// Create the new register of this manager
		projectService.createProjectMgmt(projectMgmt);
		
		// Show the managers project again
		return showProjectManagers(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/managers/id/update || Update a manager to the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/managers/{idManager}/update",  method=RequestMethod.POST)
    public String updateProjectManagers(@Valid @ModelAttribute("projectMgmt") ProjectMgmtDTO projectMgmtDTO, 
    		BindingResult result,@PathVariable String idProject,  @PathVariable String idManager, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
    	// Convert the DTO to object
		ProjectMgmt projectMgmt = ProjectMgmtDTOConversor.toProjectMgmt(projectMgmtDTO);
		
		// Create the new register of this manager
		projectService.updateProjectMgmt(projectMgmt);
		
		// Show the managers project again
		return showProjectManagers(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/managers/id/delete || Delete a new manager to the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/managers/{idManager}/delete",  method=RequestMethod.POST)
    public String deleteProjectManager(@PathVariable String idProject, @PathVariable String idManager, Model model) 
    		throws InstanceNotFoundException, InputValidationException {
		
    	// String to Long
		Long id = Long.parseLong(idManager, 10);
		
		// Delete this manager
		projectService.removeProjectMgmt(id);
		
		// Show the managers project again
		return showProjectManagers(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/customer || Get the information of the project customer.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/customer",  method=RequestMethod.GET)
    public String showCustomer(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
    	//  Add the id project to the model
    	model.addAttribute("idProject", id);
    	
		// Find the first task of this project
		Task task = projectService.findFirstTask(id);
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
		
    	model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		
    	// If this project doesn't have customer we show a warning interface 
    	if (project.getCustomer() == null) {
    		
    		// Get all the customers 
    		List<Customer> customers = customerService.findAllCustomers();
    		
    		// Convert the customer list to DTO list
    		List<CustomerDTO> customersDTO = CustomerDTOConversor.toCustomerDTOList(customers);
    		
    		// Create the model with this list
    		model.addAttribute("customers", customersDTO);
    		
    		return "project/customerEmpty";
    	}
    	
    	// If the project has customer, then we get the customer data
    	Customer customer = customerService.findCustomer(project.getCustomer().getId());
    	CustomerDTO customerDTO = CustomerDTOConversor.toCustomerDTO(customer);
    	
    	// We also need the contact information that it is included in the project so
    	ProjectDTO projectDTO = ProjectDTOConversor.toProjectDTO(project);
    	
    	// Create the model with this list
		model.addAttribute("customer", customerDTO);
		model.addAttribute("project", projectDTO);
		
		return "project/customer";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/customer || Add a customer to the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/customer",  method=RequestMethod.POST)
    public String addCustomer(@Valid @ModelAttribute("project") ProjectDTO projectDTO, 
    		BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
    	// Get the selected customer
    	Customer customer = customerService.findCustomer(projectDTO.getIdCustomer());
    	
    	// Now we need update the project to add it the customer
    	project.setCustomer(customer);
    	project.setNameContact(projectDTO.getNameContact());
    	project.setSurnameContact(projectDTO.getSurnameContact());
    	project.setNifContact(projectDTO.getNifContact());
    	project.setEmailContact(projectDTO.getEmailContact());
    	
    	// Update the project
    	projectService.updateProject(project);
    	    	
    	// Show the interface		
    	return showCustomer(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/customer/update || Update the contact information if the customer.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/customer/update",  method=RequestMethod.POST)
    public String updateCustomer(@Valid @ModelAttribute("project") ProjectDTO projectDTO, 
    		BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
    	// Update the contact 
    	project.setNameContact(projectDTO.getNameContact());
    	project.setSurnameContact(projectDTO.getSurnameContact());
    	project.setNifContact(projectDTO.getNifContact());
    	project.setEmailContact(projectDTO.getEmailContact());
    	
    	// Update the project
    	projectService.updateProject(project);
    	    	
    	// Show the interface		
		return showCustomer(idProject, model);
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/customer/delete || Delete the customer to the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/customer/delete",  method=RequestMethod.POST)
    public String deleteCustomer(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
    	// Set null all the customer attributes of the project 
    	project.setCustomer(null);
    	project.setNameContact(null);
    	project.setSurnameContact(null);
    	project.setNifContact(null);
    	project.setEmailContact(null);
    	
    	// Update the project
    	projectService.updateProject(project);
    	    	
    	// Show the interface		
		return showCustomer(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/calendar || Show the free days linked with the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/calendar",  method=RequestMethod.GET)
    public String showFreeDays(@PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
    	// Find the FreeDays of this Project
    	List<FreeDay> freeDays = projectService.findProjectFreeDayByProject(project);
    	List<FreeDayDTO> freeDaysDTO = FreeDayDTOConversor.toFreeDayDTOList(freeDays);
    	
    	// Now we separate the previous list in week day or period
    	List<FreeDayDTO> freeWeekDays = new ArrayList<FreeDayDTO>();
    	List<FreeDayDTO> freePeriodDays = new ArrayList<FreeDayDTO>();
    	
    	for (FreeDayDTO f : freeDaysDTO) {
    		if (f.getWeekDay()==null) {
    			freePeriodDays.add(f);
    		} else {
    			freeWeekDays.add(f);
    		}
    	}
    	
    	// Find the FreeDays distinct to the Project
    	List<FreeDay> otherFreeDays = projectService.findFreeDayDistinctThisProject(project);
    	List<FreeDayDTO> otherFreeDaysDTO = FreeDayDTOConversor.toFreeDayDTOList(otherFreeDays);
    	
    	// Repeat the previous process 
    	List<FreeDayDTO> otherFreeWeekDays = new ArrayList<FreeDayDTO>();
    	List<FreeDayDTO> otherFreePeriodDays = new ArrayList<FreeDayDTO>();
    	
    	for (FreeDayDTO f : otherFreeDaysDTO) {
    		if (f.getWeekDay()==null) {
    			otherFreePeriodDays.add(f);
    		} else {
    			otherFreeWeekDays.add(f);
    		}
    	}
		
		// Find the first task of this project
		Task task = projectService.findFirstTask(id);
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
		
    	// Create the model
		model.addAttribute("idProject", id);
    	model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
    	model.addAttribute("freeWeekDays", freeWeekDays);
    	model.addAttribute("freePeriodDays", freePeriodDays);
    	model.addAttribute("otherFreeWeekDays", otherFreeWeekDays);
    	model.addAttribute("otherFreePeriodDays", otherFreePeriodDays);
		
		return "project/calendar";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/calendar || Add a new free day to the project.   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/calendar",  method=RequestMethod.POST)
    public String addFreeDays(@Valid @ModelAttribute("freeDay") FreeDayDTO freeDayDTO, 
    		BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
    	// Find this Project
		Long id = Long.parseLong(idProject, 10);
    	Project project = projectService.findProject(id);
    	
		// Convert the DTO to object
		FreeDay freeDay = FreeDayDTOConversor.toFreeDay(freeDayDTO);
		
		// Create the FreeDay and the ProjectFreeDay
		projectService.createFreeDay(freeDay);
		
		// Now create a ProjectFreeDay
		ProjectFreeDay pfd = new ProjectFreeDay(project, freeDay);
		projectService.createProjectFreeDay(pfd);
		
		// Show the calendar interface
		return showFreeDays(idProject, model);
	}
}
