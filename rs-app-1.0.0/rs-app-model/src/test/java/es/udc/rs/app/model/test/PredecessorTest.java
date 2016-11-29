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
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class PredecessorTest {

	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("=========== Starting Predecessor Test ===========");
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
	public void fullPrecessorTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		crudPredecessor();	
		log.info("");
		log.info("===================================================================");
	}

	private void crudPredecessor() throws InstanceNotFoundException, ParseException, InputValidationException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Predecessor otherPred;
		
		// ================================================================================
		log.info("");
		log.info("===> Get some data");
		// ================================================================================	
		State plan = projectService.findState("PLAN");
		State canc = projectService.findState("CANC");
		
		Priority m = projectService.findPriority("M");
		
		TaskLinkType fc = projectService.findTaskLinkType("FC");
		TaskLinkType ff = projectService.findTaskLinkType("FF");
		
		// ================================================================================
		log.info("");
		log.info("===> Initialize necessary data");
		// ================================================================================		
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, fmt.parse("2016-01-07"), 
												   fmt.parse("2016-01-19"), null);
		projectService.createHistoryProject(planHP);
		
		Task task1 = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		Task task2 = new Task(testUtils.phase1, "Task 2", plan, m, testUtils.hp1, 12);
		Task task3 = new Task(testUtils.phase1, "Task 3", plan, m, testUtils.hp1, 4);
		Task task4 = new Task(testUtils.phase1, "Task 4", plan, m, testUtils.hp1, 8);

		projectService.createTask(task1);
		projectService.createTask(task2);
		projectService.createTask(task3);
		projectService.createTask(task4);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct links between Task");
		// ================================================================================	
		Predecessor p1 = new Predecessor(task1, task2, fc);
		Predecessor p2 = new Predecessor(task1, task3, fc);
		Predecessor p3 = new Predecessor(task3, task2, ff);
		
		projectService.createPredecessor(p1);
		projectService.createPredecessor(p2);
		projectService.createPredecessor(p3);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find the Predecessor object by id");
		// ================================================================================	
		otherPred = projectService.findPredecessor(p1.getId());
		assertEquals(p1, otherPred);
		
		otherPred = projectService.findPredecessor(p2.getId());
		assertEquals(p2, otherPred);
		
		otherPred = projectService.findPredecessor(p3.getId());
		assertEquals(p3, otherPred);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find the Predecessor object by Task");
		// ================================================================================	
		List<Predecessor> predecessors = projectService.findPredecessorByTask(task1);
		assertEquals(2, predecessors.size());
		assertEquals(p1, predecessors.get(0));
		assertEquals(p2, predecessors.get(1));
		
		predecessors = projectService.findPredecessorByTask(task3);
		assertEquals(1, predecessors.size());
		assertEquals(p3, predecessors.get(0));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect update of Predecessor due to a change in the main Task");
		// ================================================================================	
		p1.setTask(task4);
		boolean error;
		try {
			error=false;
			projectService.updatePredecessor(p1);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
		p1.setTask(task1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct delete of Predecessors");
		// ================================================================================	
		projectService.removePredecessor(p1.getId());
		projectService.removePredecessor(p2.getId());
		projectService.removePredecessor(p3.getId());
		
		predecessors = projectService.findPredecessorByTask(task1);
		assertEquals(0, predecessors.size());
		
		predecessors = projectService.findPredecessorByTask(task3);
		assertEquals(0, predecessors.size());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect creation of Predecessor -> Link with tasks of different Projects");
		// ================================================================================	
		Province province = customerService.findProvince(1L);
		Project otherProject = new Project("Other", new Date(), false, province, fmt.parse("2016-01-06"));		
		Phase phase = new Phase(otherProject,"Fase 1");

		projectService.createProject(otherProject);
		projectService.createPhase(phase);
		
		HistoryProject hp = new HistoryProject(otherProject, plan, fmt.parse("2016-01-07"), 
				fmt.parse("2016-01-19"), null);
		projectService.createHistoryProject(hp);
		
		Task task5 = new Task(phase, "Task 5", plan, m, testUtils.hp1, 8);
		projectService.createTask(task5);
		
		Predecessor p5 = new Predecessor(task5, task2, ff);
		incorrectCreate(p5);
		
		projectService.removeProject(otherProject.getId());
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect creation of Predecessor -> Link with a cancel Task");
		// ================================================================================	
		task2.setState(canc);
		projectService.updateTask(task2);
		
		Predecessor p6 = new Predecessor(task2, task3, ff);
		incorrectCreate(p6);
		
	}
	
	private void incorrectCreate(Predecessor p) throws InputValidationException, InstanceNotFoundException {
		
		boolean error;
		
		try {
			error=false;
			projectService.createPredecessor(p);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}
	
	
}
