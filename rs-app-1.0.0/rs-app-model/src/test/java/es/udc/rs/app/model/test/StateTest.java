package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class StateTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Test
	public void fullStateTest() throws InstanceNotFoundException {
		
		log.info("");
		log.info ("=========== Starting State Test ============");
		findState();
	}
	
	private void findState() throws InstanceNotFoundException {
		
		State st1 = new State("CANC", "Cancelado", "Proceso que sin cumplirse los objetivos iniciales termina.");
		State st2 = new State("EJEC", "Ejecucion", "Proceso en marcha.");
		State st3 = new State("PLAN", "Planificacion", "Fase inicial y previa a la ejecucion.");
		
		// ================================== Find by id ==================================
		State thisState = projectService.findState(st1.getId());
		assertEquals(st1, thisState);
		
		thisState = projectService.findState(st2.getId());
		assertEquals(st2, thisState);
		
		thisState = projectService.findState(st3.getId());
		assertEquals(st3, thisState);
		
		// =================================== Find all ===================================
		List<State> states = projectService.findAllState();
		
		assertEquals(5, states.size());
		assertEquals(st1, states.get(0));
		assertEquals(st2, states.get(1));
		assertEquals(st3, states.get(2));
	}

}
