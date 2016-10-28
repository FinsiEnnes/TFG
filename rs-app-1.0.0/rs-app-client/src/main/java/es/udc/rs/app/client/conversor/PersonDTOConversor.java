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
		
		PersonDTO personDTO = new PersonDTO();
		
		personDTO.setId(person.getId());
		personDTO.setName(person.getName());
		personDTO.setSurname1(person.getSurname1());
		personDTO.setSurname2(person.getSurname2());
		personDTO.setNif(person.getNif());
		personDTO.setEmail(person.getEmail());
		personDTO.setHiredate(ClientUtilMethods.convertDateToString(person.getHiredate()));
		personDTO.setComment(person.getComment());
		
		return personDTO;
	}
	
	public static Person toPerson(PersonDTO personDTO) {
		
		Person person = new Person();
		
		person.setId(personDTO.getId());
		person.setName(personDTO.getName());
		person.setSurname1(personDTO.getSurname1());
		person.setSurname2(personDTO.getSurname2());
		person.setNif(personDTO.getNif());
		person.setEmail(personDTO.getEmail());
		person.setHiredate(ClientUtilMethods.toDate(personDTO.getHiredate()));
		person.setComment(personDTO.getComment());
		
		return person;
	}
}
