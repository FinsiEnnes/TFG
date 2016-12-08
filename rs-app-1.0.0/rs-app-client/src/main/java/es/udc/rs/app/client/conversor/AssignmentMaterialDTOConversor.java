package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.AssignmentMaterialDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.material.MaterialService;
import es.udc.rs.app.model.service.project.ProjectService;

@Component
public class AssignmentMaterialDTOConversor {

	private static ProjectService projectService;
	private static MaterialService materialService;
	
	@Autowired
    public AssignmentMaterialDTOConversor(ProjectService projectService, MaterialService materialService) {
		AssignmentMaterialDTOConversor.projectService = projectService;
		AssignmentMaterialDTOConversor.materialService = materialService;
    }
	
	
	public static List<AssignmentMaterialDTO> toAssignmentMaterialDTOList (List<AssignmentMaterial> ams) {
		
		List<AssignmentMaterialDTO> amsDTO = new ArrayList<AssignmentMaterialDTO>();
		
		for (AssignmentMaterial am : ams) {
			amsDTO.add(toAssignmentMaterialDTO(am));
		}
		
		return amsDTO;	
	}
	
	
	public static AssignmentMaterialDTO toAssignmentMaterialDTO(AssignmentMaterial am) {
		
		AssignmentMaterialDTO amDTO = new AssignmentMaterialDTO();
		
		// Set the type material
		String type = (am.getMaterial().isInner() ? "Propio" : "Comprado");
		
		// Create the object AssignmentMaterialDTO
		amDTO.setId(am.getId());
		amDTO.setIdTask(am.getTask().getId());
		amDTO.setName(am.getMaterial().getName());
		amDTO.setCost(am.getMaterial().getCost());
		amDTO.setType(type);
		amDTO.setIdMaterial(am.getMaterial().getId());
		amDTO.setPlan(am.isPlan());
		amDTO.setReal(am.isReal());
		amDTO.setUnitsPlan(am.getUnitsPlan());
		amDTO.setUnitsReal(am.getUnitsReal());
		amDTO.setCostPlan(am.getCostPlan());
		amDTO.setCostReal(am.getCostReal());
		
		// Return the object
		return amDTO;
	}
	
	
	public static AssignmentMaterial toAAssignmentMaterial(AssignmentMaterialDTO amDTO) throws InstanceNotFoundException {
		
		AssignmentMaterial am = new AssignmentMaterial();
		
		// Get the Task and Material
		Task task = projectService.findTask(amDTO.getIdTask());
		Material material = materialService.findMaterial(amDTO.getIdMaterial());
		
		// Create the object AssignmentMaterial
		am.setId(amDTO.getId());
		am.setTask(task);
		am.setMaterial(material);
		am.setPlan(amDTO.isPlan());
		am.setReal(amDTO.isReal());
		am.setUnitsPlan(amDTO.getUnitsPlan());
		am.setUnitsReal(amDTO.getUnitsReal());
		am.setCostPlan(amDTO.getCostPlan());
		am.setCostReal(amDTO.getCostReal());
		
		return am;
	}
}
