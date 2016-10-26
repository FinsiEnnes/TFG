package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.AptitudeDTO;
import es.udc.rs.app.client.dto.PersonDTO;
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
	
	public static List<AptitudeDTO> toAptitudeDTOList(List<Aptitude> aptitudes) {
		
		List<AptitudeDTO> aptitudesDTO = new ArrayList<AptitudeDTO>();
		
		for (Aptitude apt : aptitudes) {
			aptitudesDTO.add(toAptitudeDTO(apt));
		}
		return aptitudesDTO;
	}
	
	
	public static AptitudeDTO toAptitudeDTO(Aptitude aptitude) {
		
		AptitudeDTO aptitudeDto = new AptitudeDTO();
		
		aptitudeDto.setId(aptitude.getId());
		aptitudeDto.setIdPerson(aptitude.getPerson().getId());
		aptitudeDto.setName(aptitude.getName());
		aptitudeDto.setType(aptitude.getType().getName());
		aptitudeDto.setValue(aptitude.getValue());
		aptitudeDto.setComment(aptitude.getComment());
		
		return aptitudeDto;
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
