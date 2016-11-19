package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.HistoryPersonDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.model.domain.HistoryPerson;

public class HistoryPersonDTOConversor {
	
	public static List<HistoryPersonDTO> toHistoryPersonDTOs (List<HistoryPerson> hps) {
		
		List<HistoryPersonDTO> hpDTOs = new ArrayList<HistoryPersonDTO>();
		
		for (HistoryPerson hp: hps) {
			hpDTOs.add(toHistoryPersonDTO(hp));
		}
		return hpDTOs;
	}
	
	public static HistoryPersonDTO toHistoryPersonDTO(HistoryPerson hp) {
		
		HistoryPersonDTO hpDTO = new HistoryPersonDTO();
		String fullName = hp.getPerson().getName() + " " + hp.getPerson().getSurname1() + " " + 
						  hp.getPerson().getSurname2();
		
		hpDTO.setId(hp.getId());
		hpDTO.setIdPerson(hp.getPerson().getId());
		hpDTO.setNamePerson(fullName);
		hpDTO.setIdProfcatg(hp.getProfcatg().getId());
		hpDTO.setIni(ClientUtilMethods.convertDateToString(hp.getIni()));
		hpDTO.setEnd(ClientUtilMethods.convertDateToString(hp.getEnd()));
		hpDTO.setSal(hp.getSal());
		hpDTO.setSalExtra(hp.getSalExtra());
		hpDTO.setComment(hp.getComment());
		
		return hpDTO;
	}

}
