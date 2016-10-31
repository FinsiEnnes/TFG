package es.udc.rs.app.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.rs.app.client.conversor.CustomerDTOConversor;
import es.udc.rs.app.client.dto.CustomerDTO;
import es.udc.rs.app.client.dto.PersonDTO;
import es.udc.rs.app.model.domain.Customer;
import es.udc.rs.app.model.service.customer.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
	//-----------------------------------------------------------------------------------------------------
	// [GET]-> /persons || Main method that deals with the request to /persons   
	//-----------------------------------------------------------------------------------------------------
	@RequestMapping(value="/customers", method=RequestMethod.GET)
	public String customerTable (Model model) {
		
    	// Initialization of variables
    	List<CustomerDTO> customersDTO = new ArrayList<CustomerDTO>();
    	List<Customer> customers = new ArrayList<Customer>();
    	
    	// Get the customers and convert those to DTO
    	customers = customerService.findAllCustomers();
    	customersDTO = CustomerDTOConversor.toCustomerDTOList(customers);
    	
    	// Now create the model
    	model.addAttribute("customers", customersDTO);
		
		return "customer/customerTable";
	}
}
