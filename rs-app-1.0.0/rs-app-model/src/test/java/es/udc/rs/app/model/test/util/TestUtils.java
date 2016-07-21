package es.udc.rs.app.model.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;

public class TestUtils {

	@Autowired
	private PersonService personService; 
	
	public Person p1;
	public Person p2;
	public Person p3;
	public Person p4;
	
	
	public void createDataSet() throws ParseException, InputValidationException {
		
		// ============================== Person ==============================
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		p1 = new Person("Raul", "Gonzalez","Costa","11111111A","costa@gmail.com", fmt.parse("2013-05-06"), "");
		p2 = new Person("Paco","Perez","Perales","22222222B","pperez@gmail.com", fmt.parse("2011-05-10"), "");
		p3 = new Person("Laura","Pois","Nada","33333333C","la.pois@gmail.com", fmt.parse("2012-01-08"), "");
		p4 = new Person("Maruja","Ramirez","Diez","44444444D","maruja@gmail.com", fmt.parse("2010-08-22"),"");
		
		personService.createPerson(p1);
		personService.createPerson(p2);
		personService.createPerson(p3);
		personService.createPerson(p4);	
		
		
	}
	
	
	public void deleteDataSet () throws InstanceNotFoundException {
		
		// ============================== Person ==============================
		List<Person> persons = personService.findAllPersons();
		
		for (Person p : persons) {
			personService.removePerson(p.getId());
		}
	}
	
}
