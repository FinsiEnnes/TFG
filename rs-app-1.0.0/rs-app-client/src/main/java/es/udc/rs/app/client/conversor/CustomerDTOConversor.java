package es.udc.rs.app.client.conversor;

import java.util.ArrayList;
import java.util.List;

import es.udc.rs.app.client.dto.CustomerDTO;
import es.udc.rs.app.model.domain.Customer;

public class CustomerDTOConversor {

	public static List<CustomerDTO> toCustomerDTOList(List<Customer> customers) {
		
		List<CustomerDTO> customersDTO = new ArrayList<CustomerDTO>();
		
		for (Customer c : customers) {
			customersDTO.add(toCustomerDTO(c));
		}
		return customersDTO;
	}
	
	public static CustomerDTO toCustomerDTO(Customer customer) {
		
		CustomerDTO customerDTO = new CustomerDTO();
		
		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		customerDTO.setCountry(customer.getProvince().getCountry().getName());
		customerDTO.setProvince(customer.getProvince().getName());
		customerDTO.setType(customer.getType().getName());
		customerDTO.setCategory(customer.getCategory().getName());
		customerDTO.setSize(customer.getSize().getName());
		
		return customerDTO;
	}
}
