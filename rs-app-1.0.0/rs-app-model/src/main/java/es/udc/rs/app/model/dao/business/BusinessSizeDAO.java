package es.udc.rs.app.model.dao.business;

import java.util.List;

import es.udc.rs.app.model.domain.BusinessSize;

public interface BusinessSizeDAO {

	public BusinessSize find(String id);
	
	public List<BusinessSize> findAll();
	
	public boolean businessSizeExists(String id);
}
