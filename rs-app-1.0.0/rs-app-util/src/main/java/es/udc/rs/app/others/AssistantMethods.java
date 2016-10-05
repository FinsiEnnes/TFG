package es.udc.rs.app.others;

import es.udc.rs.app.exceptions.FirstPageElementException;

public class AssistantMethods {
	
	public static int getFirstPageElement(int pageNumber, int pageSize, int totalElements) 
			throws FirstPageElementException {
		
		int firstElement = 0;
		
		// Get the first element
		firstElement = (pageNumber - 1) * pageSize;
		
		// Check if it is correct
		if (firstElement >= totalElements) {
			String msg = "For this page, the first element would be " + firstElement + " and the total are " 
					+ totalElements + " elements";
			throw new FirstPageElementException(msg);
		}
		
		// Return the result
		return firstElement;
	}

}
