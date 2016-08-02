package es.udc.rs.app.model.dao.incident;

import es.udc.rs.app.model.domain.Incident;

public interface IncidentDAO {
	
	public Long create(Incident incident);
	
	public Incident find(Long id);
				
	public boolean IncidentExists(Long id);
	
	public void update(Incident incident);
	
	public void remove(Incident incident);	

}
