package es.udc.rs.app.model.test;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
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
		

		
	}
	
	private void createAndFindHistoryPerson () {
		
	}

}
