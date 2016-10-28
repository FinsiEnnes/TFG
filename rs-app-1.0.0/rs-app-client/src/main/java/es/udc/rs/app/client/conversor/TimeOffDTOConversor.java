package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.udc.rs.app.client.dto.TimeOffDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.service.person.PersonService;

@Component
public class TimeOffDTOConversor {

	private static PersonService personService;
	
	@Autowired
    public TimeOffDTOConversor(PersonService personService) {
		TimeOffDTOConversor.personService = personService;
    }
	
	
	public static List<TimeOffDTO> toTimeOffDTOList(List<TimeOff> timeOffs) {
		
		List<TimeOffDTO> timeOffsDTO = new ArrayList<TimeOffDTO>();
		
		for (TimeOff timeoff : timeOffs) {
			timeOffsDTO.add(toTimeOffDTO(timeoff));
		}
		return timeOffsDTO;
	}
	
	public static TimeOffDTO toTimeOffDTO(TimeOff timeOff) {
		
		TimeOffDTO timeOffDTO = new TimeOffDTO();
		
		timeOffDTO.setId(timeOff.getId());
		timeOffDTO.setIdPerson(timeOff.getPerson().getId());
		timeOffDTO.setIni(ClientUtilMethods.convertDateToString(timeOff.getIni()));
		timeOffDTO.setEnd(ClientUtilMethods.convertDateToString(timeOff.getEnd()));
		timeOffDTO.setReason(timeOff.getReason());
		
		return timeOffDTO;
	}
	
	
	public static TimeOff toTimeOff(TimeOffDTO timeOffDTO) throws InstanceNotFoundException {
		
		TimeOff timeOff = new TimeOff();
		
		// Get the object Person
		Person person = personService.findPerson(timeOffDTO.getIdPerson());
		
		timeOff.setId(timeOffDTO.getId());
		timeOff.setPerson(person);
		timeOff.setIni(ClientUtilMethods.toDate(timeOffDTO.getIni()));
		timeOff.setEnd(ClientUtilMethods.toDate(timeOffDTO.getEnd()));
		timeOff.setReason(timeOffDTO.getReason());
		
		return timeOff;
	}
}
