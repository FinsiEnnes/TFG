package es.udc.rs.app.model.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class PersonTest {
	
	private Logger log = Logger.getLogger("project");
 
	@Autowired
	private TestUtils testUtils;
	
	@Autowired
	private PersonService personService; 
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("=========== Starting Person Test ===========");
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
	public void fullPersonTest() throws InputValidationException, InstanceNotFoundException, ParseException {
		
		createAndFindPerson();
		updatePerson();
		removePerson();
	}
	
	private void createAndFindPerson() 
			throws InputValidationException, InstanceNotFoundException, ParseException {
		
		Person p1;
		Person p2;
		List<Person> persons; 
		boolean error;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		p1 = new Person("David", "Zapatero", "Garcia", "55555555E",
				"dzap@gmail.com", fmt.parse("2014-06-06"), "");
		personService.createPerson(p1);
		
		// ========================== Find by id, nif and by name =========================
		p2 = personService.findPerson(p1.getId());
		assertEquals(p1, p2);
		p2 = personService.findPersonByNif(p1.getNif());
		assertEquals(p1, p2);
		p2 = personService.findPersonsByName(p1.getName(), p1.getSurname1(), p1.getSurname2()).get(0);
		assertEquals(p1, p2);
		
		// ============================  Duplicate detection ==============================
		try {
			error=false;
			p2 = new Person("David", "Zapatero", "Garcia", "55555555E", 
					"davidz@gmail.com", fmt.parse("2014-06-06"), "");
			personService.createPerson(p2);
		} catch (ConstraintViolationException e){
			error=true;
		}
		assertTrue(error);
		
		// ============================  Name null detection ============================== 
		try {
			error=false;
			p2 = new Person(null, "Zapatero", "Garcia", "55555555E",
					"davidz@gmail.com",fmt.parse("2014-06-06"), "");
			personService.createPerson(p2);
		} catch (ConstraintViolationException e){
			error=true;
		}
		assertTrue(error);
		
		// ========================== Incorrect email detection ===========================
		try {
			error=false;
			p2 = new Person("David", "Zapatero", "Garcia", "55555555E",
					"davidzgmail.com", fmt.parse("2014-06-06"), "");
			personService.createPerson(p2);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);
		
		// =============================== Find all persons ===============================
		persons = personService.findAllPersons();
		assertEquals(persons.size(), 5);
		assertEquals(persons.get(0), testUtils.p1);
		assertEquals(persons.get(1), testUtils.p2);
		assertEquals(persons.get(2), testUtils.p3);
		assertEquals(persons.get(3), testUtils.p4);
		assertEquals(persons.get(4), p1);
		log.info ("Correct: Persons got correctly with findAllPersons.");
	}
	
	private void updatePerson() throws InputValidationException, InstanceNotFoundException, ParseException {
		
		Person p1;
		Person p2;
		boolean error;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		// Get the last Person
		Long id = personService.findPersonByNif("55555555E").getId();
		p1 = new Person(id, "Dave", "Shoer", "Opa", "55555555E", "dave@gmail.com", fmt.parse("2014-06-06"), "");
		
		// ================================ Update a Person ===============================
		personService.updatePerson(p1);
		p2 = personService.findPerson(p1.getId());
		assertEquals(p1, p2);
	
		// ====================== Update a Person with incorrect data =====================
		p1.setEmail("hotmail");
		try {
			error=false;
			personService.updatePerson(p1);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);
		
		// ========================== Update an unregistred Person ========================
		p1 = new Person(1L, "Dave", "Shoer", "Opa", "55555555E", "dave@gmail.com", fmt.parse("2014-06-06"), "");
		try {
			error=false;
			personService.updatePerson(p1);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
		
		// ========================== Update the nif: Not allowed =========================
		p1 = new Person(id, "Dave", "Shoer", "Opa", "55555555X", "dave@gmail.com", fmt.parse("2014-06-06"), "");
		try {
			error=false;
			personService.updatePerson(p1);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);
	}
	
	private void removePerson() throws InstanceNotFoundException, InputValidationException {
		
		boolean error;
		
		// ================================ Delete a Person ===============================
		Long id = personService.findPersonByNif("55555555E").getId();
		personService.removePerson(id);
		
		
		// ========================= Delete an unregistred Person =========================
		try {
			error=false;
			personService.removePerson(id);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
	}

}
