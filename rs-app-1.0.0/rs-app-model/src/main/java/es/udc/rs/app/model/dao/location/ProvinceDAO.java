package es.udc.rs.app.model.dao.location;

import java.util.List;

import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Province;

public interface ProvinceDAO {

	public Province find(Long id);
	
	public List<Province> findAll();
	
	public List<Province> findByCountry(Country country);
}
