package es.udc.rs.app.model.dao.projectmgmt;

import java.util.List;

import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectMgmt;

public interface ProjectMgmtDAO {
	
	public Long create(ProjectMgmt projectMgmt);
	
	public ProjectMgmt find(Long id);
	
	public List<ProjectMgmt> findAll();
	
	public List<ProjectMgmt> findByProject(Project project);
			
	public boolean ProjectMgmtExists(Long id);
	
	public void update(ProjectMgmt projectMgmt);
	
	public void remove(ProjectMgmt projectMgmt);

}
