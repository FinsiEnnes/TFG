package es.udc.rs.app.model.dao.location;

import java.util.List;

import es.udc.rs.app.model.domain.Country;

public interface CountryDAO {

	public Country find(String id);
	
	public List<Country> findAll();
	
	public boolean countryExists(String id);
}
