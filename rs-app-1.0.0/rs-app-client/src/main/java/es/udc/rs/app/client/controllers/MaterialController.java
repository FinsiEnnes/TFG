package es.udc.rs.app.client.controllers;

import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.MaterialDTOConversor;
import es.udc.rs.app.client.dto.MaterialDTO;
import es.udc.rs.app.client.util.JsonConversor;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.material.MaterialService;
import es.udc.rs.app.model.service.project.ProjectService;

@Controller
public class MaterialController {

	@Autowired 
	private MaterialService materialService;
	
	@Autowired 
	private ProjectService projectService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /projects/id/materials || Get all the information about materials   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/materials",  method=RequestMethod.GET)
    public String showMaterials(@PathVariable String idProject, Model model) throws InstanceNotFoundException {
		
		// Find all the materials
		List<Material> materials = materialService.findAllMaterials();
		
		// Convert the previous object to DTO
		List<MaterialDTO> materialsDTO = MaterialDTOConversor.toMaterialDTOList(materials);
		
		// Get the material description information as JSON
		JSONArray materialDesc = JsonConversor.getMaterialDescAsJSON(materials);
		
		// Find the first task of this project
		Long longIdProject =  Long.parseLong(idProject, 10);
		Task task = projectService.findFirstTask(longIdProject);
		
		Long idTask = (task.getId() == null ? 0L : task.getId());
		Long idPhase = (task.getId() == null ? 0L : task.getPhase().getId());
		
		// Create the model
		model.addAttribute("idProject", idProject);
		model.addAttribute("idPhase", idPhase);
		model.addAttribute("idTask", idTask);
		model.addAttribute("materials", materialsDTO);
		model.addAttribute("matDescriptions", materialDesc);
		
		return "material/materials";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/materials || Add a new material   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/materials",  method=RequestMethod.POST)
	public String addMaterial(@Valid @ModelAttribute("material") MaterialDTO materialDTO, 
							  BindingResult result, @PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the DTO to object
		Material material = MaterialDTOConversor.toMaterial(materialDTO);
		
		// Create the material
		materialService.createMaterial(material);
		
		// Return the main interface
		return showMaterials(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/materials/id/update || Update a material   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/materials/{idMaterial}/update",  method=RequestMethod.POST)
	public String updateMaterial(@Valid @ModelAttribute("material") MaterialDTO materialDTO, 
			BindingResult result, @PathVariable String idProject, @PathVariable String idMaterial, Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the DTO to object
		Material material = MaterialDTOConversor.toMaterial(materialDTO);
		
		// Create the material
		materialService.updateMaterial(material);
		
		// Return the main interface
		return showMaterials(idProject, model);
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /projects/id/materials/id/delete || Delete a material   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/projects/{idProject}/materials/{idMaterial}/delete",  method=RequestMethod.POST)
	public String deleteMaterial(@PathVariable String idMaterial, @PathVariable String idProject, Model model) throws InstanceNotFoundException, InputValidationException {
		
		// Convert the string id to long
		Long id =  Long.parseLong(idMaterial, 10);
				
		// Delete the material
		materialService.removeMaterial(id);
		
		// Return the main interface
		return showMaterials(idProject, model);
	}

}
