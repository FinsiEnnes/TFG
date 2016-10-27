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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	
	public static String convertDateToString(Date indate) {

		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");

		try{
			dateString = sdfr.format( indate );
		}catch (Exception ex ){
			System.out.println(ex);
		}
		return dateString;
	}
	
	
	public static int getTotalPagesPerson(int totalItems) {
				
    	int totalPages = (int) Math.ceil(totalItems/ClientConstants.PAGE_SIZE);
    	
    	if ((totalItems % ClientConstants.PAGE_SIZE) > 0) {
    		totalPages++;
    	}
    	
    	return totalPages;
	}

}
