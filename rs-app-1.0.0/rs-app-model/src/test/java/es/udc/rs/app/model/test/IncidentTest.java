package es.udc.rs.app.model.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class IncidentTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Test
	public void fullIncidentTest () throws InstanceNotFoundException {
		log.info("");
		log.info ("======= Starting Damage and Incident Test =======");
		findDamage();
		crudIncident();

	}

	private void findDamage() throws InstanceNotFoundException {
		
		// Create the data of this table
		Damage d1 = new Damage("MGRV", "Muy grave");
		Damage d2 = new Damage("GRAV", "Grave");
		Damage d3 = new Damage("IMPT", "Importante");
		Damage d4 = new Damage("MENR", "Menor");
		Damage d5 = new Damage("IRLV", "Irrelevante");
		
		// ================================================================================
		log.info("");
		log.info ("===> Find Damage by id");
		// ================================================================================
		Damage thisDamage = projectService.findDamage(d1.getId());
		assertEquals(thisDamage, d1);
		
		thisDamage = projectService.findDamage(d2.getId());
		assertEquals(thisDamage, d2);
		
		thisDamage = projectService.findDamage(d3.getId());
		assertEquals(thisDamage, d3);
		
		thisDamage = projectService.findDamage(d4.getId());
		assertEquals(thisDamage, d4);
		
		thisDamage = projectService.findDamage(d5.getId());
		assertEquals(thisDamage, d5);
		
		// ================================================================================
		log.info("");
		log.info ("===> Find all the Damage");
		// ================================================================================
		List<Damage> damages = projectService.findAllDamage();
		
		assertEquals(5, damages.size());
		assertEquals(damages.get(0), d2);
		assertEquals(damages.get(1), d3);
		assertEquals(damages.get(2), d5);
		assertEquals(damages.get(3), d4);
		assertEquals(damages.get(4), d1);	
	}
	

	private void crudIncident() throws InstanceNotFoundException {
		// ================================================================================
		log.info("");
		log.info ("===> Create a new Incident");
		// ================================================================================
		Damage damage = projectService.findDamage("MGRV");
		Incident incident = new Incident(damage, "Incident reason", "Incident result");
		
		projectService.createIncident(incident);
		
		// ================================================================================
		log.info("");
		log.info ("===> Find the created incident");
		// ================================================================================		
		Incident thisIncident = projectService.findIncident(incident.getId());
		assertEquals(thisIncident, incident);
		
		// ================================================================================
		log.info("");
		log.info ("===> Update the Incident");
		// ================================================================================		
		incident.setReason("other reason");
		incident.setResult("other result");
		
		projectService.updateIncident(incident);
		
		thisIncident = projectService.findIncident(incident.getId());
		assertEquals(thisIncident, incident);
		
		// ================================================================================
		log.info("");
		log.info ("===> Delete the Incident");
		// ================================================================================		
		projectService.removeIncident(incident.getId());
		
		boolean error;
		try {
			error=false;
			projectService.findIncident(incident.getId());
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
	}
	
}
