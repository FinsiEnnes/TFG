package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.rs.app.client.conversor.PersonDTOConversor;
import es.udc.rs.app.client.dto.PersonDTO;
import es.udc.rs.app.client.util.ClientConstants;
import es.udc.rs.app.exceptions.FirstPageElementException;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;

@Controller
public class PersonController {

	@Autowired 
	private PersonService personService;
    
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons || Show the registered persons in pages of 5 entries.   
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons", method=RequestMethod.GET)
    public String personTable 
    	(@RequestParam(value="page", required=false, defaultValue="1") int pageNumber, Model model) throws FirstPageElementException {

    	// Initialization of variables
    	List<PersonDTO> personsDTO = new ArrayList<PersonDTO>();
    	int totalItems = 0, totalPages = 0, previousPage = 1, nextPage = 1;
    	String nextActive = "", previousActive = "";

    	// First we are going to get the Persons belong to this page number
    	List<Person> persons = new ArrayList<Person>();
    	persons = personService.findAllPersons(pageNumber, ClientConstants.PAGE_SIZE);
    	
    	// Check if the list is empty
    	if (!persons.isEmpty()) {
	    	
    		// Convert to DTO
    		personsDTO = PersonDTOConversor.toPersonDTOs(persons);
    		
    		// Get the number of total pages
    		totalItems = personService.getTotalPersons();
	    	totalPages = (int) Math.ceil(totalItems/ClientConstants.PAGE_SIZE);
	    	
	    	if ((totalItems % ClientConstants.PAGE_SIZE) > 0) {
	    		totalPages++;
	    	}
	    	
	    	// We set the previous and next page
	    	previousPage = (pageNumber == 1) ? 1 : (pageNumber - 1);
	    	nextPage = (pageNumber == totalPages) ? totalPages : (pageNumber + 1);
	    			
	    	// If it is the first or last page, we will disable the button previous or next
	    	previousActive = (pageNumber == 1) ? "disabled" : "";
	    	nextActive = (pageNumber == totalPages) ? "disabled" : "";
    	}
	    	
    	// Now create the model
    	model.addAttribute("persons", personsDTO);
    	model.addAttribute("pageNumber", pageNumber);
    	model.addAttribute("totalPage", totalPages);
    	model.addAttribute("previousActive", previousActive);
    	model.addAttribute("nextActive", nextActive);
    	model.addAttribute("previousPage", previousPage);
    	model.addAttribute("nextPage", nextPage);
    	model.addAttribute("action", "");
    	
    	// Return the name of the view
        return "personTable";
    }
    
    
    //-----------------------------------------------------------------------------------------------------
    // [POST]-> /persons || Addition of a new Person.   
    //-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons", method=RequestMethod.POST)
    public String addPerson(@Valid @ModelAttribute("person")PersonDTO personDto, 
    	      BindingResult result, Model model, HttpServletRequest request) {
    	
    	model.addAttribute("idPerson", request.getParameter("hiredate"));
    	model.addAttribute("action", "correctCreation");
    	return "personTable";
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons/id || Show the person information included the aptitudes and times off.   
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}",  method=RequestMethod.GET)
    public String personInfo(@PathVariable String idPerson, Model model) throws InstanceNotFoundException {
    	
    	// Get the Person and convert it in PersonDTO
    	Long idPersonLong = Long.parseLong(idPerson, 10);
    	Person person = personService.findPerson(idPersonLong);
    	PersonDTO personDTO = PersonDTOConversor.toPersonDTO(person);
    	
    	
    	List<Aptitude> aptitudes = personService.findAptitudeByPerson(person);
    	
    	model.addAttribute("person", personDTO);
    	model.addAttribute("aptitudes", aptitudes);
    	
    	model.addAttribute("section1State", "active");
    	model.addAttribute("section2State", "");
    	model.addAttribute("section3State", "");
    	
    	return "personInfo";
    }
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id || Add new features at the Person, such as aptitudes or times off.   
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}", method=RequestMethod.POST)
    public String addAptitude(@PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Find the Person
    	Long idPersonLong = Long.parseLong(idPerson, 10);
    	Person person = personService.findPerson(idPersonLong);
    	
    	// Create the Aptitude
    	String nameApt = request.getParameter("nameAptitude");
    	AptitudeType typeApt = personService.findAptitudeType("ART");
    	Integer valueApt = Integer.parseInt(request.getParameter("valueAptitude"));
    	
    	Aptitude apt = new Aptitude(person, nameApt, typeApt, valueApt, "");
    	
    	// Add the aptitude to the Person
    	personService.createAptitude(apt);
    	
    	List<Aptitude> aptitudes = personService.findAptitudeByPerson(person);
    	
    	model.addAttribute("person", person);
    	model.addAttribute("aptitudes", aptitudes);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "active");
    	model.addAttribute("section3State", "");
    	
    	return "personInfo";
    }
}
