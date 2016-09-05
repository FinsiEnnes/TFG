package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.project.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class FullTest {
	
	private Logger log = Logger.getLogger("project");

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CustomerService customerService;

	@Test
	public void fullTest() throws InstanceNotFoundException, InputValidationException, ParseException {

		log.info("");
		log.info ("============== Starting Full Test ==============");
		thisTest();	
	}

	private void thisTest() throws InstanceNotFoundException, InputValidationException, ParseException {

	
		
	}

}
