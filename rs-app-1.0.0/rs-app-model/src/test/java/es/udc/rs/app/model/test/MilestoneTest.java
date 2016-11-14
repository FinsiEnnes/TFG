package es.udc.rs.app.model.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class MilestoneTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullPhaseTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		log.info("");
		log.info ("============ Starting Milestone Test ============");
		allOperationsMilestone();	
	}

	private void allOperationsMilestone() throws InputValidationException, InstanceNotFoundException, ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		boolean error;
		
		// ================================================================================
		log.info("");
		log.info ("===> Initialize some datas");
		// ================================================================================
		Province province = customerService.findProvince(1L);
		
		Project project = new Project("Proyecto Zeta", new Date(), false, province, fmt.parse("2016-01-10"));
		Phase phase = new Phase(project,"Fase 1");

		projectService.createProject(project);		
		projectService.createPhase(phase);
		
		// ================================================================================
		log.info("");
		log.info ("===> Incorrect create");
		// ================================================================================
		Milestone milestone = new Milestone(phase, "milestone1", fmt.parse("2015-01-10"), null, null);
		incorrectCreate(milestone);
		
		// ================================================================================
		log.info("");
		log.info ("===> Correct create");
		// ================================================================================
		milestone = new Milestone(phase, "milestone1", fmt.parse("2016-01-25"), null, null);
		projectService.createMilestone(milestone);
		
		// ================================================================================
		log.info("");
		log.info ("===> Find Milestone");
		// ================================================================================
		Milestone thisMilestone = projectService.findMilestone(milestone.getId());
		assertEquals(thisMilestone, milestone);
		
		// ================================================================================
		log.info("");
		log.info ("===> Update Milestone");
		// ================================================================================
		milestone.setName("milestone modificado");
		milestone.setDatePlan(fmt.parse("2016-02-10"));
		
		projectService.updateMilestone(milestone);
		
		thisMilestone = projectService.findMilestone(milestone.getId());
		assertEquals(thisMilestone, milestone);
		
		// ================================================================================
		log.info("");
		log.info ("===> Remove Milestone");
		// ================================================================================
		projectService.removeMilestone(milestone.getId());
		
		try {
			error=false;
			projectService.findMilestone(milestone.getId());
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
		
		projectService.removeProject(project.getId());
	}
	
	
	private void incorrectCreate(Milestone milestone) throws InstanceNotFoundException {
		boolean error;
		
		try {
			error=false;
			projectService.createMilestone(milestone);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}

}
