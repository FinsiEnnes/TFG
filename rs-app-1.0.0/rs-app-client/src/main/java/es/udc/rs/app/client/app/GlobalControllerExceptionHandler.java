package es.udc.rs.app.client.app;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus; 

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;


public class GlobalControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
	@ExceptionHandler(InputValidationException.class)
	public ModelAndView InputValidation(InputValidationException e) {

		ModelAndView mav = new ModelAndView("exception");
		mav.addObject("name", e.getClass().getSimpleName());
		mav.addObject("message", e.getMessage());

		return mav;
	}


	@ResponseStatus(HttpStatus.FORBIDDEN)  // 403
	@ExceptionHandler(GenericJDBCException.class)
	public ModelAndView GenericJDBC(GenericJDBCException e) {

		ModelAndView mav = new ModelAndView("exception");
		mav.addObject("name", e.getClass().getSimpleName());
		mav.addObject("message", e.getCause().getCause().getMessage());

		return mav;
	}


	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	@ExceptionHandler(InstanceNotFoundException.class)
	public ModelAndView InstanceNotFound(HttpServletRequest req, InstanceNotFoundException e) {

		ModelAndView mav = new ModelAndView("exception");
		mav.addObject("name", e.getClass().getSimpleName());
		mav.addObject("message", e.getMessage());

		return mav;
	}


	@ResponseStatus(HttpStatus.CONFLICT)  // 409
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView DataAccess(DataAccessException e) {

		ModelAndView mav = new ModelAndView("exception");
		mav.addObject("name", e.getClass().getSimpleName());
		mav.addObject("message", e.getCause().getCause().getMessage());

		return mav;
	}
}
