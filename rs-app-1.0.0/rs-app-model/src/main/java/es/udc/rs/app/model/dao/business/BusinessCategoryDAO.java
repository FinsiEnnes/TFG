package es.udc.rs.app.model.dao.business;

import java.util.List;

import es.udc.rs.app.model.domain.BusinessCategory;

public interface BusinessCategoryDAO {

	public BusinessCategory find(String id);
	
	public List<BusinessCategory> findAll();	
	
	public boolean businessCategoryExists(String id);
}
