package es.udc.rs.app.model.dao.task;

import java.util.List;

import es.udc.rs.app.model.domain.Priority;

public interface PriorityDAO {

	public Priority find(String id);
	
	public List<Priority> findAll();
	
	public boolean priorityExists(String id);
}
