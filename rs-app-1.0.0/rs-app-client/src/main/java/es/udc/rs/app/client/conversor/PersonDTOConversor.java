package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.PersonDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.model.domain.Person;

public class PersonDTOConversor {
	
	public static List<PersonDTO> toPersonDTOList(List<Person> persons) {
		
		List<PersonDTO> personsDTO = new ArrayList<PersonDTO>();
		
		for (Person p : persons) {
			personsDTO.add(toPersonDTO(p));
		}
		return personsDTO;
	}
	
	public static PersonDTO toPersonDTO(Person person) {
		
		PersonDTO personDto = new PersonDTO();
		
		personDto.setId(person.getId());
		personDto.setName(person.getName());
		personDto.setSurname1(person.getSurname1());
		personDto.setSurname2(person.getSurname2());
		personDto.setNif(person.getNif());
		personDto.setEmail(person.getEmail());
		personDto.setHiredate(ClientUtilMethods.convertDateToString(person.getHiredate()));
		personDto.setComment(person.getComment());
		
		return personDto;
	}
	
	public static Person toPerson(PersonDTO personDto) {
		
		Person person = new Person();
		
		person.setId(personDto.getId());
		person.setName(personDto.getName());
		person.setSurname1(personDto.getSurname1());
		person.setSurname2(personDto.getSurname2());
		person.setNif(personDto.getNif());
		person.setEmail(personDto.getEmail());
		person.setHiredate(ClientUtilMethods.toDate(personDto.getHiredate()));
		person.setComment(personDto.getComment());
		
		return person;
	}
}
