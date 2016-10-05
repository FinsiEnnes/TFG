package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.rs.app.client.util.ClientConstants;
import es.udc.rs.app.exceptions.FirstPageElementException;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;

@Controller
public class PersonController {

	@Autowired 
	private PersonService personService;
    
    @RequestMapping("/person")
    public String personTable 
    	(@RequestParam(value="page", required=false, defaultValue="1") int pageNumber, Model model) throws FirstPageElementException {

    	// First we are going to get the Persons belong to this page number
    	List<Person> persons = new ArrayList<Person>();
    	persons = personService.findAllPersons(pageNumber, ClientConstants.PAGE_SIZE);
    	
    	// Now create the model
    	model.addAttribute("persons", persons);
    	
    	// Return the name of the view
        return "personTable";
    }
}
