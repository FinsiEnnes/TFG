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
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class CountryAndProvinceTest {

	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullProfessionalCategoryTest () throws InstanceNotFoundException {
		
		log.info("");
		log.info ("==== Starting CountryAndProvinceTest Test ====");
		findCountry();
		findProvince();
	}

	private void findCountry() throws InstanceNotFoundException {
		
		// Due there is only one country in the db.
		Country thisCountry = new Country("ESP", "Espana");
		
		// Find by id.
		Country dbCountry = customerService.findCountry(thisCountry.getId());
		assertEquals(thisCountry, dbCountry);
		
		// Find all and select the first element.
		dbCountry = customerService.findAllCountries().get(0);
		assertEquals(thisCountry, dbCountry);
	}
	
	private void findProvince() throws InstanceNotFoundException {
		
		List<Province> provinces = customerService.findAllProvinces();
		assertEquals(13, provinces.size());
		assertEquals("A Coruna", provinces.get(0).getName());
		assertEquals("Albacete", provinces.get(1).getName());
		assertEquals("Asturias", provinces.get(2).getName());
		assertEquals("Badajoz", provinces.get(3).getName());
		
		Province province = customerService.findProvince(provinces.get(0).getId());
		assertEquals(provinces.get(0), province);
		
		Country country = customerService.findAllCountries().get(0);
		provinces = customerService.findProvinceByCountry(country);
		assertEquals(13, provinces.size());
		assertEquals("A Coruna", provinces.get(0).getName());
		assertEquals("Albacete", provinces.get(1).getName());
		assertEquals("Asturias", provinces.get(2).getName());
		assertEquals("Badajoz", provinces.get(3).getName());
	}
}
