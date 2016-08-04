package es.udc.rs.app.model.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class AssignmentProfileTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("===== Starting AssignmentProfileTest Test =======");
		log.info ("Initializing data for test case: " + this.getClass().getName());
		testUtils.createDataSet();
		log.info ("Initilizated data with success.");
	}
	
	@After
	public void tearDown() throws Exception {
		log.info ("Deleting data for test case: " + this.getClass().getName());
		testUtils.deleteDataSet(); 
		log.info ("The datas have been deleted with success.");
	}
	
	@Test
	public void fullTaskTest() throws InstanceNotFoundException, InputValidationException, ParseException {

		crudAssignmentProfile();
		log.info("");
	}

	private void crudAssignmentProfile() 
			throws InstanceNotFoundException, ParseException, InputValidationException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		boolean error;
		Integer num;
		
		// ================================================================================
		log.info("");
		log.info("===> Initialize necessary datas");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		Priority m = projectService.findPriority("M");
		
		Task task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		task.setIniPlan(fmt.parse("2016-01-22"));
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, 
												   fmt.parse("2016-01-07"), fmt.parse("2016-01-19"), null);
		
		projectService.createHistoryProject(planHP);
		projectService.createTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect create of Assignment Profile");
		// ================================================================================
		AssignmentProfile ap = new AssignmentProfile(task, testUtils.pc1, -1, 10);
		try {
			error=false;
			assignmentService.createAssignmentProfile(ap);
		} catch (InputValidationException e){
			log.info("Error: Input invalidation because unitsAssigProf is negative");
			error=true;
		}
		assertTrue(error);
		
		ap = new AssignmentProfile(task, testUtils.pc1, 1, -10);
		try {
			error=false;
			assignmentService.createAssignmentProfile(ap);
		} catch (InputValidationException e){
			log.info("Error: Input invalidation because hoursPerPersonAssigProf is negative");
			error=true;
		}
		assertTrue(error);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct create of Assignment Profile");
		// ================================================================================
		ap = new AssignmentProfile(task, testUtils.pc1, 1, 10, 0);
		assignmentService.createAssignmentProfile(ap);
		
		// ================================================================================
		log.info("");
		log.info("===> Find the created Assignment Profile by Id");
		// ================================================================================
		AssignmentProfile thisAP =  assignmentService.findAssignmentProfile(ap.getId());
		assertEquals(ap, thisAP);
		num = 50;
		assertEquals(num, thisAP.getCost());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find the created Assignment Profile by the Task");
		// ================================================================================
		thisAP =  assignmentService.findAssignmentProfileByTask(task).get(0);
		assertEquals(ap, thisAP);
		num = 50;
		assertEquals(num, thisAP.getCost());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect update of the Assignment Profile");
		// ================================================================================
		ap.setCost(20);
		try {
			error=false;
			assignmentService.updateAssignmentProfile(ap);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
		ap.setCost(50);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct update of the Assignment Profile");
		// ================================================================================
		ap.setUnits(2);
		assignmentService.updateAssignmentProfile(ap);
		
		thisAP =  assignmentService.findAssignmentProfile(ap.getId());
		assertEquals(ap, thisAP);
		num = 100;
		assertEquals(num, thisAP.getCost());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct delete of the Assignment Profile");
		// ================================================================================
		assignmentService.removeAssignmentProfile(ap.getId());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Task state change to PRPD and we create other Assignment Profile");
		// ================================================================================
		ap = new AssignmentProfile(task, testUtils.pc1, 1, 10, 0);
		assignmentService.createAssignmentProfile(ap);
		task.setState(prpd);
		projectService.updateTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> We cannot create/update Assignment Profile when the task state is different to PLAN");
		// ================================================================================
		ap.setHoursPerPerson(5);
		try {
			error=false;
			assignmentService.updateAssignmentProfile(ap);
		} catch (DataAccessException e){
			error=true;
		}
		
		ap = new AssignmentProfile(task, testUtils.pc3, 2, 10, 0);
		try {
			error=false;
			assignmentService.createAssignmentProfile(ap);
		} catch (GenericJDBCException e){
			error=true;
		}
		
		
	}

}
