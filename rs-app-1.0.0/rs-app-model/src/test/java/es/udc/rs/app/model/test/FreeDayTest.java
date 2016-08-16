package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class FreeDayTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private ProjectService projectService;
	
	@Test
	public void fullFreeDayTest() throws InstanceNotFoundException, InputValidationException, ParseException {
		log.info("");
		log.info ("=========== Starting FreeDay Test ==========");
		crudFreeDay();		
	}

	private void crudFreeDay() throws InputValidationException, ParseException, InstanceNotFoundException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		FreeDay incorrectFreeDay;
		FreeDay fd1, fd2, fd3, fd4;
		FreeDay thisFreeDay;
		
		Date d1 = fmt.parse("2016-12-20");
		Date d2 = fmt.parse("2016-12-30");
		Date d3 = fmt.parse("2017-04-10");
		Date d4 = fmt.parse("2017-04-16");
		Date d5 = fmt.parse("2017-08-01");
		Date d6 = fmt.parse("2017-08-15");
		
		
		// ================================================================================
		log.info("");
		log.info("===> Incorrect creation of FreeDay");
		// ================================================================================	
		incorrectFreeDay = new FreeDay("only name", null, null, null);
		incorrectCreate(incorrectFreeDay);
		
		incorrectFreeDay = new FreeDay("name", 9, null, null); // WeekDay is not between 0 and 6
		incorrectCreateInput(incorrectFreeDay);
		
		incorrectFreeDay = new FreeDay("name", 5, d1, null); // We cannot define weekDay and iniFreeDay
		incorrectCreate(incorrectFreeDay);
		
		incorrectFreeDay = new FreeDay("name", 5, d1, null); // We cannot define weekDay and iniFreeDay
		incorrectCreate(incorrectFreeDay);
		
		incorrectFreeDay = new FreeDay("name", null, d1, null); // If you define the ini, you must define end
		incorrectCreate(incorrectFreeDay);
		
		incorrectFreeDay = new FreeDay("name", null, d2, d1); // Then ini is greater than the end
		incorrectCreate(incorrectFreeDay);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct creation of FreeDay");
		// ================================================================================	
		fd1 = new FreeDay("finde", 5, null, null);
		projectService.createFreeDay(fd1);
		
		fd2 = new FreeDay("navidades", null, d1, d2);
		projectService.createFreeDay(fd2);
		
		fd3 = new FreeDay("semana santa", null, d3, d4);
		projectService.createFreeDay(fd3);
		
		fd4 = new FreeDay("vacaciones agosto", null, d5, d6);
		projectService.createFreeDay(fd4);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find FreeDay by id");
		// ================================================================================	
		thisFreeDay = projectService.findFreeDay(fd1.getId());
		assertEquals(fd1, thisFreeDay);
		
		thisFreeDay = projectService.findFreeDay(fd2.getId());
		assertEquals(fd2, thisFreeDay);
		
		thisFreeDay = projectService.findFreeDay(fd3.getId());
		assertEquals(fd3, thisFreeDay);
		
		thisFreeDay = projectService.findFreeDay(fd4.getId());
		assertEquals(fd4, thisFreeDay);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Find all FreeDay");
		// ================================================================================	
		List<FreeDay> freeDays = projectService.findAllFreeDay();
		
		assertEquals(4, freeDays.size());
		assertEquals(fd1, freeDays.get(0));
		assertEquals(fd2, freeDays.get(1));
		assertEquals(fd3, freeDays.get(2));
		assertEquals(fd4, freeDays.get(3));
		
		
		// ================================================================================
		log.info("");
		log.info("===> Correct update FreeDay");
		// ================================================================================	
		fd1.setWeekDay(6);
		projectService.updateFreeDay(fd1);
		
		thisFreeDay = projectService.findFreeDay(fd1.getId());
		assertEquals(fd1, thisFreeDay);
		
		fd4.setEnd(fmt.parse("2017-08-20"));
		projectService.updateFreeDay(fd4);
		
		thisFreeDay = projectService.findFreeDay(fd4.getId());
		assertEquals(fd4, thisFreeDay);
		
		
		// ================================================================================
		log.info("");
		log.info("===> Remove the created FreeDay");
		// ================================================================================	
		projectService.removeFreeDay(fd1.getId());
		projectService.removeFreeDay(fd2.getId());
		projectService.removeFreeDay(fd3.getId());
		projectService.removeFreeDay(fd4.getId());
		
		
		// ================================================================================
		log.info("");
		log.info("===> Check the FreeDays");
		// ================================================================================	
		freeDays = projectService.findAllFreeDay();	
		
		assertEquals(0, freeDays.size());
	}
	

	private void incorrectCreate(FreeDay incorrectFreeDay) throws InputValidationException {
		boolean error;
		
		try {
			error=false;
			projectService.createFreeDay(incorrectFreeDay);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
	}
	
	private void incorrectCreateInput(FreeDay incorrectFreeDay) throws InputValidationException {
		boolean error;
		
		try {
			error=false;
			projectService.createFreeDay(incorrectFreeDay);
		} catch (InputValidationException e){
			log.info("Error: The attribute weekDay is not between 0 and 6");
			error=true;
		}
		assertTrue(error);
		
	}

}
