package es.udc.rs.app.model.dao.predecessor;

import java.util.List;

import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;

public interface PredecessorDAO {

	public Long create(Predecessor predecessor);
	
	public Predecessor find(Long id);
	
	public List<Predecessor> findByTask(Task task);
	
	public List<Predecessor> findByProject(Project project);
				
	public boolean PredecessorExists(Long id);
	
	public void update(Predecessor predecessor);
	
	public void remove(Predecessor predecessor);
}
