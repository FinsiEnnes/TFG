package es.udc.rs.app.model.dao.phase;

import java.util.List;

import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Project;

public interface PhaseDAO {

	public Long create(Phase phase);
	
	public Phase find(Long id);
		
	public List<Phase> findByProject(Project project);
		
	public boolean PhaseExists(Long id);
	
	public void update(Phase phase);
	
	public void remove(Phase phase);
}
