package es.udc.rs.app.model.test;


import static org.junit.Assert.assertEquals;

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
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.Workload;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.material.MaterialService;
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
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private MaterialService materialService;

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

		Project thisProject;
		Task thisTask;
		Integer num;
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date iniPlanProject = fmt.parse("2016-01-01");
		
		Date iniPlanHistoryProject = fmt.parse("2016-08-15");
		Date endPlanHistoryProject = fmt.parse("2016-08-28");
		Date iniEjecHistoryProject = fmt.parse("2016-09-01");
		Date endEjecHistoryProject = fmt.parse("2016-10-05");
		
		Date iniPhase1 = fmt.parse("2016-09-01");
		Date endPhase1 = fmt.parse("2016-09-05");
		Date iniPhase2 = fmt.parse("2016-09-05");
		Date endPhase2 = fmt.parse("2016-09-12");
		
		Date iniPlanTask1 = fmt.parse("2016-09-01");
		Date iniPlanTask2 = fmt.parse("2016-09-04");
		Date iniPlanTask3 = fmt.parse("2016-09-05");
		Date iniPlanTask4 = fmt.parse("2016-09-07");
		Date iniPlanTask5 = fmt.parse("2016-09-09");

		Integer daysPlanTask1 = 3;
		Integer daysPlanTask2 = 1;
		Integer daysPlanTask3 = 2;
		Integer daysPlanTask4 = 2;
		Integer daysPlanTask5 = 3;
		
		// ================================================================================
		log.info("");
		log.info("===================================================================");
		log.info("===> Get some necessary data");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		State ejec = projectService.findState("EJEC");
		State term = projectService.findState("TERM");
		
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
		Person boss = new Person("boss", "b","s","99999999A","boss@gmail.com", fmt.parse("2013-05-06"), "");
		personService.createPerson(boss);

		ProfessionalCategory manager = new ProfessionalCategory("Manager", 10, senior, 12, null);
		personService.createProfessionalCategory(manager);

		HistoryPerson hpManager = new HistoryPerson(boss, manager, fmt.parse("2013-05-06"), null, 12, 14, null);
		personService.createHistoryPerson(hpManager);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of the Employees");
		// ================================================================================	
		Person person1 = new Person("P1", "p","1","11111111A","p1@gmail.com", fmt.parse("2013-05-06"), "");
		Person person2 = new Person("P2", "p","2","22222222B","p2@gmail.com", fmt.parse("2013-05-06"), "");
		Person person3 = new Person("P3", "p","3","33333333C","p3@gmail.com", fmt.parse("2013-05-06"), "");

		personService.createPerson(person1);
		personService.createPerson(person2);
		personService.createPerson(person3);
		
		ProfessionalCategory profCatg1 = new ProfessionalCategory("Perfil1", 1, senior, 6, null);
		ProfessionalCategory profCatg2 = new ProfessionalCategory("Perfil2", 1, senior, 8, null);
		ProfessionalCategory profCatg3 = new ProfessionalCategory("Perfil3", 1, senior, 11, null);
		
		personService.createProfessionalCategory(profCatg1);
		personService.createProfessionalCategory(profCatg2);
		personService.createProfessionalCategory(profCatg3);
		
		HistoryPerson hpPerson1 = new HistoryPerson(person1, profCatg1, fmt.parse("2013-05-06"), null, 6, 8, null);
		HistoryPerson hpPerson2 = new HistoryPerson(person2, profCatg2, fmt.parse("2013-05-06"), null, 8, 10, null);
		HistoryPerson hpPerson3 = new HistoryPerson(person3, profCatg3, fmt.parse("2013-05-06"), null, 11, 13, null);
		
		personService.createHistoryPerson(hpPerson1);
		personService.createHistoryPerson(hpPerson2);
		personService.createHistoryPerson(hpPerson3);
		
		
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
		Task task1 = new Task(phase1, "Tarea 1", plan, m, hpManager, daysPlanTask1);
		task1.setIniPlan(iniPlanTask1);
		
		Task task2 = new Task(phase1, "Tarea 2", plan, m, hpManager, daysPlanTask2);
		task2.setIniPlan(iniPlanTask2);
		
		Task task3 = new Task(phase2, "Tarea 3", plan, m, hpManager, daysPlanTask3);
		task3.setIniPlan(iniPlanTask3);
		
		Task task4 = new Task(phase2, "Tarea 4", plan, m, hpManager, daysPlanTask4);
		task4.setIniPlan(iniPlanTask4);
		
		Task task5 = new Task(phase2, "Tarea 5", plan, m, hpManager, daysPlanTask5);
		task5.setIniPlan(iniPlanTask5);
		
		projectService.createTask(task1);
		projectService.createTask(task2);
		projectService.createTask(task3);
		projectService.createTask(task4);
		projectService.createTask(task5);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Assignment of the Profiles in the Task");
		// ================================================================================
		AssignmentProfile ap1 = new AssignmentProfile(task1, profCatg1, 1, 20, 0);
		AssignmentProfile ap2 = new AssignmentProfile(task1, profCatg2, 1, 10, 0);
		AssignmentProfile ap3 = new AssignmentProfile(task1, profCatg3, 1, 8, 0);
		
		AssignmentProfile ap4 = new AssignmentProfile(task2, profCatg2, 1, 7, 0);
		
		AssignmentProfile ap5 = new AssignmentProfile(task3, profCatg1, 1, 16, 0);
		AssignmentProfile ap6 = new AssignmentProfile(task3, profCatg2, 1, 8, 0);
		
		AssignmentProfile ap7 = new AssignmentProfile(task4, profCatg3, 1, 12, 0);
		
		AssignmentProfile ap8 = new AssignmentProfile(task5, profCatg1, 1, 15, 0);
		AssignmentProfile ap9 = new AssignmentProfile(task5, profCatg2, 1, 12, 0);
		AssignmentProfile ap10 = new AssignmentProfile(task5, profCatg3, 1, 9, 0);
		
		assignmentService.createAssignmentProfile(ap1);
		assignmentService.createAssignmentProfile(ap2);
		assignmentService.createAssignmentProfile(ap3);
		assignmentService.createAssignmentProfile(ap4);
		assignmentService.createAssignmentProfile(ap5);
		assignmentService.createAssignmentProfile(ap6);
		assignmentService.createAssignmentProfile(ap7);
		assignmentService.createAssignmentProfile(ap8);
		assignmentService.createAssignmentProfile(ap9);
		assignmentService.createAssignmentProfile(ap10);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Creation of the Materials");
		// ================================================================================	
		Material material1 = new Material("Material1",null, 60, false);
		Material material2 = new Material("Material2",null, 130, false);
		
		materialService.createMaterial(material1);
		materialService.createMaterial(material2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Assignment of the Materials in the Task");
		// ================================================================================
		AssignmentMaterial assigMat1 = new AssignmentMaterial(task1, material1);
		assigMat1.setUnitsPlan(1);
		
		AssignmentMaterial assigMat2 = new AssignmentMaterial(task5, material2);
		assigMat2.setUnitsPlan(1);
		
		assignmentService.createAssignmentMaterial(assigMat1);
		assignmentService.createAssignmentMaterial(assigMat2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Task State: PLAN -> PRPR");
		// ================================================================================	
		task1.setState(prpd);
		projectService.updateTask(task1);
		
		task2.setState(prpd);
		projectService.updateTask(task2);
		
		task3.setState(prpd);
		projectService.updateTask(task3);
		
		task4.setState(prpd);
		projectService.updateTask(task4);
		
		task5.setState(prpd);
		projectService.updateTask(task5);
		
		
		// Check the final costs
		thisTask = projectService.findTask(task1.getId());
		num = 348;
		assertEquals(num, thisTask.getCostPlan());
		
		thisTask = projectService.findTask(task2.getId());
		num = 56;
		assertEquals(num, thisTask.getCostPlan());
		
		thisTask = projectService.findTask(task3.getId());
		num = 160;
		assertEquals(num, thisTask.getCostPlan());
		
		thisTask = projectService.findTask(task4.getId());
		num = 132;
		assertEquals(num, thisTask.getCostPlan());
		
		thisTask = projectService.findTask(task5.getId());
		num = 415;
		assertEquals(num, thisTask.getCostPlan());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Project State: PLAN -> EJEC");
		// ================================================================================
		HistoryProject ejecHP = new HistoryProject(project, ejec, iniEjecHistoryProject, endEjecHistoryProject, null);
		projectService.createHistoryProject(ejecHP);
		
		// Check the planned data of the Project
		thisProject = projectService.findProject(project.getId());
		
		num = 11;
		assertEquals(num, thisProject.getDaysPlan());
		
		num = 117;
		assertEquals(num, thisProject.getHoursPlan());
		
		num = 1111;
		assertEquals(num, thisProject.getCostPlan());
		
		assertEquals(fmt.parse("2016-09-12"), thisProject.getEndPlan());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Real datas for Tarea1");
		// ================================================================================
		task1.setIniReal(fmt.parse("2016-09-01"));
		task1.setState(ejec);
		
		projectService.updateTask(task1);
		
		AssignmentPerson assigPerson1 = new AssignmentPerson(task1, hpPerson1, false);
		AssignmentPerson assigPerson2 = new AssignmentPerson(task1, hpPerson2, false);
		AssignmentPerson assigPerson3 = new AssignmentPerson(task1, hpPerson3, false);
		
		assignmentService.createAssignmentPerson(assigPerson1);
		assignmentService.createAssignmentPerson(assigPerson2);
		assignmentService.createAssignmentPerson(assigPerson3);
		
		Workload wl1 = new Workload(task1, hpPerson1, fmt.parse("2016-09-01"), 8, 0);
		Workload wl2 = new Workload(task1, hpPerson1, fmt.parse("2016-09-02"), 4, 0);
		Workload wl3 = new Workload(task1, hpPerson1, fmt.parse("2016-09-03"), 6, 0);
		Workload wl4 = new Workload(task1, hpPerson1, fmt.parse("2016-09-04"), 3, 0);
		Workload wl5 = new Workload(task1, hpPerson1, fmt.parse("2016-09-05"), 2, 0);
		
		Workload wl6 = new Workload(task1, hpPerson2, fmt.parse("2016-09-01"), 2, 0);
		Workload wl7 = new Workload(task1, hpPerson2, fmt.parse("2016-09-02"), 8, 0);
		Workload wl8 = new Workload(task1, hpPerson2, fmt.parse("2016-09-03"), 6, 0);
		Workload wl9 = new Workload(task1, hpPerson2, fmt.parse("2016-09-04"), 4, 0);
		Workload wl10 = new Workload(task1, hpPerson2, fmt.parse("2016-09-05"), 2, 0);
		
		Workload wl11 = new Workload(task1, hpPerson3, fmt.parse("2016-09-01"), 5, 0);
		Workload wl12 = new Workload(task1, hpPerson3, fmt.parse("2016-09-02"), 7, 0);
		
		assignmentService.createWorkload(wl1);
		assignmentService.createWorkload(wl2);
		assignmentService.createWorkload(wl3);
		assignmentService.createWorkload(wl4);
		assignmentService.createWorkload(wl5);
		assignmentService.createWorkload(wl6);
		assignmentService.createWorkload(wl7);
		assignmentService.createWorkload(wl8);
		assignmentService.createWorkload(wl9);
		assignmentService.createWorkload(wl10);
		assignmentService.createWorkload(wl11);
		assignmentService.createWorkload(wl12);

		assigPerson1.setConclude(true);
		assigPerson2.setConclude(true);
		assigPerson3.setConclude(true);
		
		assignmentService.updateAssignmentPerson(assigPerson1);
		assignmentService.updateAssignmentPerson(assigPerson2);
		assignmentService.updateAssignmentPerson(assigPerson3);
		
		task1.setState(term);
		projectService.updateTask(task1);
		
		
	}

}
