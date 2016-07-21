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
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TimeOffTest {
	
	private Logger log = Logger.getLogger("project");
	 
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private PersonService personService; 
	
	@Test
	public void fullTimeOffTest() throws ParseException, InputValidationException, InstanceNotFoundException {
		
		log.info("");
		log.info ("=========== Starting TimeOff Test ===========");
		createAndFindTimeOff();	
		updateTimeOff();
		removeTimeOff();		
	}
	
	private void createAndFindTimeOff() throws ParseException, InputValidationException, InstanceNotFoundException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = fmt.parse("2014-06-06");
		Date d2 = fmt.parse("2015-01-10");
		Date d3 = fmt.parse("2015-01-16");
		Date d4 = fmt.parse("2016-04-20");
		Date d5 = fmt.parse("2016-05-20");
		
		boolean error;
		
		// ===================== Create a new person and him timeoffs =====================
		Person p1 = new Person("David", "Zapatero", "Garcia", "15555555E", "dzap@gmail.com", d1, "");
		personService.createPerson(p1);
		
		Person p2 = new Person("Federico", "Lopez", "Garcia", "25555555E", "fed@gmail.com", d1, "");
		personService.createPerson(p2);
		
		TimeOff t1 = new TimeOff(p1, d2, d3, "Fiebre");
		TimeOff t2 = new TimeOff(p1, d4, null, "Catarro");
		TimeOff t3 = new TimeOff(p2, d5, null, "Virus");
		
		personService.createTimeOff(t1);
		personService.createTimeOff(t2);
		personService.createTimeOff(t3);
		
		// ========================== Find the created timeoffs ===========================
		TimeOff t4 = personService.findTimeOff(t1.getId());
		assertEquals(t1, t4);
		
		t4 = personService.findTimeOff(t2.getId());
		assertEquals(t2, t4);
		
		List<TimeOff> timeoffs = personService.findAllTimeOffs();
		assertEquals(timeoffs.size(), 3);
		assertEquals(timeoffs.get(0), t1);
		assertEquals(timeoffs.get(1), t2);
		assertEquals(timeoffs.get(2), t3);
		
		timeoffs = personService.findTimeOffByPerson(p1);
		assertEquals(timeoffs.size(), 2);
		assertEquals(timeoffs.get(0), t1);
		assertEquals(timeoffs.get(1), t2);
		
		// ====================== Create a timeoff with invalid data ======================
		TimeOff t5 = new TimeOff(p1, fmt.parse("2006-05-20"), null, "Fiebre"); // iniTimeOff < hiredate
		TimeOff t6 = new TimeOff(p1, d2, d1, "Fiebre"); // endTimeOff < iniTimeOff
		
		try {
			error=false;
			personService.createTimeOff(t5);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		try {
			error=false;
			personService.createTimeOff(t6);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);	
	}
	

	private void updateTimeOff() throws InstanceNotFoundException, ParseException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date d4 = fmt.parse("2016-04-28");
		boolean error;
		
		// =============================== Correct update ===============================
		TimeOff t1 = personService.findAllTimeOffs().get(1);
		t1.setEnd(d4);
		
		personService.updateTimeOff(t1);
		
		// ============================== Incorrect update ==============================
		t1 = personService.findAllTimeOffs().get(0);
		t1.setIni(d4);

		try {
			error=false;
			personService.updateTimeOff(t1);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);	
	}
	
	private void removeTimeOff() throws InstanceNotFoundException {
		
		boolean error;
		
		// ================================ Correct remove ================================
		TimeOff t1 = personService.findAllTimeOffs().get(2);
		Long id = t1.getId();
		
		personService.removeTimeOff(id);
		
		// ========================= Remove a nonexistent TimeOff =========================
		try {
			error=false;
			personService.removeTimeOff(id);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
		
		// ============= If we delete a Person, him TimeOffs also are deleted =============
		List<Person> persons = personService.findAllPersons();
		for (Person p : persons) {
			personService.removePerson(p.getId());
		}
		
		List <TimeOff> timeoffs = personService.findAllTimeOffs();
		assertEquals(timeoffs.size(), 0);		
	}

}
