package es.udc.rs.app.model.service.material;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.material.MaterialDAO;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.util.ModelConstants;
import es.udc.rs.app.validation.PropertyValidator;

@Service
public class MaterialServiceImpl implements MaterialService {

	static Logger log = Logger.getLogger("project");
	
	// ============================================================================
	// ============================== DAO Injection ===============================
	// ============================================================================
	@Autowired
	private MaterialDAO materialDAO;
	
	
	private void validateMaterial(Material material) throws InputValidationException { 
		PropertyValidator.validatePositiveInt("costMaterial", material.getCost());
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Long createMaterial(Material material) throws InputValidationException {
		
		// Create an empty id and validate the data
		Long id = null;
		validateMaterial(material);
		
		// Now, we create the Material
		try{
			id = materialDAO.create(material);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.CREATE + material.toString());
		return id;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public Material findMaterial(Long id) throws InstanceNotFoundException {
		
		// Create an empty Material
		Material material = null;
		
		// Find the Material by id
		try{
			material = materialDAO.find(id);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		if (material == null) {
			throw new InstanceNotFoundException(id, Material.class.getName());
		}
		
		log.info(ModelConstants.FIND_ID + material.toString());
		return material;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Material> findAllMaterials() {
		
		List<Material> materials = new ArrayList<Material>();
		
		try{
			materials = materialDAO.findAll();
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.FIND_ALL + materials.size() + " registred materials");
		return materials;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public List<Material> findMaterialByNameAndInner(String name, Boolean inner) 
			throws InputValidationException {
		
		List<Material> materials = new ArrayList<Material>();
		
		// Check if the material's name was defined.
		PropertyValidator.validateMandatoryString("name", name);
		
		try{
			materials = materialDAO.findByNameAndInner(name, inner);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		// Get the result
		String msg = " registred materials with a name like [" + name + "]";
		if (inner != null) { msg = msg + " and inner[" + inner.toString() + "]"; }
		
		log.info(ModelConstants.FIND_ALL + materials.size() + msg);
		return materials;
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void updateMaterial(Material material) throws InstanceNotFoundException {

		Long id = material.getId();
		
		// Check if the material exits
		if (!materialDAO.MaterialExists(id)) {
			throw new InstanceNotFoundException(id, Material.class.getName());
		}
		
		// Now update
		try{
			materialDAO.update(material);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.UPDATE + material.toString());		
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void removeMaterial(Long id) throws InstanceNotFoundException {
		
		// Get the material by id
		Material material = findMaterial(id);
		
		// Now delete
		try{
			materialDAO.remove(material);
		}
		catch (DataAccessException e){
			throw e;
		}
		
		log.info(ModelConstants.DELETE + material.toString());	
	}

}
