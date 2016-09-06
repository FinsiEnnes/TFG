package es.udc.rs.app.model.util;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.Aptitude;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.BusinessCategory;
import es.udc.rs.app.model.domain.BusinessSize;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Customer;
import es.udc.rs.app.model.domain.Damage;
import es.udc.rs.app.model.domain.FreeDay;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.Milestone;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.Predecessor;
import es.udc.rs.app.model.domain.Priority;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.ProjectFreeDay;
import es.udc.rs.app.model.domain.ProjectMgmt;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.State;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
import es.udc.rs.app.model.domain.TimeOff;
import es.udc.rs.app.model.domain.Workload;

public interface FindInstanceService {

	public void findCountry(Country country) throws InstanceNotFoundException;
	
	public void findProvince(Province province) throws InstanceNotFoundException;
	
	public void findBusinessType(BusinessType businessType) throws InstanceNotFoundException;
	
	public void findBusinessCategory(BusinessCategory businessCategory) throws InstanceNotFoundException;
	
	public void findBusinessSize(BusinessSize businessSize) throws InstanceNotFoundException;
	
	public void findCustomer(Customer customer)  throws InstanceNotFoundException;
	
	public void findProject(Project project) throws InstanceNotFoundException;
	
	public void findHistoryProject(HistoryProject historyProject) throws InstanceNotFoundException;
	
	public void findProjectFreeDay(ProjectFreeDay projectFreeDay) throws InstanceNotFoundException;
	
	public void findFreeDay(FreeDay freeDay) throws InstanceNotFoundException;
	
	public void findProjectMgmt(ProjectMgmt projectMgmt) throws InstanceNotFoundException;
	
	public void findPhase(Phase phase) throws InstanceNotFoundException;
	
	public void findMilestone(Milestone milestone) throws InstanceNotFoundException;
	
	public void findPriority(Priority priority) throws InstanceNotFoundException;
	
	public void findState(State state) throws InstanceNotFoundException;
	
	public void findTask(Task task) throws InstanceNotFoundException;
	
	public void findPredecessor(Predecessor predecessor) throws InstanceNotFoundException;
	
	public void findDamage(Damage damage) throws InstanceNotFoundException;
	
	public void findIncident(Incident incident) throws InstanceNotFoundException;
	
	public void findTaskIncident(TaskIncident taskIncident) throws InstanceNotFoundException;
	
	public void findMaterial(Material material) throws InstanceNotFoundException;
	
	public void findHistoryPerson(HistoryPerson hPerson) throws InstanceNotFoundException;
	
	public void findPerson(Person person) throws InstanceNotFoundException;
	
	public void findTimeoff(TimeOff timeoff) throws InstanceNotFoundException;
	
	public void findAptitude(Aptitude aptitude) throws InstanceNotFoundException;
	
	public void findProfessionalCategory(ProfessionalCategory pf) throws InstanceNotFoundException;
	
	public void findLevelProfCatg(LevelProfCatg levelProfCatg) throws InstanceNotFoundException;
	
	public void findAssignmentProfile(AssignmentProfile ap) throws InstanceNotFoundException;
	
	public void findAssignmentMaterial(AssignmentMaterial am) throws InstanceNotFoundException;
	
	public void findAssignmentPerson(AssignmentPerson ap) throws InstanceNotFoundException;
	
	public void findWorkload(Workload workload) throws InstanceNotFoundException;
}
