package es.udc.rs.app.client.util;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.rs.app.model.service.person.PersonService;

public class ClientUtilMethods {

	@Autowired 
	private static PersonService personService;
	
	
	public static Date toDate(String dateInString) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		
		if (dateInString != null && !dateInString.isEmpty()) {
			try {
				date = formatter.parse(dateInString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return date;
	}
	
	
	public static String convertDateToString(Date indate) {

		String dateString = ClientConstants.NOT_AVAILABLE;
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
	
	public static Date plusDay(Date date) {
		
		Date nextDay = new Date();
		
		// Add one day to this date
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		
		// Get the next day
		nextDay = c.getTime();
		
		return nextDay;
	}
	
	public static String getFullURL(HttpServletRequest req) {
	    
	    String contextPath = req.getContextPath();   // /mywebapp
	    String servletPath = req.getServletPath();   // /servlet/MyServlet
	    String pathInfo = req.getPathInfo();         // /a/b;c=123
	    String queryString = req.getQueryString();          // d=789

	    // Reconstruct original requesting URL
	    StringBuilder url = new StringBuilder();

	    url.append(contextPath).append(servletPath);

	    if (pathInfo != null) {
	        url.append(pathInfo);
	    }
	    if (queryString != null) {
	        url.append("?").append(queryString);
	    }
	    return url.toString();
	}

}
