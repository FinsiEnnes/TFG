package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TaskIncidentTest {
	
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
		log.info ("========== Starting TaskIncident Test ===========");
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
	public void fullTaskIncidentTest() 
			throws InstanceNotFoundException, InputValidationException, ParseException {
		
		crudTaskIncident();	
		log.info("");
		log.info("===================================================================");
	}

	private void crudTaskIncident() throws ParseException, InstanceNotFoundException, InputValidationException {
		
		Task t1, t2, t3;
		Incident incident1, incident2;
		TaskIncident ti1, ti2, ti3, thisTaskIncident;
		
		List<TaskIncident> taskIncidents;
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Get some necessary data");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		Priority m = projectService.findPriority("M");
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of the PLAN HistoryProject");
		// ================================================================================
		Date dateHP1 = fmt.parse("2016-01-07");
		Date dateHP2 = fmt.parse("2016-01-19");
		
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, dateHP1, dateHP2, null);
		projectService.createHistoryProject(planHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of 3 Task");
		// ================================================================================
		t1 = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		t1.setIniPlan(fmt.parse("2016-01-22"));
		
		t2 = new Task(testUtils.phase1, "Task 2", plan, m, testUtils.hp2, 10);
		t2.setIniPlan(fmt.parse("2016-01-25"));
		
		t3 = new Task(testUtils.phase1, "Task 3", plan, m, testUtils.hp3, 7);
		t3.setIniPlan(fmt.parse("2016-02-10"));
		
		projectService.createTask(t1);
		projectService.createTask(t2);
		projectService.createTask(t3);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Creation of 2 Incident");
		// ================================================================================
		Damage damage = projectService.findDamage("GRAV");
		
		incident1 = new Incident(damage, "Reason 1", "Result 1");
		incident2 = new Incident(damage, "Reason 2", "Result 2");
		
		projectService.createIncident(incident1);
		projectService.createIncident(incident2);
		
		// ================================================================================
		log.info("");
		log.info ("===> Creation of the TaskIncident");
		// ================================================================================
		ti1 = new TaskIncident(t1, incident1, 5000);
		ti2 = new TaskIncident(t2, incident2, 1500);
		ti3 = new TaskIncident(t3, incident2, 3050);
		
		projectService.createTaskIncident(ti1);
		projectService.createTaskIncident(ti2);
		projectService.createTaskIncident(ti3);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Find TaskIncident by id");
		// ================================================================================
		thisTaskIncident = projectService.findTaskIncident(ti1.getId());
		assertEquals(ti1, thisTaskIncident);
		
		thisTaskIncident = projectService.findTaskIncident(ti2.getId());
		assertEquals(ti2, thisTaskIncident);
		
		thisTaskIncident = projectService.findTaskIncident(ti3.getId());
		assertEquals(ti3, thisTaskIncident);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Find TaskIncident by Task");
		// ================================================================================
		taskIncidents = projectService.findTaskIncidentByTask(t1);
		
		assertEquals(1, taskIncidents.size());
		assertEquals(ti1, taskIncidents.get(0));
		
		taskIncidents = projectService.findTaskIncidentByTask(t2);
		
		assertEquals(1, taskIncidents.size());
		assertEquals(ti2, taskIncidents.get(0));
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Find TaskIncident by Project");
		// ================================================================================
		taskIncidents = projectService.findTaskIncidentByProject(testUtils.project);
		
		assertEquals(3, taskIncidents.size());
		assertEquals(ti1, taskIncidents.get(0));
		assertEquals(ti2, taskIncidents.get(1));
		assertEquals(ti3, taskIncidents.get(2));
			
		
		// ================================================================================
		log.info("");
		log.info ("===> Update of TaskIncident");
		// ================================================================================
		ti1.setLoss(1000);
		projectService.updateTaskIncident(ti1);
		
		thisTaskIncident = projectService.findTaskIncident(ti1.getId());
		assertEquals(ti1, thisTaskIncident);
		Integer num = 1000;
		assertEquals(num, thisTaskIncident.getLoss());
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Remove of TaskIncidents");
		// ================================================================================
		projectService.removeTaskIncident(ti1.getId());
		projectService.removeTaskIncident(ti2.getId());
		projectService.removeTaskIncident(ti3.getId());
		
		taskIncidents = projectService.findTaskIncidentByProject(testUtils.project);
		assertEquals(0, taskIncidents.size());
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Remove other datas");
		// ================================================================================
		projectService.removeIncident(incident1.getId());
		projectService.removeIncident(incident2.getId());
	}

}
