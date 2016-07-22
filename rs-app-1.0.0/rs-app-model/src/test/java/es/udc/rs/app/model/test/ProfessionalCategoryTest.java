package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
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
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.service.person.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class ProfessionalCategoryTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private PersonService personService; 
	
	@Test
	public void fullProfessionalCategoryTest () throws ParseException, InputValidationException, InstanceNotFoundException {
		
		log.info("");
		log.info ("==== Starting ProfessionalCategory Test ====");
		findLevelsProfCatg();
		createAndFindProfCatg();
		updateProfCatg();
		removeProfCatg();
	}
	
	private void findLevelsProfCatg() {
		
		LevelProfCatg lpc1 = new LevelProfCatg("ESP","Especialista");
		LevelProfCatg lpc2 = new LevelProfCatg("JUN","Junior");
		LevelProfCatg lpc3 = new LevelProfCatg("PDS","Profesional del sector");
		LevelProfCatg lpc4 = new LevelProfCatg("SJN","Semi Junior");
		LevelProfCatg lpc5 = new LevelProfCatg("SEN","Senior");
		
		List<LevelProfCatg> lpcs = personService.findAllLevelsProfCatg();
		
		// We check the first five aptitudeType of the db.
		assertEquals(lpcs.size(), 5);
		assertEquals(lpcs.get(0), lpc1);
		assertEquals(lpcs.get(1), lpc2);
		assertEquals(lpcs.get(2), lpc3);
		assertEquals(lpcs.get(3), lpc4);
		assertEquals(lpcs.get(4), lpc5);
	
	}
	
	private void createAndFindProfCatg() throws InstanceNotFoundException, InputValidationException {
		
		boolean error;
		
		LevelProfCatg lpc1 = personService.findLevelProfCatg("ESP");
		LevelProfCatg lpc2 = personService.findLevelProfCatg("JUN");
		
		// ================================ Correct create ================================
		ProfessionalCategory pc1 = new ProfessionalCategory("SysAdmin",7,lpc1,10,14);
		ProfessionalCategory pc2 = new ProfessionalCategory("Desarrollador SW",0,lpc2,5,null);
		
		personService.createProfessionalCategory(pc1);
		personService.createProfessionalCategory(pc2);
		
		// ========================== Find of the created objects =========================
		ProfessionalCategory pc3 = personService.findProfessionalCategory(pc1.getId());
		assertEquals(pc1, pc3);
		
		pc3 = personService.findProfessionalCategory(pc2.getId());
		assertEquals(pc2, pc3);
		
		List<ProfessionalCategory> pcs = personService.findAllProfessionalCategories();
		assertEquals(pcs.size(), 2);
		assertEquals(pcs.get(0), pc2);
		assertEquals(pcs.get(1), pc1);
		
		pc3 = personService.findProfessionalCategoryByNameAndLevel("admin", null).get(0);
		assertEquals(pc1, pc3);

		pc3 = personService.findProfessionalCategoryByNameAndLevel(null, lpc2).get(0);
		assertEquals(pc2, pc3);
		
		// ============================== Incorrect creates ===============================
		ProfessionalCategory incorrectPc = new ProfessionalCategory("SysAdmin",7,lpc1,-10,14);
		try {
			error=false;
			personService.createProfessionalCategory(incorrectPc);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);
		
		// ================================ Incorrect finds =================================
		try {
			error=false;
			personService.findProfessionalCategoryByNameAndLevel(null, null);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);	
	}
	
	private void updateProfCatg() throws InstanceNotFoundException, InputValidationException {
	
		boolean error;
		ProfessionalCategory pc = personService.findProfessionalCategoryByNameAndLevel("admin", null).get(0);
		
		// ================================ Correct update ================================
		pc.setMinExp(5);
		pc.setName("Administrador");
		
		personService.updateProfessionalCategory(pc);
		
		// =============================== Incorrect update ===============================
		pc.setSal(9);
		
		// We can not modify the salary of the profile.
		try {
			error=false;
			personService.updateProfessionalCategory(pc);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
		
		// Update a nonexistent profile.
		ProfessionalCategory incorrectPc = pc;
		incorrectPc.setId(0L);
		
		try {
			error=false;
			personService.updateProfessionalCategory(pc);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void removeProfCatg() throws InstanceNotFoundException {
		
		boolean error; 
		
		// Remove all data.
		List<ProfessionalCategory> pcs = personService.findAllProfessionalCategories();
		for (ProfessionalCategory pc : pcs) {
			personService.removeProfessionalCategory(pc.getId());
		}
		
		// Remove a nonexistent ProfessionalCategory.
		try {
			error=false;
			personService.removeProfessionalCategory(0L);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
