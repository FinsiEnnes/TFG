package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
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
import es.udc.rs.app.client.conversor.TimeOffDTOConversor;
import es.udc.rs.app.client.dto.AptitudeDTO;
import es.udc.rs.app.client.dto.PersonDTO;
import es.udc.rs.app.client.dto.TimeOffDTO;
import es.udc.rs.app.client.util.ClientConstants;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.PaginationException;
import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.service.person.PersonService;

@Controller
public class PersonController {

	static Logger log = Logger.getLogger("project");
	
	@Autowired 
	private PersonService personService;
    
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons?keyword=&search-term=id || Person search by id.   
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
    	 Model model) throws PaginationException, InputValidationException, 
    	 					 NumberFormatException {
    	
    	// Initialization of variables
    	List<PersonDTO> personsDTO = new ArrayList<PersonDTO>();
    	List<Person> persons = new ArrayList<Person>();
    	int totalPages = 0, previousPage = 1, nextPage = 1;
    	String nextActive = "", previousActive = "", action="", msg="";
    	
    	log.info("Page:" + pageNumber + " || Search-term:" + searchTerm + " || Keyword:" + keyword);

    	// Get the table content in function of the parameters
    	// ...
    	// Request -> /persons?keyword=X&search-term=Y
    	if (searchTerm!=null) {
    		if (searchTerm.equals("ID")) 	 { 
    			try {
					persons = findPersonByID(Long.parseLong(keyword, 10));
				} catch (InstanceNotFoundException e) {
					action="correctCreation";
					msg="Error: No existe una persona con este id";
				} }
    		if (searchTerm.equals("nombre")) { persons = findPersonByName(keyword); }
    		if (searchTerm.equals("DNI")) 	 { try {
				persons = findPersonByNif(keyword);
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }    		
    	} 
    	// Request -> /persons or /persons?page=X
    	else { 
    		pageNumber = (pageNumber==null) ? 1 : pageNumber;
    		persons = personService.findAllPersons(pageNumber, ClientConstants.PAGE_SIZE);
    	} 
    	    	
    	// Check if the list is empty
    	if (!persons.isEmpty()) {
	    	
    		// Convert to DTO
    		personsDTO = PersonDTOConversor.toPersonDTOList(persons);
    		
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
    	model.addAttribute("action", action);
    	model.addAttribute("msg", msg);
    	
    	// Return the name of the view
        return "person/personTable";
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
    	
    	// Get the corresponding aptitudes
    	List<Aptitude> aptitudes = personService.findAptitudeByPerson(person);
    	List<AptitudeDTO> aptitudeDTOList = AptitudeDTOConversor.toAptitudeDTOList(aptitudes);
    	
    	// Now the time offs
    	List<TimeOff> timeOffs = personService.findTimeOffByPerson(person);
    	List<TimeOffDTO> timeOffDTOList = TimeOffDTOConversor.toTimeOffDTOList(timeOffs);
    	
    	model.addAttribute("person", personDTO);
    	model.addAttribute("aptitudes", aptitudeDTOList);
    	model.addAttribute("timeoffs", timeOffDTOList);
    	
    	model.addAttribute("section1State", "active");
    	model.addAttribute("section2State", "");
    	model.addAttribute("section3State", "");
    	
    	return "person/personInfo";
    }
    
    
    //-----------------------------------------------------------------------------------------------------
    // [POST]-> /persons || Addition of a new Person.   
    //-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons", method=RequestMethod.POST)
    public String addPerson(@Valid @ModelAttribute("person")PersonDTO personDTO, 
    	      BindingResult result, Model model, HttpServletRequest request) throws InputValidationException, PaginationException, NumberFormatException, InstanceNotFoundException {
    	
    	// Get the PersonDTO
    	String hiredate = request.getParameter("hiredate");
    	personDTO.setHiredate(hiredate);
    	
    	// Convert the PersonDTO to Person
    	Person person = PersonDTOConversor.toPerson(personDTO);
    	
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
    	return "person/personTable";
    }
    
    
    //-----------------------------------------------------------------------------------------------------
    // [POST]-> /persons/id/update || Update a Person.   
    //-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/update", method=RequestMethod.POST)
    public String updatePerson(@Valid @ModelAttribute("person")PersonDTO personDto, BindingResult result,
    						   @PathVariable String idPerson, Model model, HttpServletRequest request) 
    							throws InstanceNotFoundException, InputValidationException {
    
    	// Get the PersonDTO
    	String hiredate = request.getParameter("hiredate");
    	personDto.setId(Long.parseLong(idPerson, 10));
    	personDto.setHiredate(hiredate);
    	
    	// Convert the PersonDTO to Person
    	Person person = PersonDTOConversor.toPerson(personDto);
    	
    	// Update the Person
    	personService.updatePerson(person);
    	
    	// If the operation is correct, the view maintains
    	return personInfo(idPerson, model);
    	
    }
    
    //-----------------------------------------------------------------------------------------------------
    // [POST]-> /persons/id/delete || Delete a Person.   
    //-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/delete", method=RequestMethod.POST)
    public String deletePerson(@PathVariable String idPerson, Model model) throws InstanceNotFoundException, NumberFormatException, PaginationException, InputValidationException {
    	
    	Long id = Long.parseLong(idPerson, 10);
    	
    	// Call the service to delete the person
    	personService.removePerson(id);
    	
    	// Create the model     	
    	personTable(1,null,null,model);
    	model.addAttribute("idPerson", idPerson);
    	model.addAttribute("action", "");
    	
    	// Return the table in the first page 
    	return "person/personTable";
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/aptitude || Add new aptitudes at the Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/aptitude", method=RequestMethod.POST)
    public String addAptitude(@Valid @ModelAttribute("aptitude")AptitudeDTO aptitudeDTO, 
    		 BindingResult result, @PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Convert the AptitudeDTO to Aptitude
    	Aptitude aptitude = AptitudeDTOConversor.toAptitude(aptitudeDTO);
    	
    	// Add the aptitude to the Person
    	personService.createAptitude(aptitude);
    	
    	// Get all the data of this Person
    	personInfo(idPerson, model);
    	
    	// The section2 is able
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "active");
    	model.addAttribute("section3State", "");
    	
    	return "person/personInfo";
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/aptitude/update || Update the Aptitude of a Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/aptitude/update", method=RequestMethod.POST)
    public String updateAptitude(@Valid @ModelAttribute("aptitude")AptitudeDTO aptitudeDTO, 
    		 BindingResult result, @PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Convert the AptitudeDTO to Aptitude
    	Aptitude aptitude = AptitudeDTOConversor.toAptitude(aptitudeDTO);
    	
    	// Update the aptitude to the Person
    	personService.updateAptitude(aptitude);
    	
    	// Get all the data of this Person
    	personInfo(idPerson, model);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "active");
    	model.addAttribute("section3State", "");
    	
    	return "person/personInfo";
    }
    
   
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/aptitude/delete || Delete aptitudes at the Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/aptitude/delete", method=RequestMethod.POST)
    public String deleteAptitude(@PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Get the aptitudes id to delete these
    	String ids = request.getParameter("ids");
    	String[] splited = ids.split("-");
		int lengthSplit = splited.length;

		// Now call the service to delete the aptitudes
		for(int i=0; i<lengthSplit; i++){
			personService.removeAptitude(Long.parseLong(splited[i],10));
       }
    	
    	// Get all the data of this Person
    	personInfo(idPerson, model);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "active");
    	model.addAttribute("section3State", "");
    	
    	return "person/personInfo";
    }
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/timeoff || Add new TimeOffs at the Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/timeoff", method=RequestMethod.POST)
    public String addTimeOff(@Valid @ModelAttribute("timeoff")TimeOffDTO timeOffDTO, 
    		 BindingResult result, @PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Convert the TimeOffDTO to TimeOff
    	TimeOff timeOff = TimeOffDTOConversor.toTimeOff(timeOffDTO);
    	
    	// Add the TimeOff to the Person
    	personService.createTimeOff(timeOff);
    	
    	// Get all the data of this Person
    	personInfo(idPerson, model);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "");
    	model.addAttribute("section3State", "active");
    	
    	return "person/personInfo";
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/timeoff/update || Update the TimeOff of a Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/timeoff/update", method=RequestMethod.POST)
    public String updateTimeOff(@Valid @ModelAttribute("timeoff")TimeOffDTO timeOffDTO, 
    		 BindingResult result, @PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Convert the TimeOffDTO to TimeOff
    	TimeOff timeOff = TimeOffDTOConversor.toTimeOff(timeOffDTO);
    	
    	// Add the TimeOff to the Person
    	personService.updateTimeOff(timeOff);
    	
    	// Get all the data of this Person
    	personInfo(idPerson, model);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "");
    	model.addAttribute("section3State", "active");
    	
    	return "person/personInfo";
    }
    
    
	//-----------------------------------------------------------------------------------------------------
	// [POST]-> /persons/id/timeoff/delete || Delete the TimeOff of a Person.  
	//-----------------------------------------------------------------------------------------------------
    @RequestMapping(value="/persons/{idPerson}/timeoff/delete", method=RequestMethod.POST)
    public String deleteTimeOff(@Valid @ModelAttribute("timeoff")TimeOffDTO timeOffDTO, 
    		 BindingResult result, @PathVariable String idPerson, Model model, HttpServletRequest request) 
    		throws InstanceNotFoundException, InputValidationException  {
    	
    	// Get the aptitudes id to delete these
    	String ids = request.getParameter("ids");
    	String[] splited = ids.split("-");
		int lengthSplit = splited.length;

		// Now call the service to delete the aptitudes
		for(int i=0; i<lengthSplit; i++){
			personService.removeTimeOff(Long.parseLong(splited[i],10));
       }
    	
    	// Get all the data of this Person
    	personInfo(idPerson, model);
    	
    	model.addAttribute("section1State", "");
    	model.addAttribute("section2State", "");
    	model.addAttribute("section3State", "active");
    	
    	return "person/personInfo";
    }
    
    
//    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
//    @ExceptionHandler(InstanceNotFoundException.class)
//    public ModelAndView InstanceNotFound(HttpServletRequest req, InstanceNotFoundException e) {
//
//    	List<PersonDTO> personsDTO = new ArrayList<PersonDTO>();
//    	List<Person> persons = new ArrayList<Person>();
//    	int totalPages = 0, pageNumber = 0, nextPage = 1;
//    	String nextActive = "", previousActive = "";
//    	
//    	String uri = ClientUtilMethods.getFullURL(req);
//    	ModelAndView mav = new ModelAndView("personTable");
//
//	    try {
//			persons = personService.findAllPersons(1, ClientConstants.PAGE_SIZE);
//		} catch (FirstPageElementException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	    
//	
//	    // Check if the list is empty
//	    if (!persons.isEmpty()) {
//	
//	    	// Convert to DTO
//	    	personsDTO = PersonDTOConversor.toPersonDTOList(persons);
//	    	pageNumber = 1;
//
//    		// Get the number of total pages
//    		totalPages = ClientUtilMethods.getTotalPagesPerson(personService.getTotalPersons());
//	
//    		// We set the previous and next page
//    		nextPage = (pageNumber == totalPages) ? totalPages : (2);
//
//    		// If it is the first or last page, we will disable the button previous or next
//    		nextActive = (pageNumber == totalPages) ? "disabled" : "";
//    		
//	    } else {
//	    	totalPages = 0; pageNumber = 0;
//	    }
//	
//	    // Now create the model
//	    mav.addObject("persons", personsDTO);
//	    mav.addObject("pageNumber", 1);
//	    mav.addObject("totalPage", totalPages);
//	    mav.addObject("previousActive", "disabled");
//	    mav.addObject("nextActive", nextActive);
//	    mav.addObject("previousPage", 1);
//	    mav.addObject("nextPage", nextPage);
//	    mav.addObject("action", "correctCreation");
//	    mav.addObject("msg","Persona no encontrada");
//	
//	    return mav;
//
//    }
}
