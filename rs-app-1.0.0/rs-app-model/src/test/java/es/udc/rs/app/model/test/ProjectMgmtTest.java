package es.udc.rs.app.model.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.ProjectMgmt;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class ProjectMgmtTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private ProjectService projectService;
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("========== Starting ProjectMgmt Test ============");
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
	public void fullProjectMgmtTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		crudProjectMgmt();	
		log.info("");
		log.info("===================================================================");
	}

	private void crudProjectMgmt() throws InstanceNotFoundException, ParseException, InputValidationException {

		ProjectMgmt pm1, pm2, pm3, thisPm;
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");	
		Date d1 = fmt.parse("2016-02-05");
		Date d2 = fmt.parse("2016-10-25");
				
		// ================================================================================
		log.info("");
		log.info("===> Incorrect creation of ProjectMgmt");
		// ================================================================================
		pm1 = new ProjectMgmt(testUtils.project, testUtils.hp1, d2, d1);
		incorrectCreate(pm1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of ProjectMgmt");
		// ================================================================================
		pm1 = new ProjectMgmt(testUtils.project, testUtils.hp1, d1, d2);
		projectService.createProjectMgmt(pm1);
		
		pm2 = new ProjectMgmt(testUtils.project, testUtils.hp2, fmt.parse("2016-02-10"), d2);
		projectService.createProjectMgmt(pm2);
		
		pm3 = new ProjectMgmt(testUtils.project, testUtils.hp3, fmt.parse("2016-02-15"), d2);
		projectService.createProjectMgmt(pm3);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find ProjectMgmt by id");
		// ================================================================================
		thisPm = projectService.findProjectMgmt(pm1.getId());
		assertEquals(pm1, thisPm);
		
		thisPm = projectService.findProjectMgmt(pm2.getId());
		assertEquals(pm2, thisPm);
		
		thisPm = projectService.findProjectMgmt(pm3.getId());
		assertEquals(pm3, thisPm);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find all the ProjectMgmt");
		// ================================================================================
		List<ProjectMgmt> projectMgmts = projectService.findAllProjectMgmt();
		
		assertEquals(3, projectMgmts.size());
		assertEquals(pm1, projectMgmts.get(0));
		assertEquals(pm2, projectMgmts.get(1));
		assertEquals(pm3, projectMgmts.get(2));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find ProjectMgmts by Project");
		// ================================================================================
		projectMgmts = projectService.findProjectMgmtByProject(testUtils.project);
		
		assertEquals(3, projectMgmts.size());
		assertEquals(pm1, projectMgmts.get(0));
		assertEquals(pm2, projectMgmts.get(1));
		assertEquals(pm3, projectMgmts.get(2));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct update of ProjectMgmts");
		// ================================================================================
		pm1.setEnd(fmt.parse("2016-12-10"));
		projectService.updateProjectMgmt(pm1);
		
		thisPm = projectService.findProjectMgmt(pm1.getId());
		assertEquals(pm1, thisPm);
		assertEquals(fmt.parse("2016-12-10"), thisPm.getEnd());
		
		// ================================================================================
		log.info("");
		log.info("===> Delete of ProjectMgmts");
		// ================================================================================
		projectService.removeProjectMgmt(pm1.getId());
		projectService.removeProjectMgmt(pm2.getId());
		projectService.removeProjectMgmt(pm3.getId());
		
		projectMgmts = projectService.findAllProjectMgmt();
		assertEquals(0, projectMgmts.size());
	}
	
	private void incorrectCreate(ProjectMgmt pm) throws InstanceNotFoundException {
		boolean error;
		
		try {
			error=false;
			projectService.createProjectMgmt(pm);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);	
	}

}
