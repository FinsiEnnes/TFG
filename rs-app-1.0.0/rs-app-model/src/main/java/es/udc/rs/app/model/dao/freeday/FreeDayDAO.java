package es.udc.rs.app.model.dao.freeday;

import java.util.List;

import es.udc.rs.app.model.domain.FreeDay;

public interface FreeDayDAO {

	public Long create(FreeDay freeDay);
	
	public FreeDay find(Long id);
	
	public List<FreeDay> findAll();
			
	public boolean FreeDayExists(Long id);
	
	public void update(FreeDay freeDay);
	
	public void remove(FreeDay freeDay);	
}
