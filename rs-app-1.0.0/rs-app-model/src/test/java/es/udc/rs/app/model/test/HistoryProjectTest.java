package es.udc.rs.app.model.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
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
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class HistoryProjectTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullHistoryProjectTest () throws ParseException, 
												InputValidationException, InstanceNotFoundException {
		
		log.info("");
		log.info ("========== Starting HistoryProject Test =========");
		createFindAndUpdateHistoryProject();
		removeHistoryProject();		
	}
	
	private void createFindAndUpdateHistoryProject() 
			throws InstanceNotFoundException, InputValidationException, ParseException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			
		log.info("");
		log.info ("===> Initialize some datas");
		
		// First we need to create a project
		Province province = customerService.findProvince(1L);
		Project project = new Project("Proyecto Zeta", new Date(), false, province, new Date());
		projectService.createProject(project);
		
		// We also need a State
		State plan = projectService.findState("PLAN");
		State ejec = projectService.findState("EJEC");
		State term = projectService.findState("TERM");
		
		// ================================================================================
		log.info("");
		log.info ("===> Create HistoryProject with wrong initial State");
		// ================================================================================
		HistoryProject incorrectHP = new HistoryProject(project, ejec, fmt.parse("2015-05-06"), null, null);
		incorrectCreate(incorrectHP);

		incorrectHP = new HistoryProject(project, term, fmt.parse("2015-05-06"), null, null);
		incorrectCreate(incorrectHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Create HistoryProject with iniDate greater than endDate");
		// ================================================================================
		incorrectHP = new HistoryProject(project,plan,fmt.parse("2015-05-06"),fmt.parse("2014-05-06"),null);
		incorrectCreate(incorrectHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Correct creation");
		// ================================================================================
		HistoryProject planHP = new HistoryProject(project, plan, fmt.parse("2015-05-06"), null, null);
		projectService.createHistoryProject(planHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Incorrect creation because the current history hasn't got endDate");
		// ================================================================================
		incorrectHP = new HistoryProject(project, ejec, fmt.parse("2016-05-06"), null, null);
		incorrectCreate(incorrectHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> We assign a endDate for the current history project");
		// ================================================================================
		planHP.setEnd(fmt.parse("2015-12-20"));
		projectService.updateHistoryProject(planHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> Incorrect state transtion in a new history project");
		// ================================================================================
		incorrectHP = new HistoryProject(project, term, fmt.parse("2016-01-10"), null, null);
		incorrectCreate(incorrectHP);
		
		incorrectHP = new HistoryProject(project, plan, fmt.parse("2016-01-10"), null, null);
		incorrectCreate(incorrectHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> New history project with a correct state transtion");
		// ================================================================================
		HistoryProject ejecHP = new HistoryProject(project,ejec,fmt.parse("2016-01-10"),fmt.parse("2016-10-20"),null);
		projectService.createHistoryProject(ejecHP);
		
		
		// ================================================================================
		log.info("");
		log.info ("===> New history project with a iniDate lower than the current history endDate");
		// ================================================================================
		incorrectHP = new HistoryProject(project, term, fmt.parse("2016-01-10"), null, null);
		incorrectCreate(incorrectHP);	
		
		// ================================================================================
		log.info("");
		log.info ("===> If the current HistoryProject has a endDate the changes are forbidden");
		// ================================================================================
		planHP.setEnd(new Date());
		incorrectUpdate(planHP);

		
		// ================================================================================
		log.info("");
		log.info ("===> Find by id");
		// ================================================================================
		HistoryProject hp = projectService.findHistoryProject(planHP.getId());
		assertEquals(planHP, hp);
		
		hp = projectService.findHistoryProject(ejecHP.getId());
		assertEquals(ejecHP, hp);

		// ================================================================================
		log.info("");
		log.info ("===> Find by project");
		// ================================================================================
		List<HistoryProject> histories = projectService.findHistoryProjectByProject(project);
		assertEquals(2, histories.size());
		assertEquals(planHP, histories.get(0));
		assertEquals(ejecHP, histories.get(1));

		
		// ================================================================================
		log.info("");
		log.info ("===> Find current history project");
		// ================================================================================
		hp = projectService.findCurrentHistoryProject(project);
		assertEquals(ejecHP, hp);
	}
	
	private void removeHistoryProject() throws InstanceNotFoundException {
		
		// ================================================================================
		log.info("");
		log.info ("===> Delete all histories project");
		// ================================================================================
		Project project = projectService.findProjectByName("Proyecto Zeta");
		List<HistoryProject> histories = projectService.findHistoryProjectByProject(project);
		
		for (HistoryProject h : histories) {
			projectService.removeHistoryProject(h.getId());
		}
		
		projectService.removeProject(project.getId());
	}
	
	private void incorrectCreate(HistoryProject historyProject) throws InstanceNotFoundException {
		boolean error;
		
		try {
			error=false;
			projectService.createHistoryProject(historyProject);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void incorrectUpdate(HistoryProject historyProject) throws InstanceNotFoundException {
		boolean error;
		
		try {
			error=false;
			projectService.updateHistoryProject(historyProject);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
	}
	

}
