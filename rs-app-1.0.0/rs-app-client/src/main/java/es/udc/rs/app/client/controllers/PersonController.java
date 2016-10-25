package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.rs.app.client.conversor.AptitudeDTOConversor;
import es.udc.rs.app.client.conversor.PersonDTOConversor;
import es.udc.rs.app.client.dto.AptitudeDTO;
import es.udc.rs.app.client.dto.PersonDTO;
import es.udc.rs.app.client.util.ClientConstants;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.FirstPageElementException;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;

@Controller
public class PersonController {

	static Logger log = Logger.getLogger("project");
	
	@Autowired 
	private PersonService personService;
    
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons?keyword=&search-term=id || Person search by name.   
	//-----------------------------------------------------------------------------------------------------
	private List<Person> findPersonByID(Long id) throws InstanceNotFoundException {
		
		// List with only a person
		List<Person> persons = new ArrayList<Person>();
		
		// Find the person by id and add to the list
		Person person = personService.findPerson(id);
		persons.add(person);
		
		return persons;
	}
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons?keyword=&search-term=name || People search by name.   
	//-----------------------------------------------------------------------------------------------------
	private List<Person> findPersonByName(String fullName) throws InputValidationException {
		
		List<Person> persons = new ArrayList<Person>();
		String[] splited = fullName.split("\\s+");
		int lengthSplit = splited.length;
		
		// Get the name and surnames separately
		String name = splited[0];
		String surname1 = (lengthSplit > 1) ? splited[1] : null;
		String surname2 = (lengthSplit > 2) ? splited[2] : null;
		
		// Find by name
		persons = personService.findPersonsByName(name, surname1, surname2);
		
		return persons;
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons?keyword=&search-term=nif || Person search by nif.   
	//-----------------------------------------------------------------------------------------------------
	private List<Person> findPersonByNif(String nif) throws InputValidationException, InstanceNotFoundException {
		
		// List with only a person
		List<Person> persons = new ArrayList<Person>();
		
		// Find the person by nif and add to the list
		Person person = personService.findPersonByNif(nif);
		persons.add(person);
		
		return persons;
	}
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons || Main method that deals with the request to /persons   
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons", method=RequestMethod.GET)
    public String personTable 
    	(@RequestParam(value="page", required=false) Integer pageNumber,
    	 @RequestParam(value="search-term", required=false) String searchTerm,
    	 @RequestParam(value="keyword", required=false) String keyword,
    	 Model model) throws FirstPageElementException, InputValidationException, 
    	 					 NumberFormatException, InstanceNotFoundException {
    	
    	// Initialization of variables
    	List<PersonDTO> personsDTO = new ArrayList<PersonDTO>();
    	List<Person> persons = new ArrayList<Person>();
    	int totalPages = 0, previousPage = 1, nextPage = 1;
    	String nextActive = "", previousActive = "";
    	
    	log.info("Page:" + pageNumber + " || Search-term:" + searchTerm + " || Keyword:" + keyword);

    	// Get the table content in function of the parameters
    	// ...
    	// Request -> /persons?keyword=X&search-term=Y
    	if (searchTerm!=null) {
    		if (searchTerm.equals("ID")) { persons = findPersonByID(Long.parseLong(keyword, 10)); }
    		if (searchTerm.equals("nombre")) { persons = findPersonByName(keyword); }
    		if (searchTerm.equals("DNI")) { persons = findPersonByNif(keyword); }    		
    	} 
    	// Request -> /persons or /persons?page=X
    	else { 
    		pageNumber = (pageNumber==null) ? 1 : pageNumber;
    		persons = personService.findAllPersons(pageNumber, ClientConstants.PAGE_SIZE);
    	} 
    	    	
    	// Check if the list is empty
    	if (!persons.isEmpty()) {
	    	
    		// Convert to DTO
    		personsDTO = PersonDTOConversor.toPersonDTOs(persons);
    		
    		// If is a full search, we need the next attributes
    		if (pageNumber!=null) {
	    		// Get the number of total pages
		    	totalPages = ClientUtilMethods.getTotalPagesPerson(personService.getTotalPersons());
		    	
		    	// We set the previous and next page
		    	previousPage = (pageNumber == 1) ? 1 : (pageNumber - 1);
		    	nextPage = (pageNumber == totalPages) ? totalPages : (pageNumber + 1);
		    			
		    	// If it is the first or last page, we will disable the button previous or next
		    	previousActive = (pageNumber == 1) ? "disabled" : "";
		    	nextActive = (pageNumber == totalPages) ? "disabled" : "";
    		}
    		else {
    			totalPages = 1; pageNumber = 1;
    			previousActive = "disabled"; nextActive = "disabled";
    		}
    	} else {
    		totalPages = 0; pageNumber = 0;
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
	// [GET]-> /persons/id || Show the person information included the aptitudes and times off.   
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}",  method=RequestMethod.GET)
    public String personInfo(@PathVariable String idPerson, Model model) throws InstanceNotFoundException {
    	
    	// Get the Person and convert it in PersonDTO
    	Long idPersonLong = Long.parseLong(idPerson, 10);
    	Person person = personService.findPerson(idPersonLong);
    	PersonDTO personDTO = PersonDTOConversor.toPersonDTO(person);
    	
    	// Get the corresponding aptitudes and times off
    	List<Aptitude> aptitudes = personService.findAptitudeByPerson(person);
    	
    	model.addAttribute("person", personDTO);
    	model.addAttribute("aptitudes", aptitudes);
    	
    	model.addAttribute("section1State", "active");
    	model.addAttribute("section2State", "");
    	model.addAttribute("section3State", "");
    	
    	return "personInfo";
    }
    
    
    //-----------------------------------------------------------------------------------------------------
    // [POST]-> /persons || Addition of a new Person.   
    //-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons", method=RequestMethod.POST)
    public String addPerson(@Valid @ModelAttribute("person")PersonDTO personDto, 
    	      BindingResult result, Model model, HttpServletRequest request) throws InputValidationException, FirstPageElementException, NumberFormatException, InstanceNotFoundException {
    	
    	// Get the PersonDTO
    	Date hiredate = ClientUtilMethods.toDate(request.getParameter("hiredate"));
    	personDto.setHiredate(hiredate);
    	
    	// Convert the PersonDTO to Person
    	Person person = PersonDTOConversor.toPerson(personDto);
    	
    	// Create the Person calling at the service
    	Long idPerson = personService.createPerson(person);
    	
    	// Get the last page
    	int lastPage = ClientUtilMethods.getTotalPagesPerson(personService.getTotalPersons());
    	
    	// Create a message with feedback for the user
    	String msg = "Persona creada correctamente. Identificador asignado: " + idPerson;
    	
    	// Create the model     	
    	personTable(lastPage,null,null,model);
    	model.addAttribute("action", "correctCreation");
    	model.addAttribute("msg",msg);
    	
    	// Return the table in the first page 
    	return "personTable";
    }
    
    //-----------------------------------------------------------------------------------------------------
    // [POST]-> /persons/id/delete || Delete of a Person.   
    //-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/delete", method=RequestMethod.POST)
    public String deletePerson(@PathVariable String idPerson, Model model) throws InstanceNotFoundException, NumberFormatException, FirstPageElementException, InputValidationException {
    	
    	Long id = Long.parseLong(idPerson, 10);
    	
    	// Call the service to delete the person
    	personService.removePerson(id);
    	
    	// Create the model     	
    	personTable(1,null,null,model);
    	model.addAttribute("idPerson", idPerson);
    	model.addAttribute("action", "correctCreation");
    	
    	// Return the table in the first page 
    	return "personTable";
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/aptitude || Add new aptitudes at the Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/aptitude", method=RequestMethod.POST)
    public String addAptitude(@Valid @ModelAttribute("aptitude")AptitudeDTO aptitudeDto, 
    		@PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Convert the AptitudeDTO to Aptitude
    	Aptitude aptitude = AptitudeDTOConversor.toAptitude(aptitudeDto);
    	
    	// Add the aptitude to the Person
    	personService.createAptitude(aptitude);
    	
    	// Get all the updated aptitudes of the Person
    	List<Aptitude> aptitudes = personService.findAptitudeByPerson(aptitude.getPerson());
    	
    	model.addAttribute("person", aptitude.getPerson());
    	model.addAttribute("aptitudes", aptitudes);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "active");
    	model.addAttribute("section3State", "");
    	
    	return "personInfo";
    }
}
