package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class ProjectTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullProjectTest() throws InputValidationException, InstanceNotFoundException, ParseException {
		
		log.info("");
		log.info ("========== Starting Project Test ===========");
		createAndFindProject();
		updateProject();
		removeProject();
	}

	private void createAndFindProject() throws InstanceNotFoundException, InputValidationException, ParseException {
		
		Province province = customerService.findProvince(1L);
		Project thisProject;
		boolean error;
		
		// ================================ Correct create ================================
		Project p1 = new Project("Proyecto Zeta", new Date(), false, province, new Date());
		projectService.createProject(p1);
		
		// =============================== Incorrect create ===============================
		Project incorrectProject = new Project("Otro proyecto", new Date(), false, province, new Date());
				
		// Negative budget
		incorrectProject.setBudget(-1000);
		
		try {
			error=false;
			projectService.createProject(incorrectProject);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);
		
		// Define real or planned data is forbidden
		incorrectProject.setBudget(1000);
		incorrectProject.setCostPlan(1000);
		
		try {
			error=false;
			projectService.createProject(incorrectProject);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		// Attribute stateDate greater than today
		incorrectProject.setCostPlan(null);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = fmt.parse("2030-06-06");
		incorrectProject.setStateDate(d1);
		
		try {
			error=false;
			projectService.createProject(incorrectProject);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		// ================================== Find by id ==================================
		thisProject = projectService.findProject(p1.getId());
		assertEquals(p1, thisProject);
		
		// ================================= Find by name =================================
		thisProject = projectService.findProjectByName("Proyecto Zeta");
		assertEquals(p1, thisProject);
	}
	
	private void updateProject() {
		// TODO Auto-generated method stub
		
	}

	private void removeProject() throws InstanceNotFoundException {
		Project thisProject = projectService.findProjectByName("Proyecto Zeta");
		projectService.removeProject(thisProject.getId());
		
	}

}
