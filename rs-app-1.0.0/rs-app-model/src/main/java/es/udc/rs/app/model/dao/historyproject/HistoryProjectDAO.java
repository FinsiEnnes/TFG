package es.udc.rs.app.model.dao.historyproject;

import java.util.List;

import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;

public interface HistoryProjectDAO {

	public Long create(HistoryProject historyProject);
	
	public HistoryProject find(Long id);
		
	public List<HistoryProject> findByProject(Project project);
	
	public HistoryProject findCurrentHistoryProject(Project project);
	
	public boolean HistoryProjectExists(Long id);
	
	public void update(HistoryProject historyProject);
	
	public void remove(HistoryProject historyProject);
}
