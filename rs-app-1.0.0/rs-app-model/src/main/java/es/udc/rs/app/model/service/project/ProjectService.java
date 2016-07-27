package es.udc.rs.app.model.service.project;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Project;

public interface ProjectService {

	// ============================ Project operations ============================
	public Long createProject(Project project) throws InputValidationException;
	
	public Project findProject(Long id) throws InstanceNotFoundException;
	
	public Project findProjectByName(String name) throws InstanceNotFoundException;
		
	public void updateProject(Project project) throws InputValidationException, InstanceNotFoundException;
	
	public void removeProject(Long id) throws InstanceNotFoundException;
}
