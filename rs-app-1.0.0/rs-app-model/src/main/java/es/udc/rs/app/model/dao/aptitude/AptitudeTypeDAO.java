package es.udc.rs.app.model.dao.aptitude;

import java.util.List;

import es.udc.rs.app.model.domain.AptitudeType;

public interface AptitudeTypeDAO {
	
	public AptitudeType find(String id);
	
	public List<AptitudeType> findAll();

}
