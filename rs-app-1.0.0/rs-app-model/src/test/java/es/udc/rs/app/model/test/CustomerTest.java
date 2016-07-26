package es.udc.rs.app.model.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.BusinessCategory;
import es.udc.rs.app.model.domain.BusinessSize;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Customer;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class CustomerTest {
	
	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullCustomerTest() throws InstanceNotFoundException {
		log.info("");
		log.info ("=========== Starting Customer Test ==========");
		createAndFindCustomer();
		updateCustomer();
		removeCustomer();		
	}
	
	private void createAndFindCustomer() throws InstanceNotFoundException {
		
		// First get the data of the attributes.
		Province p1 = customerService.findProvince(1L);
		Province p2 = customerService.findProvince(8L);
		
		BusinessType bt1 = customerService.findBusinessType("PROD");
		BusinessType bt2 = customerService.findBusinessType("SERV");
		
		BusinessCategory bc1 = customerService.findBusinessCategory("NAC");
		BusinessCategory bc2 = customerService.findBusinessCategory("MUL");
		
		BusinessSize bs1 = customerService.findBusinessSize("P");
		BusinessSize bs2 = customerService.findBusinessSize("G");
		
		// Now create the customers.
		Customer c1 = new Customer("GaliGames", p1, bt1, bc1, bs1);
		Customer c2 = new Customer("CajaBank", p2, bt2, bc2, bs2);
		
		// ================================ Correct create ================================
		customerService.createCustomer(c1);
		customerService.createCustomer(c2);
		
		// ========================== Find of the created objects =========================
		// By id
		Customer c3 = customerService.findCustomer(c1.getId());
		assertEquals(c1, c3);
		
		c3 = customerService.findCustomer(c2.getId());
		assertEquals(c2, c3);
		
		// All
		List<Customer> customers = customerService.findAllCustomers();
		assertEquals(2, customers.size());
		assertEquals(customers.get(0), c2);
		assertEquals(customers.get(1), c1);
		
		// By name
		c3 = customerService.findCustomerByName("GaliGames").get(0);
		assertEquals(c1, c3);
		
		c3 = customerService.findCustomerByName("bank").get(0);
		assertEquals(c2, c3);
	}
	
	private void updateCustomer() throws InstanceNotFoundException {
		
		BusinessType bt = customerService.findBusinessType("SERV");
		BusinessCategory bc = customerService.findBusinessCategory("MUL");
		BusinessSize bs = customerService.findBusinessSize("G");
		
		// =============================== Correct update ===============================
		Customer c = customerService.findCustomerByName("GaliGames").get(0);
		c.setName("OnlyGame");
		c.setType(bt);
		c.setCategory(bc);
		c.setSize(bs);
		
		customerService.updateCustomer(c);
		
		Customer customer = customerService.findCustomerByName("OnlyGame").get(0);
		assertEquals(c, customer);	
	}
	
	private void removeCustomer() throws InstanceNotFoundException {
		
		// =============================== Correct remove ===============================
		List<Customer> customers = customerService.findAllCustomers();
		
		for (Customer c : customers) {
			customerService.removeCustomer(c.getId());
		}
		
		customers = customerService.findAllCustomers();
		assertEquals(0, customers.size());		
	}
	

}
