package es.udc.rs.app.others;

import es.udc.rs.app.exceptions.PaginationException;

public class AssistantMethods {
	
	public static int getFirstPageElement(int pageNumber, int pageSize, int totalElements) 
			throws PaginationException {
		
		int firstElement = 0;
		
		// Get the first element
		firstElement = (pageNumber - 1) * pageSize;
		
		// Check if it is correct
		if (totalElements > 0 && firstElement >= totalElements) {
			String msg = "For this page, the first element would be " + firstElement + " and the total are " 
					+ totalElements + " elements";
			throw new PaginationException(msg);
		}
		
		// Return the result
		return firstElement;
	}

}
