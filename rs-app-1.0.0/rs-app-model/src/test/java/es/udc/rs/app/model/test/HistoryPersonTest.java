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
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.test.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class HistoryPersonTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private PersonService personService; 
	
	@Autowired
	private TestUtils testUtils;
	
	@Before
	public void setUp() throws Exception { 
		log.info("");
		log.info ("========== Starting HistoryPerson Test ==========");
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
	public void fullHistoryPersonTest () throws ParseException, InputValidationException, InstanceNotFoundException {
		createAndFindHistoryPerson();
		updateHistoryPerson();
		removeHistoryPerson();
	}
	
	private void createAndFindHistoryPerson() 
			throws ParseException, InputValidationException, InstanceNotFoundException {
		
		boolean error;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		// ================================ Correct create ================================
		HistoryPerson hp1 = new HistoryPerson(testUtils.p1, testUtils.pc1, 
				fmt.parse("2013-05-06"), fmt.parse("2016-04-06"), 5, 6, null);
		
		HistoryPerson hp2 = new HistoryPerson(testUtils.p2, testUtils.pc3, 
				fmt.parse("2012-01-10"), null, 6, 7, null);
		
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
		
		hps = personService.findHistoryPersonByPerson(testUtils.p1);
		numHistoriesPerson = hps.size();
		assertEquals(hps.get(numHistoriesPerson-1), hp1);
		
		hps = personService.findHistoryPersonByProfCatg(testUtils.pc3);
		numHistoriesPerson = hps.size();
		assertEquals(hps.get(numHistoriesPerson-1), hp2);
		
		// ============================== Incorrect creates ===============================
		HistoryPerson incorrectHp = new HistoryPerson(testUtils.p1, testUtils.pc1, 
				fmt.parse("2010-05-06"), null, 5, 6, null); //--> hiredatePerson > iniHPerson 
		
		try {
			error=false;
			personService.createHistoryPerson(incorrectHp);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		incorrectHp = new HistoryPerson(testUtils.p1, testUtils.pc1, 
				fmt.parse("2014-05-06"), fmt.parse("2015-04-06"), 5, 6, null); //--> Repeated profile.
		
		try {
			error=false;
			personService.createHistoryPerson(incorrectHp);
		} catch (GenericJDBCException e){
			error=true;
		}
		assertTrue(error);
		
		incorrectHp = new HistoryPerson(testUtils.p3, testUtils.pc1, 
				fmt.parse("2014-05-06"), fmt.parse("2015-04-06"), -5, 6, null);
		
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
		List<HistoryPerson> hps = personService.findHistoryPersonByProfCatg(testUtils.pc3);
		int numHistoriesPerson = hps.size();
		
		HistoryPerson hp = hps.get(numHistoriesPerson-1);
		hp.setEnd(fmt.parse("2014-01-10"));
		
		personService.updateHistoryPerson(hp);
		
		// ============================== Incorrect update ==============================
		hps = personService.findHistoryPersonByPerson(testUtils.p1);
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
	}

}
