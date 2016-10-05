package es.udc.rs.app.client.testControllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.service.person.PersonService;

@Controller
public class TestController {

	static Logger log = Logger.getLogger("project");
	
	@Autowired 
	private PersonService personService;
    
    @RequestMapping("/searchPerson")
    public String greeting
    	(@RequestParam(value="id", required=false, defaultValue="1") String idString, Model model) 
    		throws InstanceNotFoundException, InputValidationException {
        
    	// We search the Person with id=1 
    	Person p = null; 
    	
    	Long id = Long.parseLong(idString, 10);

		p = personService.findPerson(id);

    	// Create the model
		model.addAttribute("msg", "Successful search by nif!");
    	model.addAttribute("name", p.getName());
    	model.addAttribute("id", p.getId());
    	model.addAttribute("nif", p.getNif());
    	model.addAttribute("email", p.getEmail());
        return "personView";
    }
    
    @RequestMapping(value = "/updatePersonNif", method = RequestMethod.GET)
    public String update() throws InputValidationException, InstanceNotFoundException {
    	
    	// Get the person
    	Person person = personService.findPersonByNif("54155363T");
    	
    	// Update the nif
    	person.setNif("55555555T");
        personService.updatePerson(person);

        return "personView";
    }
    
    
    @RequestMapping(value = "/incorrectTimeOff", method = RequestMethod.GET)
    public String incorrectTimeOff() throws InputValidationException, InstanceNotFoundException, ParseException {
    	
    	// Get the person
    	Person person = personService.findPersonByNif("54155363T");
    	
    	// Create a timeOff
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    	TimeOff timeOff = new TimeOff(person, fmt.parse("2006-05-20"), null, "Fiebre");
    	
	    //personService.createTimeOff(timeOff);

    	personService.createTimeOff(timeOff);
	
        return "personView";
    }
    
    
    @RequestMapping(value = "/gantt", method = RequestMethod.GET)
    public String gantt() {
	
        return "myGantt";
    }
}
