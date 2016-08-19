package es.udc.rs.app.model.dao.workload;

import java.util.List;

import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.Workload;

public interface WorkloadDAO {

	public Long create(Workload workload);
	
	public Workload find(Long id);
	
	public List<Workload> findByTask(Task task);
	
	public List<Workload> findByHistoryPerson(HistoryPerson historyPerson);
				
	public boolean WorkloadExists(Long id);
	
	public void update(Workload workload);
	
	public void remove(Workload workload);

}
