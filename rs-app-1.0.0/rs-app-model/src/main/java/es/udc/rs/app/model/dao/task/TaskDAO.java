package es.udc.rs.app.model.dao.task;

import java.util.List;

import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Task;

public interface TaskDAO {
	
	public Long create(Task task);
	
	public Task find(Long id);
	
	public List<Task> findByProject(Project project);
					
	public boolean TaskExists(Long id);
	
	public void update(Task task);
	
	public void remove(Task task);

}
