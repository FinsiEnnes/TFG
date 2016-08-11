package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.service.person.PersonService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class HistoryPersonTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private PersonService personService; 
	
	private Person p1;
	private Person p2;
	private Person p3;
	ProfessionalCategory pc1;
	ProfessionalCategory pc2;
	
	@Test
	public void fullHistoryPersonTest () throws ParseException, InputValidationException, InstanceNotFoundException {
		log.info("");
		log.info ("========= Starting HistoryPerson Test =========");
		createAndFindHistoryPerson();
		updateHistoryPerson();
		removeHistoryPerson();
	}
	
	private void createAndFindHistoryPerson() 
			throws ParseException, InputValidationException, InstanceNotFoundException {
		
		boolean error;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		p1 = new Person("Raul", "Glz","Costa","11111111A","costa@gmail.com", fmt.parse("2013-05-06"), "");
		p2 = new Person("Paco","Perez","Pois","22222222B","pperez@gmail.com", fmt.parse("2011-05-10"), "");
		p3 = new Person("Laura","Pois","Nada","33333333C","la.pois@gmail.com", fmt.parse("2012-01-08"), "");
		
		personService.createPerson(p1);
		personService.createPerson(p2);
		personService.createPerson(p3);
		
		LevelProfCatg jun = personService.findLevelProfCatg("JUN");
		pc1 = new ProfessionalCategory("Desarrollador SW", 0, jun, 5, null);
		pc2 = new ProfessionalCategory("Diseñador Personajes", 1, jun, 6, 7);

		personService.createProfessionalCategory(pc1);
		personService.createProfessionalCategory(pc2);

		
		// ================================ Correct create ================================
		HistoryPerson hp1 = new HistoryPerson(p1, pc1, fmt.parse("2013-05-06"), fmt.parse("2016-04-06"), 5, 6, null);
		
		HistoryPerson hp2 = new HistoryPerson(p2, pc2, fmt.parse("2012-01-10"), null, 6, 7, null);
		
		personService.createHistoryPerson(hp1);
		personService.createHistoryPerson(hp2);
		
		// ========================== Find of the created objects =========================
		HistoryPerson hp3 = personService.findHistoryPerson(hp1.getId());
		assertEquals(hp1, hp3);
		
		hp3 = personService.findHistoryPerson(hp2.getId());
		assertEquals(hp2, hp3);
		
		List<HistoryPerson> hps = personService.findAllHistoryPerson();
		int numHistoriesPerson = hps.size();

		assertEquals(hps.get(numHistoriesPerson-1), hp2);
		assertEquals(hps.get(numHistoriesPerson-2), hp1);
		
		hps = personService.findHistoryPersonByPerson(p1);
		numHistoriesPerson = hps.size();
		assertEquals(hps.get(numHistoriesPerson-1), hp1);
		
		hps = personService.findHistoryPersonByProfCatg(pc2);
		numHistoriesPerson = hps.size();
		assertEquals(hps.get(numHistoriesPerson-1), hp2);
		
		// ============================== Incorrect creates ===============================
		HistoryPerson incorrectHp = new HistoryPerson(p1, pc1, 
				fmt.parse("2010-05-06"), null, 5, 6, null); //--> hiredatePerson > iniHPerson 
		
		try {
			error=false;
			personService.createHistoryPerson(incorrectHp);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		incorrectHp = new HistoryPerson(p2, pc1, fmt.parse("2012-05-10"), null, 6, 7, null);
		// --> This Person currently has assigned a ProfessionalCategory
		try {
			error=false;
			personService.createHistoryPerson(incorrectHp);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		incorrectHp = new HistoryPerson(p1, pc2, 
				fmt.parse("2014-05-06"), fmt.parse("2015-04-06"), 5, 6, null); //--> Parallel dates.
		
		try {
			error=false;
			personService.createHistoryPerson(incorrectHp);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		incorrectHp = new HistoryPerson(p3, pc1, fmt.parse("2014-05-06"), fmt.parse("2015-04-06"), -5, 6, null);
		
		try {
			error=false;
			personService.createHistoryPerson(incorrectHp);
		} catch (InputValidationException e){
			error=true;
		}
		assertTrue(error);	
	}
	
	private void updateHistoryPerson() throws ParseException, InstanceNotFoundException {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		// =============================== Correct update ===============================
		List<HistoryPerson> hps = personService.findHistoryPersonByProfCatg(pc2);
		int numHistoriesPerson = hps.size();
		
		HistoryPerson hp = hps.get(numHistoriesPerson-1);
		hp.setEnd(fmt.parse("2014-01-10"));
		
		personService.updateHistoryPerson(hp);
		
		// ============================== Incorrect update ==============================
		hps = personService.findHistoryPersonByPerson(p1);
		numHistoriesPerson = hps.size();
		
		hp = hps.get(numHistoriesPerson-1);
		hp.setIni(fmt.parse("2014-01-10"));

		boolean error;
		try {
			error=false;
			personService.updateHistoryPerson(hp);
		} catch (DataAccessException e){
			error=true;
		}
		assertTrue(error);	
	}
	
	private void removeHistoryPerson() throws InstanceNotFoundException {
		
		List<HistoryPerson> hps = personService.findAllHistoryPerson();
		
		for (HistoryPerson hp : hps) {
			personService.removeHistoryPerson(hp.getId());
		}
		
		hps = personService.findAllHistoryPerson();
		assertEquals(hps.size(), 0);
		
		// ============================== Person ==============================
		List<Person> persons = personService.findAllPersons();

		for (Person p : persons) {
			personService.removePerson(p.getId());
		}

		// ====================== ProfessionalCategory =======================
		List<ProfessionalCategory> profiles = personService.findAllProfessionalCategories();

		for (ProfessionalCategory prof : profiles) {
			personService.removeProfessionalCategory(prof.getId());
		}
	}

}
