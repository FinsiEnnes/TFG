package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.FreeDayDTO;
import es.udc.rs.app.client.util.ClientUtilMethods;
import es.udc.rs.app.model.domain.FreeDay;

public class FreeDayDTOConversor {
	
	public static List<FreeDayDTO> toFreeDayDTOList(List<FreeDay> freeDayList) {
		
		List<FreeDayDTO> freeDayDTOList = new ArrayList<FreeDayDTO>();
		
		for (FreeDay f : freeDayList) {
			freeDayDTOList.add(toFreeDayDTO(f));
		}
		return freeDayDTOList;
	}
	
	
	private static String getWeekDay(Integer weekDayN) {
		
		String weekDay = "Lunes";
		
		if (weekDayN == 1) {
			weekDay = "Martes";
		} else if (weekDayN == 2) {
			weekDay = "Miércoles";
		} else if (weekDayN == 3) {
			weekDay = "Jueves";
		} else if (weekDayN == 4) {
			weekDay = "Viernes";
		} else if (weekDayN == 5) {
			weekDay = "Sábado";
		} else if (weekDayN == 6) {
			weekDay = "Sábado";
		} 
		
		return weekDay;
	}
	
	public static FreeDayDTO toFreeDayDTO(FreeDay freeDay) {
		
		FreeDayDTO freeDayDTO = new FreeDayDTO();
		
		freeDayDTO.setId(freeDay.getId());
		freeDayDTO.setName(freeDay.getName());
		freeDayDTO.setWeekDay(freeDay.getWeekDay());
		freeDayDTO.setWeekDayString(getWeekDay(freeDay.getWeekDay()));
		freeDayDTO.setIni(ClientUtilMethods.convertDateToString(freeDay.getIni()));
		freeDayDTO.setEnd(ClientUtilMethods.convertDateToString(freeDay.getEnd()));
		
		return freeDayDTO;
	}
	
	
	public static FreeDay toFreeDay(FreeDayDTO freeDayDTO) {
		
		FreeDay freeDay = new FreeDay();
		
		freeDay.setId(freeDayDTO.getId());
		freeDay.setName(freeDayDTO.getName());
		freeDay.setWeekDay(freeDayDTO.getWeekDay());
		freeDay.setIni(ClientUtilMethods.toDate(freeDayDTO.getIni()));
		freeDay.setEnd(ClientUtilMethods.toDate(freeDayDTO.getEnd()));
		
		return freeDay;
	}

}
