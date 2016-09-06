package es.udc.rs.app.model.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class FullTest {
	
	private Logger log = Logger.getLogger("project");

	@Autowired
	private ProjectService projectService;
	
	@Autowired 
	private PersonService personService;

	@Autowired
	private CustomerService customerService;

	@Test
	public void fullTest() throws InstanceNotFoundException, InputValidationException, ParseException {

		log.info("");
		log.info ("============== Starting Full Test ==============");
		thisTest();	
	}

	private void thisTest() throws InstanceNotFoundException, InputValidationException, ParseException {

		// ================================================================================
		// Variables
		// ================================================================================

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date iniPlanProject = fmt.parse("2016-01-01");
		
		Date iniPlanHistoryProject = fmt.parse("2016-01-01");
		Date endPlanHistoryProject = fmt.parse("2016-02-28");
		Date iniEjecHistoryProject = fmt.parse("2016-03-01");
		Date endEjecHistoryProject = fmt.parse("2016-10-05");
		
		Date iniPhase1 = fmt.parse("2016-02-01");
		Date endPhase1 = fmt.parse("2016-05-15");
		Date iniPhase2 = fmt.parse("2016-05-15");
		Date endPhase2 = fmt.parse("2016-10-10");
		
		Date iniPlanTask1 = fmt.parse("2016-02-05");
		Date iniPlanTask2 = fmt.parse("2016-04-01");
		Date iniPlanTask3 = fmt.parse("2016-05-15");
		Date iniPlanTask4 = fmt.parse("2016-07-15");
		Date iniPlanTask5 = fmt.parse("2016-09-01");

		Integer daysPlanTask1 = 56;
		Integer daysPlanTask2 = 44;
		Integer daysPlanTask3 = 61;
		Integer daysPlanTask4 = 48;
		Integer daysPlanTask5 = 30;
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Get some necessary data");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		State ejec = projectService.findState("EJEC");
		
		Priority m = projectService.findPriority("M");
		
		LevelProfCatg junior = personService.findLevelProfCatg("JUN");
		LevelProfCatg senior = personService.findLevelProfCatg("SEN");
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of the Project");
		// ================================================================================	
		Province province = customerService.findProvince(1L);
		Project project = new Project("Mi Proyecto", new Date(), false, province, iniPlanProject);
		projectService.createProject(project);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of the Project Manager");
		// ================================================================================	
		Person person1 = new Person("P1", "p","1","11111111A","p1@gmail.com", fmt.parse("2013-05-06"), "");
		personService.createPerson(person1);

		ProfessionalCategory manager = new ProfessionalCategory("Manager", 10, senior, 12, null);
		personService.createProfessionalCategory(manager);

		HistoryPerson hpManager = new HistoryPerson(person1, manager, fmt.parse("2013-05-06"), null, 12, 14, null);
		personService.createHistoryPerson(hpManager);
		
		// ================================================================================
		log.info("");
		log.info("===> Current Project State: PLAN");
		// ================================================================================	
		HistoryProject planHP = new HistoryProject(project, plan, iniPlanHistoryProject, endPlanHistoryProject, null);
		projectService.createHistoryProject(planHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of the Phases");
		// ================================================================================	
		Phase phase1 = new Phase(project,"Fase 1", iniPhase1, endPhase1,null);
		Phase phase2 = new Phase(project,"Fase 2", iniPhase2, endPhase2, null);
		
		projectService.createPhase(phase1);
		projectService.createPhase(phase2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct create of Tasks");
		// ================================================================================
		Task task1 = new Task(phase1, "Task 1", plan, m, hpManager, daysPlanTask1);
		task1.setIniPlan(iniPlanTask1);
		
		Task task2 = new Task(phase1, "Task 2", plan, m, hpManager, daysPlanTask2);
		task2.setIniPlan(iniPlanTask2);
		
		Task task3 = new Task(phase2, "Task 3", plan, m, hpManager, daysPlanTask3);
		task3.setIniPlan(iniPlanTask3);
		
		Task task4 = new Task(phase2, "Task 4", plan, m, hpManager, daysPlanTask4);
		task4.setIniPlan(iniPlanTask4);
		
		Task task5 = new Task(phase2, "Task 5", plan, m, hpManager, daysPlanTask5);
		task5.setIniPlan(iniPlanTask5);
		
		projectService.createTask(task1);
		projectService.createTask(task2);
		projectService.createTask(task3);
		projectService.createTask(task4);
		projectService.createTask(task5);
	}

}
