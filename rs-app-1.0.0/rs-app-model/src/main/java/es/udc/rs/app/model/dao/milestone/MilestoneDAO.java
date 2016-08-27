package es.udc.rs.app.model.dao.milestone;

import es.udc.rs.app.model.domain.Milestone;

public interface MilestoneDAO {

	public Long create(Milestone milestone);
	
	public Milestone find(Long id);
				
	public boolean milestoneExists(Long id);
	
	public void update(Milestone milestone);
	
	public void remove(Milestone milestone);
}
