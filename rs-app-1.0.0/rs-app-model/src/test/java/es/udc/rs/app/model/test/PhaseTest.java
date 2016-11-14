package es.udc.rs.app.model.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class PhaseTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullPhaseTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		log.info("");
		log.info ("============== Starting Phase Test ==============");
		createAndFindPhase();
		updatePhase();
		removePhase();		
	}

	private void createAndFindPhase() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		log.info("");
		log.info ("===> Initialize some datas");
		
		// First we need to create a project
		Province province = customerService.findProvince(1L);
		Project project = new Project("Proyecto Zeta", new Date(), false, province, fmt.parse("2016-01-10"));
		projectService.createProject(project);
		

		// ================================================================================
		log.info("");
		log.info ("===> Create a correct Phases");
		// ================================================================================
		Phase phase1 = new Phase(project,"Fase 1");
		Phase phase2 = new Phase(project,"Fase 2");
		Phase phase3 = new Phase(project,"Fase 3");
		
		projectService.createPhase(phase1);
		projectService.createPhase(phase2);
		projectService.createPhase(phase3);

		// ================================================================================
		log.info("");
		log.info ("===> Find Phases by id");
		// ================================================================================
		Phase phase = projectService.findPhase(phase1.getId());
		assertEquals(phase1, phase);
		
		phase = projectService.findPhase(phase2.getId());
		assertEquals(phase2, phase);
		
		phase = projectService.findPhase(phase3.getId());
		assertEquals(phase3, phase);
		
		// ================================================================================
		log.info("");
		log.info ("===> Find project Phases");
		// ================================================================================
		List<Phase> phases = projectService.findPhaseByProject(project.getId());
		assertEquals(3, phases.size());
		assertEquals(phase1, phases.get(0));
		assertEquals(phase2, phases.get(1));
		assertEquals(phase3, phases.get(2));
	}
	
	private void updatePhase() throws InstanceNotFoundException, ParseException {
				
		// ================================================================================
		log.info("");
		log.info ("===> Correct update Phase");
		// ================================================================================	
		Project project = projectService.findProjectByName("Proyecto Zeta");
		Phase phase = projectService.findPhaseByProject(project.getId()).get(0);
		
		phase.setName("Fase 1 modificada");
		
		projectService.updatePhase(phase);
		
		Phase thisPhase = projectService.findPhase(phase.getId());
		assertEquals(thisPhase, phase);
		
	}
	
	private void removePhase() throws InstanceNotFoundException {
		
		// ================================================================================
		log.info("");
		log.info ("===> Delete all Phases");
		// ================================================================================	
		Project project = projectService.findProjectByName("Proyecto Zeta");
		List<Phase> phases = projectService.findPhaseByProject(project.getId());
		
		for (Phase p : phases) {
			projectService.removePhase(p.getId());
		}
		
		phases = projectService.findPhaseByProject(project.getId());
		assertEquals(0, phases.size());	
		
		projectService.removeProject(project.getId());
	}

}
