package es.udc.rs.app.model.dao.tasklink;

import java.util.List;

import es.udc.rs.app.model.domain.TaskLinkType;

public interface TaskLinkTypeDAO {

	public TaskLinkType find(String id);
	
	public List<TaskLinkType> findAll();
}
