package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.MaterialDTO;
import es.udc.rs.app.model.domain.Material;

public class MaterialDTOConversor {

	public static List<MaterialDTO> toMaterialDTOList(List<Material> materials) {
		
		List<MaterialDTO> materialsDTO = new ArrayList<MaterialDTO>();
		
		for (Material m : materials) {
			materialsDTO.add(toMaterialDTO(m));
		}
		
		return materialsDTO;
	}
	
	public static MaterialDTO toMaterialDTO(Material material) {
		
		MaterialDTO materialDTO = new MaterialDTO();
		
		// Set the type of the material
		String type = (material.isInner() ? "Propio" : "Comprado");
		
		materialDTO.setId(material.getId());
		materialDTO.setName(material.getName());
		materialDTO.setDescription(material.getDescription());
		materialDTO.setType(type);
		materialDTO.setCost(material.getCost());
		
		return materialDTO;		
	}
	
	public static Material toMaterial(MaterialDTO materialDTO) {
		
		Material material = new Material();
		
		// Set the type of the material
		boolean inner = (materialDTO.getType().equals("Propio"));
		
		material.setId(materialDTO.getId());
		material.setName(materialDTO.getName());
		material.setDescription(materialDTO.getDescription().trim());
		material.setInner(inner);
		material.setCost(materialDTO.getCost());
		
		return material;		
	}
}
