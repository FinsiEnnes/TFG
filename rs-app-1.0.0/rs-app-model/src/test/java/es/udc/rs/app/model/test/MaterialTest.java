package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.service.material.MaterialService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class MaterialTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private MaterialService materialService;
	
	@Test
	public void fullPersonTest() throws InputValidationException, InstanceNotFoundException, ParseException {
		
		log.info("");
		log.info ("========== Starting Material Test ==========");
		createAndFindMaterial();
		updateMaterial();
		removeMaterial();
	}


	private void createAndFindMaterial() throws InputValidationException, InstanceNotFoundException {
		
		Material m = null;
		List<Material> materials = new ArrayList<Material>();
		boolean error;
		
		// ================================ Correct create ================================
		Material m1 = new Material("Pizarra electronica","Lenovo", 1100, false);
		Material m2 = new Material("Base de datos","Oracle", 8000, false);
		Material m3 = new Material("Proyector","Full HD", 0, true);
				
		materialService.createMaterial(m1);
		materialService.createMaterial(m2);
		materialService.createMaterial(m3);
		
		// =============================== Incorrect create ===============================
		m = new Material("Proyector","Full HD", -10, true);
		
		try {
			error=false;
			materialService.createMaterial(m);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);
		
		// ================================== Find by id ==================================
		m = materialService.findMaterial(m1.getId());
		assertEquals(m1, m);
		
		m = materialService.findMaterial(m2.getId());
		assertEquals(m2, m);
		
		m = materialService.findMaterial(m3.getId());
		assertEquals(m3, m);
		
		// ================================= Find by name =================================
		m = materialService.findMaterialByNameAndInner("pizarra", null).get(0);
		assertEquals(m1, m);
		
		m = materialService.findMaterialByNameAndInner("dato", false).get(0);
		assertEquals(m2, m);
		
		materials = materialService.findMaterialByNameAndInner("pizarra", true);
		assertEquals(0, materials.size());
		
		// =================================== Find all ===================================
		materials = materialService.findAllMaterials();
		assertEquals(3, materials.size());
		assertEquals(m2, materials.get(0));
		assertEquals(m1, materials.get(1));
		assertEquals(m3, materials.get(2));
	}
	
	
	private void updateMaterial() throws InputValidationException, InstanceNotFoundException {
		
		boolean error;
		
		// ================================ Correct update ================================
		Material material = materialService.findMaterialByNameAndInner("pizarra", null).get(0);
		material.setInner(true);
		material.setDescription("Epson");
		
		materialService.updateMaterial(material);
		
		Material materialUpdated = materialService.findMaterialByNameAndInner("pizarra", null).get(0);
		assertEquals(material, materialUpdated);
		
		// =============================== Incorrect update ===============================
		material = materialService.findMaterialByNameAndInner("pizarra", null).get(0);
		material.setCost(100);
		
		try {
			error=false;
			materialService.updateMaterial(material);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);	
	}

	
	private void removeMaterial() throws InstanceNotFoundException {
		List<Material> materials = materialService.findAllMaterials();
		
		for (Material m : materials) {
			materialService.removeMaterial(m.getId());
		}
		
		materials = materialService.findAllMaterials();
		assertEquals(0, materials.size());
		
	}


}
