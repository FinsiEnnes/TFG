package es.udc.rs.app.validation;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.udc.rs.app.exceptions.InputValidationException;

public class PropertyValidator {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static void validateEmail(String emailStr) throws InputValidationException {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);

		if (!matcher.find()) {
        	throw new InputValidationException("Invalid emailPerson value: " + emailStr);
        }
	}
	
    public static void validateNif(String propertyNif,
            String stringValue) throws InputValidationException {

        if ( (stringValue == null) || (stringValue.length() == 0) ) {
            throw new InputValidationException("Invalid " + propertyNif +
                    " value (it cannot be null neither empty): " +
                    stringValue);
        }
        
        // Check if it has the correct format.
        Pattern pat = Pattern.compile("^[0-9]{8}[a-zA-Z]$");
        Matcher mat = pat.matcher(stringValue);
        
        if (!mat.matches()) {
        	throw new InputValidationException("Invalid " + propertyNif +
                    " value (it has not the correct format): " + stringValue);
        }
    }
	
    public static void validateMandatoryString(String propertyName,
            String stringValue) throws InputValidationException {

        if ( (stringValue == null) || (stringValue.length() == 0) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value. It cannot be null neither empty.");
        }
    }
    
    public static void validateMandatoryDate(String propertyName,
            Date dateValue) throws InputValidationException {

        if ((dateValue == null)) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value. It cannot be null.");
        }
    }
    
    public static void validateIntWithinRange(String propertyName,
            int intValue, int min, int max) throws InputValidationException {

        if (intValue < min || intValue > max) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value. It must be in the range "+min+"-"+max+".");
        }
    }

}