package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
import es.udc.rs.app.model.domain.AssignmentPerson;
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
public class AssignmentPersonTest {

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
		log.info ("======== Starting AssignmentPerson Test =========");
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
	public void fullAssignmentmaterialTest() 
			throws InstanceNotFoundException, InputValidationException, ParseException {

		crudAssignmentPerson();
		log.info("");
	}

	private void crudAssignmentPerson() 
			throws InstanceNotFoundException, ParseException, InputValidationException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Integer num;
		AssignmentPerson thisAssigPerson;
		
		// ================================================================================
		log.info("");
		log.info("===> Previous and necessaries steps");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		State ejec = projectService.findState("EJEC");
		Priority m = projectService.findPriority("M");
		
		// Create the Project State as PLAN and the first Task
		Task task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		task.setIniPlan(fmt.parse("2016-01-22"));
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, 
												   fmt.parse("2016-01-07"), fmt.parse("2016-01-19"), null);
		
		projectService.createHistoryProject(planHP);
		projectService.createTask(task);
		
		// We assign a Profile. It is necessary
		AssignmentProfile assigProf = new AssignmentProfile(task, testUtils.pc1, 1, 10, 0);
		assignmentService.createAssignmentProfile(assigProf);
		
		// Change Task State: PLAN -> PRPR
		task.setState(prpd);
		projectService.updateTask(task);
		
		// Change Project State: PLAN -> EJEC
		HistoryProject ejecHP = new HistoryProject(testUtils.project, ejec, 
				   fmt.parse("2016-01-20"), fmt.parse("2016-10-19"), null);
		projectService.createHistoryProject(ejecHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect creation of AssignmentPerson due to the Task State");
		// ================================================================================
		AssignmentPerson assigPerson1 = new AssignmentPerson(task, testUtils.hp1, false);
		incorrectCreate(assigPerson1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Task State: PRPD -> EJEC");
		// ================================================================================
		task.setIniReal(fmt.parse("2016-01-25"));
		projectService.updateTask(task);
		
		task.setState(ejec);
		projectService.updateTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Anothers incorrect creations");
		// ================================================================================
		incorrectCreate(assigPerson1); // hp1 has endDate not null
		
		assigPerson1 = new AssignmentPerson(task, testUtils.hp2, true); // conclude is true
		incorrectCreate(assigPerson1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of AssignmentPerson");
		// ================================================================================
		AssignmentPerson assigPerson2 = new AssignmentPerson(task, testUtils.hp2, false);
		assignmentService.createAssignmentPerson(assigPerson2);
		
		AssignmentPerson assigPerson3 = new AssignmentPerson(task, testUtils.hp3, false);
		assignmentService.createAssignmentPerson(assigPerson3);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentPerson by id");
		// ================================================================================
		thisAssigPerson = assignmentService.findAssignmentPerson(assigPerson2.getId());
		assertEquals(assigPerson2, thisAssigPerson);
		
		thisAssigPerson = assignmentService.findAssignmentPerson(assigPerson3.getId());
		assertEquals(assigPerson3, thisAssigPerson);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentPerson by Task");
		// ================================================================================
		List<AssignmentPerson> assigPersons = assignmentService.findAssignmentPersonByTask(task);
		
		assertEquals(2, assigPersons.size());
		assertEquals(assigPerson2, assigPersons.get(0));
		assertEquals(assigPerson3, assigPersons.get(1));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentPerson by Person");
		// ================================================================================
		thisAssigPerson = assignmentService.findAssignmentPersonByPerson(testUtils.hp2).get(0);
		assertEquals(assigPerson2, thisAssigPerson);
		
		thisAssigPerson = assignmentService.findAssignmentPersonByPerson(testUtils.hp3).get(0);
		assertEquals(assigPerson3, thisAssigPerson);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect update");
		// ================================================================================
		assigPerson2.setTotalHours(20);
		assigPerson2.setTotalCost(200);
		
		incorrectUpdate(assigPerson2);
		
		assigPerson2.setTotalHours(null);
		assigPerson2.setTotalCost(null);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct update");
		// ================================================================================
		assigPerson2.setConclude(true);
		assignmentService.updateAssignmentPerson(assigPerson2);
		
		num = 0;
		thisAssigPerson = assignmentService.findAssignmentPerson(assigPerson2.getId());
		assertEquals(num, thisAssigPerson.getTotalHours());
		assertEquals(num, thisAssigPerson.getTotalExtraHours());
		assertEquals(num, thisAssigPerson.getTotalCost());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct delete");
		// ================================================================================
		assignmentService.removeAssignmentPerson(assigPerson2.getId());
		assignmentService.removeAssignmentPerson(assigPerson3.getId());
		
		assigPersons = assignmentService.findAssignmentPersonByTask(task);
		assertEquals(0, assigPersons.size());
	}
	
	private void incorrectCreate(AssignmentPerson assigPerson) throws InstanceNotFoundException {
		
		boolean error;
		
		try {
			error=false;
			assignmentService.createAssignmentPerson(assigPerson);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void incorrectUpdate(AssignmentPerson assigPerson) throws InstanceNotFoundException {
		
		boolean error;
		
		try {
			error=false;
			assignmentService.updateAssignmentPerson(assigPerson);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
	}
}
