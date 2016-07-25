package es.udc.rs.app.model.dao.business;

import java.util.List;

import es.udc.rs.app.model.domain.BusinessType;

public interface BusinessTypeDAO {

	public BusinessType find(String id);
	
	public List<BusinessType> findAll();
}
