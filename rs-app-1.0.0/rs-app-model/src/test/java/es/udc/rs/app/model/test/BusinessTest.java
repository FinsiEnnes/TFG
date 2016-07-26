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
import es.udc.rs.app.model.service.customer.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class BusinessTest {

	private Logger log = Logger.getLogger("project");
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void fullBusinessTest () throws InstanceNotFoundException {
		log.info("");
		log.info ("======= Starting BusinessTest Test =======");
		findBusinessType(); 
		findBusinessCategory();
		findBusinessSize();
	}
	
	private void findBusinessType() throws InstanceNotFoundException {
		
		// We have established the data previously.
		BusinessType bt1 = new BusinessType("EXTR", "Extraccion");
		BusinessType bt2 = new BusinessType("MAYR", "Mayorista");
		BusinessType bt3 = new BusinessType("MINR", "Minorista");
		BusinessType bt4 = new BusinessType("PROD", "Produccion");

		// Find by id.
		BusinessType bt = customerService.findBusinessType(bt1.getId());
		assertEquals(bt1, bt);
		
		bt = customerService.findBusinessType(bt2.getId());
		assertEquals(bt2, bt);
		
		// Find all.
		List <BusinessType> bts = customerService.findAllBusinessTypes();
		assertEquals(5, bts.size());
		assertEquals(bt1, bts.get(0));
		assertEquals(bt2, bts.get(1));
		assertEquals(bt3, bts.get(2));
		assertEquals(bt4, bts.get(3));	
	}
	
	
	private void findBusinessCategory() throws InstanceNotFoundException {
		
		BusinessCategory bc1 = new BusinessCategory("ADM", "Administracion");
		BusinessCategory bc2 = new BusinessCategory("ASC", "Asociacion");
		BusinessCategory bc3 = new BusinessCategory("MUL", "Multinacional");
		
		// Find by id.
		BusinessCategory bc = customerService.findBusinessCategory(bc1.getId());
		assertEquals(bc1, bc);
		
		bc = customerService.findBusinessCategory(bc2.getId());
		assertEquals(bc2, bc);
		
		bc = customerService.findBusinessCategory(bc3.getId());
		assertEquals(bc3, bc);
		
		// Find all.
		List <BusinessCategory> bcs = customerService.findAllBusinessCategories();
		assertEquals(6, bcs.size());
		assertEquals(bc1, bcs.get(0));
		assertEquals(bc2, bcs.get(1));
		assertEquals(bc3, bcs.get(2));	

	}
	
	
	private void findBusinessSize() throws InstanceNotFoundException {
		
		BusinessSize bs1 = new BusinessSize("G", "Grande", 51, null);
		BusinessSize bs2 = new BusinessSize("M", "Mediana", 11, 50);
		BusinessSize bs3 = new BusinessSize("P", "Pequena", 0, 10);
		
		// Find by id.
		BusinessSize bs = customerService.findBusinessSize(bs1.getId());
		assertEquals(bs, bs1);
		
		bs = customerService.findBusinessSize(bs2.getId());
		assertEquals(bs, bs2);
		
		bs = customerService.findBusinessSize(bs3.getId());
		assertEquals(bs, bs3);
		
		// Find all.
		List <BusinessSize> bss = customerService.findAllBusinessSizes();
		assertEquals(3, bss.size());
		assertEquals(bs1, bss.get(0));
		assertEquals(bs2, bss.get(1));
		assertEquals(bs3, bss.get(2));
	}
}
