package es.udc.rs.app.model.dao.projectfreeday;

import java.util.List;

import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectFreeDay;

public interface ProjectFreeDayDAO {

	public Long create(ProjectFreeDay projectFreeDay);
	
	public ProjectFreeDay find(Long id);
	
	public List<ProjectFreeDay> findAll();
	
	public List<FreeDay> findByProject(Project project);
			
	public boolean ProjectFreeDayExists(Long id);
	
	public void update(ProjectFreeDay projectFreeDay);
	
	public void remove(ProjectFreeDay projectFreeDay);
}
