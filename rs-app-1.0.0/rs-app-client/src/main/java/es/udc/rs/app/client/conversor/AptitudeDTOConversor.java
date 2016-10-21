package es.udc.rs.app.client.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.AptitudeDTO;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AptitudeType;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.service.person.PersonService;

@Component
public class AptitudeDTOConversor {
	
	private static PersonService personService;
	
	@Autowired
    public AptitudeDTOConversor(PersonService personService) {
		AptitudeDTOConversor.personService = personService;
    }
	
	private static String getAptitudeTypeId(String aptitudeName) {
		
		String aptitudeTypeId = "CIE";
		
		if (aptitudeName.equals("Cientifica")) { aptitudeTypeId="CIE"; }
		else if (aptitudeName.equals("Artistica")){ aptitudeTypeId="ART"; }
		else if (aptitudeName.equals("Espacial")){ aptitudeTypeId="ESP"; }
		else if (aptitudeName.equals("Numerica")){ aptitudeTypeId="NUM"; }
		else if (aptitudeName.equals("Mecanica")){ aptitudeTypeId="MEC"; }
		else if (aptitudeName.equals("Social")){ aptitudeTypeId="SOC"; }
		else if (aptitudeName.equals("Directiva")){ aptitudeTypeId="DIR"; }
		else if (aptitudeName.equals("Organizativa")){ aptitudeTypeId="ORG"; }

		return aptitudeTypeId;
	}
	
	public static Aptitude toAptitude(AptitudeDTO aptitudeDto) throws InstanceNotFoundException {
		
		Aptitude aptitude = new Aptitude();
		
		// Get the Person
		Person person = personService.findPerson(aptitudeDto.getIdPerson());
		
		// Get the AptitudeType
		AptitudeType aptitudeType = personService.findAptitudeType(getAptitudeTypeId(aptitudeDto.getType()));
		
		// Now we can create the Aptitude
		aptitude.setPerson(person);
		aptitude.setName(aptitudeDto.getName());
		aptitude.setType(aptitudeType);
		aptitude.setValue(aptitudeDto.getValue());
		aptitude.setComment(aptitudeDto.getComment());
		
		return aptitude;
	}

}
