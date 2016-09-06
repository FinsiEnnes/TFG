package es.udc.rs.app.model.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.aptitude.AptitudeDAO;
import es.udc.rs.app.model.dao.assignmentmaterial.AssignmentMaterialDAO;
import es.udc.rs.app.model.dao.assignmentperson.AssignmentPersonDAO;
import es.udc.rs.app.model.dao.assignmentprofile.AssignmentProfileDAO;
import es.udc.rs.app.model.dao.business.BusinessCategoryDAO;
import es.udc.rs.app.model.dao.business.BusinessSizeDAO;
import es.udc.rs.app.model.dao.business.BusinessTypeDAO;
import es.udc.rs.app.model.dao.customer.CustomerDAO;
import es.udc.rs.app.model.dao.freeday.FreeDayDAO;
import es.udc.rs.app.model.dao.historyperson.HistoryPersonDAO;
import es.udc.rs.app.model.dao.historyproject.HistoryProjectDAO;
import es.udc.rs.app.model.dao.incident.DamageDAO;
import es.udc.rs.app.model.dao.incident.IncidentDAO;
import es.udc.rs.app.model.dao.location.CountryDAO;
import es.udc.rs.app.model.dao.location.ProvinceDAO;
import es.udc.rs.app.model.dao.material.MaterialDAO;
import es.udc.rs.app.model.dao.milestone.MilestoneDAO;
import es.udc.rs.app.model.dao.person.PersonDAO;
import es.udc.rs.app.model.dao.phase.PhaseDAO;
import es.udc.rs.app.model.dao.predecessor.PredecessorDAO;
import es.udc.rs.app.model.dao.profctg.LevelProfCatgDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.projectfreeday.ProjectFreeDayDAO;
import es.udc.rs.app.model.dao.projectmgmt.ProjectMgmtDAO;
import es.udc.rs.app.model.dao.state.StateDAO;
import es.udc.rs.app.model.dao.task.PriorityDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.dao.taskincident.TaskIncidentDAO;
import es.udc.rs.app.model.dao.timeoff.TimeOffDAO;
import es.udc.rs.app.model.dao.workload.WorkloadDAO;
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

@Service
public class FindInstanceServiceImpl implements FindInstanceService {

	// ============================================================================
	// ============================== DAO Injection ===============================
	// ============================================================================
	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	private ProvinceDAO provinceDAO;
	
	@Autowired
	private BusinessTypeDAO businessTypeDAO;
	
	@Autowired
	private BusinessCategoryDAO businessCategoryDAO;
	
	@Autowired
	private BusinessSizeDAO businessSizeDAO; 
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private HistoryProjectDAO historyProjectDAO;
	
	@Autowired
	private ProjectFreeDayDAO projectFreeDayDAO;
	
	@Autowired
	private FreeDayDAO freeDayDAO;
	
	@Autowired
	private ProjectMgmtDAO projectMgmtDAO;
	
	@Autowired
	private PhaseDAO phaseDAO;
	
	@Autowired
	private MilestoneDAO milestoneDAO;
	
	@Autowired
	private PriorityDAO priorityDAO;
	
	@Autowired
	private StateDAO stateDAO;
	
	@Autowired 
	private TaskDAO taskDAO;
	
	@Autowired
	private PredecessorDAO predecessorDAO;
	
	@Autowired
	private DamageDAO damageDAO;
	
	@Autowired
	private IncidentDAO incidentDAO;
	
	@Autowired
	private TaskIncidentDAO taskIncidentDAO;
	
	@Autowired
	private MaterialDAO materialDAO;
	
	@Autowired
	private HistoryPersonDAO hPersonDAO;
	
	@Autowired
	private PersonDAO personDAO;
	
	@Autowired
	private TimeOffDAO timeOffDAO;
	
	@Autowired
	private AptitudeDAO aptitudeDAO;
	
	@Autowired 
	private ProfessionalCategoryDAO profCatgDAO;
	
	@Autowired
	private LevelProfCatgDAO levelProfCatgDAO;
	
	@Autowired
	private AssignmentProfileDAO assigProfDAO;
	
	@Autowired
	private AssignmentMaterialDAO assigMatDAO;
	
	@Autowired
	private AssignmentPersonDAO assigPersonDAO;
	
	@Autowired
	private WorkloadDAO workloadDAO;
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findCountry(Country country) throws InstanceNotFoundException {
		
		String idCountry = country.getId();
		
		if (!countryDAO.countryExists(idCountry)) {
			throw new InstanceNotFoundException(idCountry, Country.class.getName());
		}
	}
	

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProvince(Province province) throws InstanceNotFoundException {
		Long idProvince = province.getId();
		
		if (!provinceDAO.ProvinceExists(idProvince)) {
			throw new InstanceNotFoundException(idProvince, Province.class.getName());
		}
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findBusinessType(BusinessType businessType) throws InstanceNotFoundException {
		String idBusinessType = businessType.getId();
		
		if (!businessTypeDAO.businessTypeExists(idBusinessType)) {
			throw new InstanceNotFoundException(idBusinessType, BusinessType.class.getName());
		}
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager") 
	public void findBusinessCategory(BusinessCategory businessCategory) throws InstanceNotFoundException {
		String idBusinessCategory = businessCategory.getId();
		
		if (!businessCategoryDAO.businessCategoryExists(idBusinessCategory)) {
			throw new InstanceNotFoundException(idBusinessCategory, BusinessCategory.class.getName());
		}	
	}

	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findBusinessSize(BusinessSize businessSize) throws InstanceNotFoundException {
		String idBusinessSize = businessSize.getId();
		
		if (!businessSizeDAO.businessSizeExists(idBusinessSize)) {
			throw new InstanceNotFoundException(idBusinessSize, BusinessSize.class.getName());
		}		
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findCustomer(Customer customer)  throws InstanceNotFoundException {
		
		Long idCustomer = customer.getId();
		
		if (!customerDAO.CustomerExists(idCustomer)) {
			throw new InstanceNotFoundException(idCustomer, Customer.class.getName());
		}	
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProject(Project project) throws InstanceNotFoundException {
		
		Long idProject = project.getId();
		
		if (!projectDAO.ProjectExists(idProject)) {
			throw new InstanceNotFoundException(idProject, Project.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findHistoryProject(HistoryProject historyProject) throws InstanceNotFoundException {
		
		Long id = historyProject.getId();
		
		if (!historyProjectDAO.HistoryProjectExists(id)) {
			throw new InstanceNotFoundException(id, HistoryProject.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProjectFreeDay(ProjectFreeDay projectFreeDay) throws InstanceNotFoundException {
		
		Long id = projectFreeDay.getId();
		
		if (!projectFreeDayDAO.ProjectFreeDayExists(id)) {
			throw new InstanceNotFoundException(id, ProjectFreeDay.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findFreeDay(FreeDay freeDay) throws InstanceNotFoundException {
		
		Long idFreeDay = freeDay.getId();
		
		if (!freeDayDAO.FreeDayExists(idFreeDay)) {
			throw new InstanceNotFoundException(idFreeDay, FreeDay.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProjectMgmt(ProjectMgmt projectMgmt) throws InstanceNotFoundException {
		
		Long id = projectMgmt.getId();
		
		if (!projectMgmtDAO.ProjectMgmtExists(id)) {
			throw new InstanceNotFoundException(id, ProjectMgmt.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findPhase(Phase phase) throws InstanceNotFoundException {
		
		Long id = phase.getId();
		
		if (!phaseDAO.phaseExists(id)) {
			throw new InstanceNotFoundException(id, Phase.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findMilestone(Milestone milestone) throws InstanceNotFoundException {
		
		Long id = milestone.getId();
		
		if (!milestoneDAO.milestoneExists(id)) {
			throw new InstanceNotFoundException(id, Milestone.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findPriority(Priority priority) throws InstanceNotFoundException {
		
		String id = priority.getId();
		
		if (!priorityDAO.priorityExists(id)) {
			throw new InstanceNotFoundException(id, Priority.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findState(State state) throws InstanceNotFoundException {
		String id = state.getId();
		
		if (!stateDAO.stateExists(id)) {
			throw new InstanceNotFoundException(id, State.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findTask(Task task)  throws InstanceNotFoundException {
		
		Long idTask = task.getId();
		
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findPredecessor(Predecessor predecessor) throws InstanceNotFoundException {
		
		Long id = predecessor.getId();
		
		if (!predecessorDAO.PredecessorExists(id)) {
			throw new InstanceNotFoundException(id, Predecessor.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findDamage(Damage damage) throws InstanceNotFoundException {
		
		String idDamage = damage.getId();
		
		if (!damageDAO.damageExists(idDamage)) {
			throw new InstanceNotFoundException(idDamage, Damage.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findIncident(Incident incident) throws InstanceNotFoundException {
		
		Long idIncident = incident.getId();
		
		if (!incidentDAO.IncidentExists(idIncident)) {
			throw new InstanceNotFoundException(idIncident, Incident.class.getName());
		}
	}


	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findTaskIncident(TaskIncident taskIncident) throws InstanceNotFoundException {
		
		Long idTaskIncident = taskIncident.getId();
		
		if (!taskIncidentDAO.TaskIncidentExists(idTaskIncident)) {
			throw new InstanceNotFoundException(idTaskIncident, TaskIncident.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findMaterial(Material material) throws InstanceNotFoundException {
		
		Long idMaterial = material.getId();
		
		if (!materialDAO.MaterialExists(idMaterial)) {
			throw new InstanceNotFoundException(idMaterial, Material.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findHistoryPerson(HistoryPerson hPerson) throws InstanceNotFoundException {
		
		Long idHPerson = hPerson.getId();
		
		if (!hPersonDAO.historyPersonExists(idHPerson)) {
			throw new InstanceNotFoundException(idHPerson, HistoryPerson.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findPerson(Person person) throws InstanceNotFoundException {
		
		Long idPerson = person.getId();
		
		if (!personDAO.personExists(idPerson)) {
			throw new InstanceNotFoundException(idPerson, Person.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findTimeoff(TimeOff timeoff) throws InstanceNotFoundException {
		
		Long idTimeoff = timeoff.getId();
		
		if (!timeOffDAO.timeoffExists(idTimeoff)) {
			throw new InstanceNotFoundException(idTimeoff, TimeOff.class.getName());
		}
	}
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findAptitude(Aptitude aptitude) throws InstanceNotFoundException {
		
		Long idAptitude = aptitude.getId();
		
		if (!aptitudeDAO.aptitudeExists(idAptitude)) {
			throw new InstanceNotFoundException(idAptitude, Aptitude.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findProfessionalCategory(ProfessionalCategory pf) throws InstanceNotFoundException {
		
		Long idProfCatg = pf.getId();
		
		if (!profCatgDAO.ProfessionalCategoryExists(idProfCatg)) {
			throw new InstanceNotFoundException(idProfCatg, ProfessionalCategory.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findLevelProfCatg(LevelProfCatg levelProfCatg) throws InstanceNotFoundException {
		
		String idLevelProfCatg = levelProfCatg.getId();
		
		if (!levelProfCatgDAO.levelProfCatgExists(idLevelProfCatg)) {
			throw new InstanceNotFoundException(idLevelProfCatg, LevelProfCatg.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findAssignmentProfile(AssignmentProfile ap) throws InstanceNotFoundException {
		
		Long idAssigProfile = ap.getId();
		
		if (!assigProfDAO.AssignmentProfileExists(idAssigProfile)) {
			throw new InstanceNotFoundException(idAssigProfile, AssignmentProfile.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findAssignmentMaterial(AssignmentMaterial am) throws InstanceNotFoundException {
		
		Long idAssigMaterial = am.getId();
		
		if (!assigMatDAO.AssignmentMaterialExists(idAssigMaterial)) {
			throw new InstanceNotFoundException(idAssigMaterial, AssignmentMaterial.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findAssignmentPerson(AssignmentPerson ap) throws InstanceNotFoundException {
		
		Long idAssigPerson = ap.getId();
		
		if (!assigPersonDAO.AssignmentPersonExists(idAssigPerson)) {
			throw new InstanceNotFoundException(idAssigPerson, AssignmentPerson.class.getName());
		}
	}
	
	
	// ============================================================================
	@Override
	@Transactional(value="myTransactionManager")
	public void findWorkload(Workload workload) throws InstanceNotFoundException {
		
		Long idWorkload = workload.getId();
		
		if (!workloadDAO.WorkloadExists(idWorkload)) {
			throw new InstanceNotFoundException(idWorkload, Workload.class.getName());
		}
	}

}
