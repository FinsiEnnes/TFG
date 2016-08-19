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
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.ProjectFreeDay;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class ProjectFreeDayTest {

	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private ProjectService projectService;
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("========= Starting ProjectFreeDay Test ==========");
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
	public void fullProjectFreeDayTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		crudProjectFreeDay();	
		log.info("");
		log.info("===================================================================");
	}

	private void crudProjectFreeDay() throws InstanceNotFoundException, ParseException, InputValidationException {
		
		boolean error;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		FreeDay fd1, fd2, fd3, fd4;
		ProjectFreeDay pfd1, pfd2, pfd3, thisPfd;
		
		Date d1 = fmt.parse("2016-12-20");
		Date d2 = fmt.parse("2016-12-30");
		Date d3 = fmt.parse("2017-04-10");
		Date d4 = fmt.parse("2017-04-16");
		Date d5 = fmt.parse("2017-08-01");
		Date d6 = fmt.parse("2017-08-15");
		
		Date datePlan1 = fmt.parse("2016-01-07");
		Date datePlan2 = fmt.parse("2016-01-19");
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Get some necessary data and define PLAN as state for the Project");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State ejec = projectService.findState("EJEC");
		
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, datePlan1, datePlan2, null);
		projectService.createHistoryProject(planHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of FreeDay");
		// ================================================================================	
		fd1 = new FreeDay("sabado", 5, null, null);
		projectService.createFreeDay(fd1);
		
		fd2 = new FreeDay("navidades", null, d1, d2);
		projectService.createFreeDay(fd2);
		
		fd3 = new FreeDay("semana santa", null, d3, d4);
		projectService.createFreeDay(fd3);
		
		fd4 = new FreeDay("vacaciones agosto", null, d5, d6);
		projectService.createFreeDay(fd4);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of ProjectFreeDay");
		// ================================================================================	
		pfd1 = new ProjectFreeDay(testUtils.project, fd1);
		projectService.createProjectFreeDay(pfd1);
		
		pfd2 = new ProjectFreeDay(testUtils.project, fd2);
		projectService.createProjectFreeDay(pfd2);
		
		pfd3 = new ProjectFreeDay(testUtils.project, fd3);
		projectService.createProjectFreeDay(pfd3);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find ProjectFreeDays by id");
		// ================================================================================	
		thisPfd = projectService.findProjectFreeDay(pfd1.getId());
		assertEquals(pfd1, thisPfd);
		
		thisPfd = projectService.findProjectFreeDay(pfd2.getId());
		assertEquals(pfd2, thisPfd);
		
		thisPfd = projectService.findProjectFreeDay(pfd3.getId());
		assertEquals(pfd3, thisPfd);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find all ProjectFreeDays");
		// ================================================================================	
		List<ProjectFreeDay> projectFreeDays = projectService.findAllProjectFreeDay();
		
		assertEquals(3, projectFreeDays.size());
		assertEquals(pfd1, projectFreeDays.get(0));
		assertEquals(pfd2, projectFreeDays.get(1));
		assertEquals(pfd3, projectFreeDays.get(2));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find FreeDays by the Project");
		// ================================================================================	
		List<FreeDay> freeDays = projectService.findProjectFreeDayByProject(testUtils.project);
		
		assertEquals(3, projectFreeDays.size());
		assertEquals(fd1, freeDays.get(0));
		assertEquals(fd2, freeDays.get(1));
		assertEquals(fd3, freeDays.get(2));
		
		// ================================================================================
		log.info("");
		log.info("===> Correct update of ProjectFreeDay");
		// ================================================================================	
		pfd3.setFreeDay(fd4);
		projectService.updateProjectFreeDay(pfd3);
		
		thisPfd = projectService.findProjectFreeDay(pfd3.getId());
		assertEquals(pfd3, thisPfd);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct delete of ProjectFreeDay");
		// ================================================================================	
		projectService.removeProjectFreeDay(pfd2.getId());
		
		// Check if the object was deleted
		try {
			error=false;
			projectService.findProjectFreeDay(pfd2.getId());
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change the project state: EJEC");
		// ================================================================================	
		HistoryProject ejecHP = new HistoryProject(testUtils.project, ejec, datePlan2, null, null);
		projectService.createHistoryProject(ejecHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> With this project state we cannot insert/update ProjectFreedays");
		// ================================================================================
		FreeDay fd5 = new FreeDay("domingo", 6, null, null);
		projectService.createFreeDay(fd5);
		
		// Create
		thisPfd = new ProjectFreeDay(testUtils.project, fd5);
		incorrectCreate(thisPfd);
		
		// Update
		pfd3.setFreeDay(fd5);
		incorrectUpdate(pfd3);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Remove the created Data");
		// ================================================================================	
		projectService.removeProjectFreeDay(pfd1.getId());
		projectService.removeProjectFreeDay(pfd3.getId());
		
		projectService.removeFreeDay(fd1.getId());
		projectService.removeFreeDay(fd2.getId());
		projectService.removeFreeDay(fd3.getId());
		projectService.removeFreeDay(fd4.getId());
		projectService.removeFreeDay(fd5.getId());
	}
	
	private void incorrectCreate(ProjectFreeDay pfd) throws InstanceNotFoundException {
		boolean error;
		
		try {
			error=false;
			projectService.createProjectFreeDay(pfd);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
	}
	
	private void incorrectUpdate(ProjectFreeDay pfd) throws InstanceNotFoundException {
		boolean error;
		
		try {
			error=false;
			projectService.updateProjectFreeDay(pfd);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
		
	}
}
