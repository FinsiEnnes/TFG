package es.udc.rs.app.model.dao.project;

import es.udc.rs.app.model.domain.Project;

public interface ProjectDAO {

	public Long create(Project project);
	
	public Project find(Long id);
	
	public Project findByName(String name);
	
	public boolean ProjectExists(Long id);
	
	public void update(Project project);
	
	public void remove(Project project);	
}
