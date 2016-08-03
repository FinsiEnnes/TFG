package es.udc.rs.app.model.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.TaskLinkType;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class PriorityTest {

	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Test
	public void fullPriorityTest () throws InstanceNotFoundException {
		log.info("");
		log.info ("============ Starting Priority Test =============");
		findPriority();
		findTaskLinkType();


	}

	private void findPriority() throws InstanceNotFoundException {
		
		// Create the data of this table
		Priority p1 = new Priority("MA", "Muy Alta");
		Priority p2 = new Priority("A", "Alta");
		Priority p3 = new Priority("M", "Media");
		Priority p4 = new Priority("B", "Baja");

		// ================================================================================
		log.info("");
		log.info ("===> Find Priority by id");
		// ================================================================================
		Priority priority = projectService.findPriority(p1.getId());
		assertEquals(p1, priority);
		
		priority = projectService.findPriority(p2.getId());
		assertEquals(p2, priority);
		
		priority = projectService.findPriority(p3.getId());
		assertEquals(p3, priority);
		
		priority = projectService.findPriority(p4.getId());
		assertEquals(p4, priority);
		
		// ================================================================================
		log.info("");
		log.info ("===> Find all the Priority");
		// ================================================================================
		List<Priority> priorities = projectService.findAllPriority();
		
		assertEquals(4, priorities.size());
		assertEquals(priorities.get(0), p2);
		assertEquals(priorities.get(1), p4);
		assertEquals(priorities.get(2), p3);
		assertEquals(priorities.get(3), p1);
	}
	
	private void findTaskLinkType() throws InstanceNotFoundException {
		
		// Create the data of the table
		TaskLinkType t1 = new TaskLinkType("CC", "Comienzo-Comienzo");
		TaskLinkType t2 = new TaskLinkType("CF", "Comienzo-Fin");
		TaskLinkType t3 = new TaskLinkType("FC", "Fin-Comienzo");
		TaskLinkType t4 = new TaskLinkType("FF", "Fin-Fin");
		
		// ================================================================================
		log.info("");
		log.info ("===> Find TaskLinkType by id");
		// ================================================================================
		TaskLinkType tlt = projectService.findTaskLinkType(t1.getId());
		assertEquals(t1, tlt);
		
		tlt = projectService.findTaskLinkType(t2.getId());
		assertEquals(t2, tlt);
		
		tlt = projectService.findTaskLinkType(t3.getId());
		assertEquals(t3, tlt);
		
		tlt = projectService.findTaskLinkType(t4.getId());
		assertEquals(t4, tlt);
		
		// ================================================================================
		log.info("");
		log.info ("===> Find all the TaskLinkType");
		// ================================================================================
		List<TaskLinkType> tlts = projectService.findAllTaskLinkType();

		assertEquals(4, tlts.size());
		assertEquals(tlts.get(0), t1);
		assertEquals(tlts.get(1), t2);
		assertEquals(tlts.get(2), t3);
		assertEquals(tlts.get(3), t4);
	}

}
