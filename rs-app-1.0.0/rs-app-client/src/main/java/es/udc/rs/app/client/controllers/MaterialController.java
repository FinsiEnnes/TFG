package es.udc.rs.app.client.controllers;

import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.MaterialDTOConversor;
import es.udc.rs.app.client.dto.MaterialDTO;
import es.udc.rs.app.client.util.JsonConversor;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.service.material.MaterialService;

@Controller
public class MaterialController {

	@Autowired 
	private MaterialService materialService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /materials || Get all the information about materials   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/materials",  method=RequestMethod.GET)
    public String showMaterials(Model model) throws InstanceNotFoundException {
		
		// Find all the materials
		List<Material> materials = materialService.findAllMaterials();
		
		// Convert the previous object to DTO
		List<MaterialDTO> materialsDTO = MaterialDTOConversor.toMaterialDTOList(materials);
		
		// Get the material description information as JSON
		JSONArray materialDesc = JsonConversor.getMaterialDescAsJSON(materials);
		
		// Create the model
		model.addAttribute("materials", materialsDTO);
		model.addAttribute("matDescriptions", materialDesc);
		
		return "material/materials";
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /materials || Add a new material   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/materials",  method=RequestMethod.POST)
	public String addMaterial(@Valid @ModelAttribute("material") MaterialDTO materialDTO, 
							  BindingResult result,Model model) throws InstanceNotFoundException, InputValidationException {

		if (result.hasErrors()) {
            return "error";
        }
		
		// Convert the DTO to object
		Material material = MaterialDTOConversor.toMaterial(materialDTO);
		
		// Create the material
		materialService.createMaterial(material);
		
		// Return the main interface
		return showMaterials(model);
	}

}
