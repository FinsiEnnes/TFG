package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.service.assignment.AssignmentService;
import es.udc.rs.app.model.service.material.MaterialService;
import es.udc.rs.app.model.service.project.ProjectService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class AssignmentMaterialTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MaterialService materialService;
	
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("===== Starting AssignmentMaterialTest Test ======");
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
	public void fullAssignmentmaterialTest() 
			throws InstanceNotFoundException, InputValidationException, ParseException {

		crudAssignmentMaterial();
		log.info("");
	}

	private void crudAssignmentMaterial() 
			throws InstanceNotFoundException, ParseException, InputValidationException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		boolean error;
		Integer num;
		
		Task task;
		AssignmentMaterial assigMat1;
		AssignmentMaterial assigMat2;
		
		Task nonExistentTask = new Task();
		nonExistentTask.setId(0L);
		
		Material nonExistentMaterial = new Material();
		nonExistentMaterial.setId(0L);
		
		// ================================================================================
		log.info("");
		log.info("===> Initialize necessary datas");
		// ================================================================================
		State plan = projectService.findState("PLAN");
		State prpd = projectService.findState("PRPD");
		State ejec = projectService.findState("EJEC");
		Priority m = projectService.findPriority("M");
		
		task = new Task(testUtils.phase1, "Task 1", plan, m, testUtils.hp1, 5);
		task.setIniPlan(fmt.parse("2016-01-22"));
		HistoryProject planHP = new HistoryProject(testUtils.project, plan, 
												   fmt.parse("2016-01-07"), fmt.parse("2016-01-19"), null);
		
		projectService.createHistoryProject(planHP);
		projectService.createTask(task);
		
		Material material1 = new Material("Proyector","Full HD", 600, false);
		Material material2 = new Material("Servidor","Cisco", 18000, false);
		Material material3 = new Material("Impresora","Colors", 1000, false);
		materialService.createMaterial(material1);
		materialService.createMaterial(material2);
		materialService.createMaterial(material3);
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect creation of AssignmentMaterial");
		// ================================================================================
		
		// Nonexistent task 
		assigMat1 = new AssignmentMaterial(nonExistentTask, material1);
		try {
			error=false;
			assignmentService.createAssignmentMaterial(assigMat1);
		} catch (InstanceNotFoundException e){
			log.info(e.getMessage());
			error=true;
		}
		assertTrue(error);
		
		// Nonexistent material 
		assigMat1 = new AssignmentMaterial(task, nonExistentMaterial);
		try {
			error=false;
			assignmentService.createAssignmentMaterial(assigMat1);
		} catch (InstanceNotFoundException e){
			log.info(e.getMessage());
			error=true;
		}
		assertTrue(error);
		
		// Negative number of units
		assigMat1 = new AssignmentMaterial(task, material1);
		assigMat1.setUnitsPlan(-10);
		try {
			error=false;
			assignmentService.createAssignmentMaterial(assigMat1);
		} catch (InputValidationException e){
			log.info(e.getMessage());
			error=true;
		}
		assertTrue(error);
		
		// UnitsPlan is null
		assigMat1 = new AssignmentMaterial(task, material1);
		incorrectCreate(assigMat1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of AssignmentMaterial");
		// ================================================================================
		assigMat1 = new AssignmentMaterial(task, material1);
		assigMat1.setUnitsPlan(10);
		assignmentService.createAssignmentMaterial(assigMat1);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentMaterial by id");
		// ================================================================================
		AssignmentMaterial otherAssigMat = assignmentService.findAssignmentMaterial(assigMat1.getId());
		assertEquals(assigMat1, otherAssigMat);
		num = 6000;
		assertEquals(num, otherAssigMat.getCostPlan());
		assigMat1 = otherAssigMat;
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentMaterial by Task");
		// ================================================================================
		otherAssigMat = assignmentService.findAssignmentMaterialByTask(task).get(0);
		assertEquals(assigMat1, otherAssigMat);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentMaterial by Planned Task");
		// ================================================================================
		otherAssigMat = assignmentService.findAssignmentMaterialByTaskPlan(task).get(0);
		assertEquals(assigMat1, otherAssigMat);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect update of AssignmentMaterial");
		// ================================================================================
		assigMat1.setCostPlan(10);
		incorrectUpdate(assigMat1);
		assigMat1.setCostPlan(6000);
		
		assigMat1.setReal(true);
		incorrectUpdate(assigMat1);
		assigMat1.setReal(false);
		
		assigMat1.setPlan(false);
		incorrectUpdate(assigMat1);
		assigMat1.setPlan(true);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Update the AssignmentMaterial");
		// ================================================================================
		assigMat1.setUnitsPlan(20);
		assignmentService.updateAssignmentMaterial(assigMat1);
		
		otherAssigMat = assignmentService.findAssignmentMaterial(assigMat1.getId());
		assertEquals(assigMat1, otherAssigMat);
		num = 12000;
		assertEquals(num, otherAssigMat.getCostPlan());
		assigMat1 = otherAssigMat;
		
		// ================================================================================
		log.info("");
		log.info("===> Task state change to PRPD and we cannot insert AssignmentMaterial");
		// ================================================================================
		AssignmentProfile ap1 = new AssignmentProfile(task, testUtils.pc1, 1, 12, 0);
		assignmentService.createAssignmentProfile(ap1);
		
		task.setState(prpd);
		projectService.updateTask(task);
		
		assigMat2 = new AssignmentMaterial(task, material2);
		incorrectCreate(assigMat2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Change Project State: PLAN -> EJEC");
		// ================================================================================
		HistoryProject ejecHP = new HistoryProject(testUtils.project, ejec, 
				   fmt.parse("2016-01-20"), fmt.parse("2016-10-19"), null);
		projectService.createHistoryProject(ejecHP);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Now we change the Task State to EJEC");
		// ================================================================================
		task.setState(ejec);
		task.setIniReal(fmt.parse("2016-01-25"));
		projectService.updateTask(task);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of AssignmentMaterial");
		// ================================================================================
		assigMat2 = new AssignmentMaterial(task, material2);
		assigMat2.setUnitsReal(1);
		assignmentService.createAssignmentMaterial(assigMat2);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentMaterial by Task");
		// ================================================================================
		otherAssigMat = assignmentService.findAssignmentMaterialByTask(task).get(1);
		assertEquals(assigMat2, otherAssigMat);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find AssignmentMaterial by Real Task");
		// ================================================================================
		otherAssigMat = assignmentService.findAssignmentMaterialByTaskReal(task).get(0);
		assertEquals(assigMat2, otherAssigMat);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Update the first AssignmentMaterial with realAssigMat=true");
		// ================================================================================
		assigMat1.setReal(true);
		assigMat1.setUnitsReal(15);
		assignmentService.updateAssignmentMaterial(assigMat1);
		
		otherAssigMat = assignmentService.findAssignmentMaterial(assigMat1.getId());
		assertEquals(assigMat1, otherAssigMat);
		num = 9000;
		assertEquals(num, otherAssigMat.getCostReal());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Delete the AssignmentMaterials");
		// ================================================================================
		List<AssignmentMaterial> assigMats = assignmentService.findAssignmentMaterialByTask(task);
		
		for (AssignmentMaterial am : assigMats) {
			assignmentService.remove(am.getId());
		}
		
		assigMats = assignmentService.findAssignmentMaterialByTask(task);
		assertEquals(0, assigMats.size());
		
		List<Material> materials = materialService.findAllMaterials();
		
		for (Material material : materials) {
			materialService.removeMaterial(material.getId());
		}
	}
	
	private void incorrectCreate(AssignmentMaterial am) throws InstanceNotFoundException, InputValidationException {
		
		boolean error;
		try {
			error=false;
			assignmentService.createAssignmentMaterial(am);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void incorrectUpdate(AssignmentMaterial am) throws InstanceNotFoundException, InputValidationException {
		
		boolean error;
		try {
			error=false;
			assignmentService.updateAssignmentMaterial(am);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
	}
		

}
