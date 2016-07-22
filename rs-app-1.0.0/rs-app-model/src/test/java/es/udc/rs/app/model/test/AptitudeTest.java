package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class AptitudeTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private PersonService personService; 
	
	@Test
	public void fullAptitudeTest () throws ParseException, InputValidationException, InstanceNotFoundException {
		
		log.info("");
		log.info ("========== Starting Aptitude Test ==========");
		findAptitudeTypes();
		createAndFindAptitude();
		updateAptitude();
		removeAptitude();
		
	}

	private void findAptitudeTypes() {
		
		AptitudeType at1 = new AptitudeType("ART", "Artistica");
		AptitudeType at2 = new AptitudeType("CIE", "Cientifica");
		AptitudeType at3 = new AptitudeType("DIR", "Directiva");
		AptitudeType at4 = new AptitudeType("ESP", "Espacial");
		AptitudeType at5 = new AptitudeType("MEC", "Mecanica");

		List<AptitudeType> aptitudeTypes = personService.findAllAptitudeTypes();
		
		// We check the first five aptitudeType of the db.
		assertEquals(aptitudeTypes.size(), 8);
		assertEquals(aptitudeTypes.get(0), at1);
		assertEquals(aptitudeTypes.get(1), at2);
		assertEquals(aptitudeTypes.get(2), at3);
		assertEquals(aptitudeTypes.get(3), at4);
		assertEquals(aptitudeTypes.get(4), at5);
	}
	
	private void createAndFindAptitude() throws ParseException, 
	InputValidationException, InstanceNotFoundException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = fmt.parse("2014-06-06");
		boolean error;
		
		// ===================== Create a new person and him aptitudes ====================
		Person p1 = new Person("David", "Zapatero", "Garcia", "15555555E", "dzap@gmail.com", d1, "");
		personService.createPerson(p1);
		
		Person p2 = new Person("Federico", "Lopez", "Garcia", "25555555E", "fed@gmail.com", d1, "");
		personService.createPerson(p2);
		
		AptitudeType at = personService.findAptitudeType("CIE");
		
		Aptitude a1 = new Aptitude(p1,"Dominio Java",at,8,"");
		Aptitude a2 = new Aptitude(p2,"Esp. Unity",at,7,"");	
		
		personService.createAptitude(a1);
		personService.createAptitude(a2);
		
		// ============================== Find the aptitudes ==============================
		Aptitude a3 = personService.findAptitude(a1.getId());
		assertEquals(a1, a3);
		
		a3 = personService.findAptitude(a2.getId());
		assertEquals(a2, a3);
		
		List<Aptitude> aptitudes = personService.findAllAptitudes();
		assertEquals(aptitudes.size(), 2);
		assertEquals(aptitudes.get(0), a1);
		assertEquals(aptitudes.get(1), a2);
		
		a3 = personService.findAptitudeByPerson(p1).get(0);
		assertEquals(a1, a3);
		
		// ============================== Incorrect insertion =============================
		a3 = new Aptitude(p2,"aptitude",personService.findAptitudeType("ESP"),12,""); 
		
		// valueApt>10
		try {
			error=false;
			personService.createAptitude(a3);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);
		
		Person nonexistentPerson = new Person(0L,"", "", "", "35555555E", "email@gmail.com", d1, ""); 
		a3 = new Aptitude(nonexistentPerson,"aptitude",at,8,""); 
		
		// Nonexistent Person.
		try {
			error=false;
			personService.createAptitude(a3);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
		
		// Repeated Aptitude
		Aptitude a4 = new Aptitude(p1,"Dominio Java",at,8,"");

		try {
			error=false;
			personService.createAptitude(a4);
		} catch (ConstraintViolationException e){
			error=true;
		}
		assertTrue(error);
		
		
		
	}
	
	
	private void updateAptitude() throws InstanceNotFoundException, InputValidationException {
		
		// =============================== Correct update ===============================
		Aptitude a = personService.findAllAptitudes().get(0);
		AptitudeType at = personService.findAptitudeType("ESP");
		
		a.setName("Especialista Java"); a.setType(at);
		
		personService.updateAptitude(a);	
	}
	
	private void removeAptitude() throws InstanceNotFoundException {
		
		boolean error;
		
		// ================================ Correct remove ================================
		Aptitude a = personService.findAllAptitudes().get(0);
		Long id = a.getId();
		
		personService.removeAptitude(id);
		
		// ========================= Remove a nonexistent TimeOff =========================
		try {
			error=false;
			personService.removeAptitude(id);
		} catch (InstanceNotFoundException e){
			error=true;
		}
		assertTrue(error);
		
		// ============= If we delete a Person, him Aptitudes also are deleted =============
		List<Person> persons = personService.findAllPersons();
		for (Person p : persons) {
			personService.removePerson(p.getId());
		}
		
		List <Aptitude> aptitudes = personService.findAllAptitudes();
		assertEquals(aptitudes.size(), 0);	
	}

}
