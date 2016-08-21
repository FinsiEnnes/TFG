package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.ProjectFreeDay;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.domain.Workload;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class WorkloadTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private PersonService personService;
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("============ Starting Workload Test =============");
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
	public void fullWorkloadTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		operationWithWorkload();	
		log.info("");
		log.info("===================================================================");
	}

	private void operationWithWorkload() throws InstanceNotFoundException, ParseException, InputValidationException {
				
		Task task1, task2;
		Workload wl1, wl2, wl3, wl4, wl5, wl6;
		
		boolean error;
		Integer num;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Get and create some necessary data");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		State ejec = projectService.findState("EJEC");
		Priority m = projectService.findPriority("M");
		
		TimeOff timeOff = new TimeOff(testUtils.p2, fmt.parse("2016-01-25"), fmt.parse("2016-01-30"), "fiebre");
		personService.createTimeOff(timeOff);
		
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Creation of the FreeDays");
		// ================================================================================
		FreeDay fd1 = new FreeDay("domingo", 6, null, null);
		projectService.createFreeDay(fd1);
		
		ProjectFreeDay pfd1 = new ProjectFreeDay(testUtils.project, fd1);
		projectService.createProjectFreeDay(pfd1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of two Tasks");
		// ================================================================================
		Date dateHP1 = fmt.parse("2016-01-07");
		Date dateHP2 = fmt.parse("2016-01-19");
		
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, dateHP1, dateHP2, null);
		projectService.createHistoryProject(planHP);
		
		task1 = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		task1.setIniPlan(fmt.parse("2016-01-22"));
		
		task2 = new Task(testUtils.phase1, "Task 2", plan, m, testUtils.hp1, 10);
		task2.setIniPlan(fmt.parse("2016-01-25"));
		
		projectService.createTask(task1);
		projectService.createTask(task2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Assignment of trivial Profiles at the Task and change it state to PRPD");
		// ================================================================================
		AssignmentProfile ap1 = new AssignmentProfile(task1, testUtils.pc1, 1, 12, 0);
		assignmentService.createAssignmentProfile(ap1);
		
		AssignmentProfile ap2 = new AssignmentProfile(task2, testUtils.pc2, 1, 5, 0);
		assignmentService.createAssignmentProfile(ap2);
		
		task1.setState(prpd);
		projectService.updateTask(task1);
		
		task2.setState(prpd);
		projectService.updateTask(task2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Project State: PLAN -> EJEC");
		// ================================================================================
		HistoryProject ejecHP = new HistoryProject(testUtils.project, ejec, 
				   fmt.parse("2016-01-20"), fmt.parse("2016-10-19"), null);
		projectService.createHistoryProject(ejecHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Definition of iniRealTask and change task state to EJEC");
		// ================================================================================
		task1.setIniReal(fmt.parse("2016-01-23"));
		projectService.updateTask(task1);
		
		task2.setIniReal(fmt.parse("2016-01-25"));
		projectService.updateTask(task2);
		
		task1.setState(ejec);
		projectService.updateTask(task1);
		
		task2.setState(ejec);
		projectService.updateTask(task2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Assignment of HistoryPerson to the Tasks");
		// ================================================================================
		AssignmentPerson assigPerson1 = new AssignmentPerson(task1, testUtils.hp2, false);
		assignmentService.createAssignmentPerson(assigPerson1);
		
		AssignmentPerson assigPerson2 = new AssignmentPerson(task1, testUtils.hp3, false);
		assignmentService.createAssignmentPerson(assigPerson2);
		
		AssignmentPerson assigPerson3 = new AssignmentPerson(task2, testUtils.hp2, false);
		assignmentService.createAssignmentPerson(assigPerson3);
		
		AssignmentPerson assigPerson4 = new AssignmentPerson(task2, testUtils.hp3, false);
		assignmentService.createAssignmentPerson(assigPerson4);
		
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Now start the workload test");
		log.info("");
		log.info("===> Incorrect insert of Workloads");
		// ================================================================================
		
		// This HistoryPerson is not assigned at the Task
		wl1 = new Workload(task1, testUtils.hp1, fmt.parse("2016-01-23"), 2, 0);
		incorrectCreate(wl1);
		
		// The day assigned is not working day
		wl1 = new Workload(task1, testUtils.hp2, fmt.parse("2016-01-24"), 2, 0);
		incorrectCreate(wl1);
		
		// The day is lower than the initial date task
		wl1 = new Workload(task1, testUtils.hp2, fmt.parse("2016-01-01"), 2, 0);
		incorrectCreate(wl1);
		
		// The day is within a timeOff of the Person
		wl1 = new Workload(task1, testUtils.hp2, fmt.parse("2016-01-26"), 2, 0);
		incorrectCreate(wl1);
		
		// The hours is negative 
		wl1 = new Workload(task1, testUtils.hp2, fmt.parse("2016-01-24"), -2, 0);
		try {
			error=false;
			assignmentService.createWorkload(wl1);
		} catch (InputValidationException e){
			log.info("Error: The hoursWorkload is negative");
			error=true;
		}
		assertTrue(error);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct insert of workload");
		// ================================================================================	
		wl1 = new Workload(task1, testUtils.hp2, fmt.parse("2016-02-01"), 2, 1);
		wl2 = new Workload(task2, testUtils.hp2, fmt.parse("2016-02-02"), 4, 2);
		wl3 = new Workload(task1, testUtils.hp3, fmt.parse("2016-02-01"), 3, 1);
		wl4 = new Workload(task2, testUtils.hp3, fmt.parse("2016-02-02"), 2, 2);
		
		assignmentService.createWorkload(wl1);
		assignmentService.createWorkload(wl2);
		assignmentService.createWorkload(wl3);
		assignmentService.createWorkload(wl4);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect insert of workload because the max of hours is exceed");
		// ================================================================================	
		wl5 = new Workload(task1, testUtils.hp2, fmt.parse("2016-02-01"), 7, 1);
		wl6 = new Workload(task2, testUtils.hp3, fmt.parse("2016-02-02"), 4, 8);
		
		incorrectCreate(wl5);
		incorrectCreate(wl6);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find Workload by id");
		// ================================================================================	
		Workload thisWL = assignmentService.findWorkload(wl1.getId());
		assertEquals(wl1, thisWL);
		
		thisWL = assignmentService.findWorkload(wl2.getId());
		assertEquals(wl2, thisWL);
		
		thisWL = assignmentService.findWorkload(wl3.getId());
		assertEquals(wl3, thisWL);
		
		thisWL = assignmentService.findWorkload(wl4.getId());
		assertEquals(wl4, thisWL);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find Workload by Task");
		// ================================================================================	
		List<Workload> workloads = assignmentService.findWorkloadByTask(task1);
		
		assertEquals(2, workloads.size());
		assertEquals(wl1, workloads.get(0));
		assertEquals(wl3, workloads.get(1));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find Workload by HistoryPerson");
		// ================================================================================	
		workloads = assignmentService.findWorkloadByHistoryPerson(testUtils.hp2);
		
		assertEquals(2, workloads.size());
		assertEquals(wl1, workloads.get(0));
		assertEquals(wl2, workloads.get(1));
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect update of Workload");
		// ================================================================================	
		wl2.setTask(task1);
		incorrectUpdate(wl2);
		wl2.setTask(task2);
		
		wl2.setHours(9);
		incorrectUpdate(wl2);
		wl2.setHours(4);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct update of Workload");
		// ================================================================================	
		wl2.setHours(3);
		assignmentService.updateWorkload(wl2);
		
		thisWL = assignmentService.findWorkload(wl2.getId());
		num = 3;
		assertEquals(num, thisWL.getHours());
		
		// ================================================================================
		log.info("");
		log.info("===> Remove the created Data");
		// ================================================================================	
		assignmentService.removeWorkload(wl1.getId());
		assignmentService.removeWorkload(wl2.getId());
		assignmentService.removeWorkload(wl3.getId());
		assignmentService.removeWorkload(wl4.getId());
		
		workloads = assignmentService.findWorkloadByHistoryPerson(testUtils.hp2);
		assertEquals(0, workloads.size());
		
		workloads = assignmentService.findWorkloadByHistoryPerson(testUtils.hp3);
		assertEquals(0, workloads.size());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Remove other Data");
		// ================================================================================	
		projectService.removeProjectFreeDay(pfd1.getId());
		projectService.removeFreeDay(fd1.getId());
		
		

	}
	
	private void incorrectCreate(Workload workload) throws InstanceNotFoundException, InputValidationException  {
		
		boolean error;
		
		try {
			error=false;
			assignmentService.createWorkload(workload);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void incorrectUpdate(Workload workload) throws InputValidationException, InstanceNotFoundException {
		
		boolean error;
		
		try {
			error=false;
			assignmentService.updateWorkload(workload);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
	}

}
