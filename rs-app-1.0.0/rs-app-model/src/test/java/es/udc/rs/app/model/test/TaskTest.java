package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TaskTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private PersonService personService;
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("============== Starting Task Test ===============");
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
		
		crudTask();	
		log.info("");
		log.info("===================================================================");
	}

	private void crudTask() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		Task task;
		Task otherTask;
		boolean error;
		Integer num;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Get some necessary data");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		State ejec = projectService.findState("EJEC");
		Priority m = projectService.findPriority("M");
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect create Task");
		// ================================================================================
		// The project still has not State 
		task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 10);
		incorrectCreate(task);
		
		// Now we the project has Plan State
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, 
												   fmt.parse("2016-01-07"), fmt.parse("2016-01-19"), null);
		projectService.createHistoryProject(planHP);
		
		// Insert a Task with incorrect State
		task = new Task(testUtils.phase1, "Task 1", ejec, m, testUtils.hp1, 10);
		incorrectCreate(task);
		
		// Insert a Task with data that it is forbidden
		task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 10);
		task.setHoursPlan(25);
		incorrectCreate(task);
		
		// Insert a Task with a negative number of days
		task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, -10);
		
		try {
			error=false;
			projectService.createTask(task);
		} catch (InputValidationException e){
			log.info("Error: Input invalidation because daysPlanTask is negative");
			error=true;
		}
		assertTrue(error);
		
		// Insert a Task with a initial date lower than the project date
		task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 10);
		task.setIniPlan(fmt.parse("2016-01-01"));
		incorrectCreate(task);

		
		// ================================================================================
		log.info("");
		log.info("===> Correct create Task");
		// ================================================================================
		task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		task.setIniPlan(fmt.parse("2016-01-22"));
		projectService.createTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find the created Task");
		// ================================================================================	
		otherTask = projectService.findTask(task.getId());
		assertEquals(task, otherTask);
		
		
		// ================================================================================
		log.info("");
		log.info("===> When the task State is PLAN, it is only possible update daysPlan and iniPlan");
		// ================================================================================	
		task.setIniPlan(fmt.parse("2016-01-20"));
		projectService.updateTask(task);
		otherTask = projectService.findTask(task.getId());
		assertEquals(task.getIniPlan(), otherTask.getIniPlan());
		
		task.setDaysPlan(8);
		projectService.updateTask(task);	
		otherTask = projectService.findTask(task.getId());
		assertEquals(task.getDaysPlan(), otherTask.getDaysPlan());
		
		
		// ================================================================================
		log.info("");
		log.info("===> We cannot update other attributes when the task State is PLAN");
		// ================================================================================	
		task.setEndPlan(fmt.parse("2016-01-29"));
		incorrectUpdate(task);
		task.setEndPlan(null);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Error changing the Task State");
		// ================================================================================	
		task.setState(prpd);
		incorrectUpdate(task);
		task.setState(plan);

		// ================================================================================
		log.info("");
		log.info("===> Correct create of Assignment Profiles");
		// ================================================================================
		AssignmentProfile ap1 = new AssignmentProfile(task, testUtils.pc1, 1, 12, 0);
		assignmentService.createAssignmentProfile(ap1);
		
		AssignmentProfile ap2 = new AssignmentProfile(task, testUtils.pc2, 1, 5, 0);
		assignmentService.createAssignmentProfile(ap2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Task State: PLAN -> PRPR");
		// ================================================================================	
		// We need to include profiles at the task
		task.setState(prpd);
		projectService.updateTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Check the endPlan, hours and cost");
		// ================================================================================	
		otherTask = projectService.findTask(task.getId());
		
		assertEquals(fmt.parse("2016-01-28"), otherTask.getEndPlan());
		num = 17;
		assertEquals(num, otherTask.getHoursPlan());
		num = 100;
		assertEquals(num, otherTask.getCostPlan());
		
		task = projectService.findTask(task.getId());
		
		// ================================================================================
		log.info("");
		log.info("===> When the Task State is PRPD, it is only possible update iniRealTask");
		// ================================================================================
		task.setEndPlan(fmt.parse("2016-01-29"));
		incorrectUpdate(task);
		task.setEndPlan(fmt.parse("2016-01-28")); // correct date
		
		task.setCostPlan(20);
		incorrectUpdate(task);
		task.setCostPlan(100);
		
		task.setHoursReal(50);
		incorrectUpdate(task);
		task.setHoursReal(null);
		
		
		// ================================================================================
		log.info("");
		log.info("===> We cannot change to EJEC if the Project State is not EJEC");
		// ================================================================================
		task.setState(ejec);
		incorrectUpdate(task);
		task.setState(prpd);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Project State: PLAN -> EJEC");
		// ================================================================================
		HistoryProject ejecHP = new HistoryProject(testUtils.project, ejec, 
				   fmt.parse("2016-01-20"), fmt.parse("2016-10-19"), null);
		projectService.createHistoryProject(ejecHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> If now we try to change the Task State to EJEC");
		// ================================================================================
		task.setState(ejec);
		incorrectUpdate(task);
		task.setState(prpd);
		
		// ================================================================================
		log.info("");
		log.info("===> We must define iniRealTask to do the change");
		// ================================================================================
		task.setIniReal(fmt.parse("2016-01-25"));
		projectService.updateTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Now we can change the Task State");
		// ================================================================================
		task.setState(ejec);
		projectService.updateTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> When the Task State is EJEC, we cannot update any attribute");
		// ================================================================================
		task.setIniPlan(fmt.parse("2016-01-01"));
		incorrectUpdate(task);
		task.setIniPlan(fmt.parse("2016-01-22"));
		
		task.setEndPlan(fmt.parse("2016-01-01"));
		incorrectUpdate(task);
		task.setEndPlan(null);
		
		task.setCostPlan(20);
		incorrectUpdate(task);
		task.setCostPlan(100);
		
		task.setHoursReal(50);
		incorrectUpdate(task);
		task.setHoursReal(null);
		
		task.setEndReal(fmt.parse("2016-01-29"));
		incorrectUpdate(task);
		task.setEndReal(null);
	}
	
	private void incorrectCreate(Task task) throws InputValidationException, InstanceNotFoundException {
		
		boolean error;
		
		try {
			error=false;
			projectService.createTask(task);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void incorrectUpdate(Task task) throws InputValidationException, InstanceNotFoundException {
		
		boolean error;
		
		try {
			error=false;
			projectService.updateTask(task);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
	}

}
