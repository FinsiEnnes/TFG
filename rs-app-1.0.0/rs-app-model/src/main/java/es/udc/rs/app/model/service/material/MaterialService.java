package es.udc.rs.app.model.service.material;

import java.util.List;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Material;

public interface MaterialService {

	public Long createMaterial(Material material) throws InputValidationException;
	
	public Material findMaterial(Long id) throws InstanceNotFoundException;
	
	public List<Material> findAllMaterials();
	
	public List<Material> findMaterialByNameAndInner(String name, Boolean inner) throws InputValidationException;
		
	public void updateMaterial(Material material) throws InstanceNotFoundException;
	
	public void removeMaterial(Long id) throws InstanceNotFoundException;
}
