package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.service.person.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class ProfessionalCategoryTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private PersonService personService; 
	
	@Test
	public void fullProfessionalCategoryTest () throws ParseException, InputValidationException, InstanceNotFoundException {
		
		log.info("");
		log.info ("==== Starting ProfessionalCategory Test ====");
		findLevelsProfCatg();
	}
	
	private void findLevelsProfCatg() {
		
		LevelProfCatg lpc1 = new LevelProfCatg("ESP","Especialista");
		LevelProfCatg lpc2 = new LevelProfCatg("JUN","Junior");
		LevelProfCatg lpc3 = new LevelProfCatg("PDS","Profesional del sector");
		LevelProfCatg lpc4 = new LevelProfCatg("SJN","Semi Junior");
		LevelProfCatg lpc5 = new LevelProfCatg("SEN","Senior");
		
		List<LevelProfCatg> lpcs = personService.findAllLevelsProfCatg();
		
		// We check the first five aptitudeType of the db.
		assertEquals(lpcs.size(), 5);
		assertEquals(lpcs.get(0), lpc1);
		assertEquals(lpcs.get(1), lpc2);
		assertEquals(lpcs.get(2), lpc3);
		assertEquals(lpcs.get(3), lpc4);
		assertEquals(lpcs.get(4), lpc5);
	
	}

}
