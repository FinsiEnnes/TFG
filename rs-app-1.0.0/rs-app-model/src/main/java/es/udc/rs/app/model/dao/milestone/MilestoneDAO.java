package es.udc.rs.app.model.dao.milestone;

import java.util.List;

import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Project;

public interface MilestoneDAO {

	public Long create(Milestone milestone);
	
	public Milestone find(Long id);
	
	public List<Milestone> findByProject(Project project);
				
	public boolean milestoneExists(Long id);
	
	public void update(Milestone milestone);
	
	public void remove(Milestone milestone);
}
