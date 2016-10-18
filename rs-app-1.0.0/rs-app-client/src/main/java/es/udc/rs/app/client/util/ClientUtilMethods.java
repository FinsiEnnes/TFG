package es.udc.rs.app.client.util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.rs.app.model.service.person.PersonService;

public class ClientUtilMethods {

	@Autowired 
	private static PersonService personService;
	
	
	public static Date toDate(String dateInString) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		
		try {

			date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	
	public static int getTotalPagesPerson(int totalItems) {
				
    	int totalPages = (int) Math.ceil(totalItems/ClientConstants.PAGE_SIZE);
    	
    	if ((totalItems % ClientConstants.PAGE_SIZE) > 0) {
    		totalPages++;
    	}
    	
    	return totalPages;
	}

}
