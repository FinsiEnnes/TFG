package es.udc.rs.app.model.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.dao.assignmentmaterial.AssignmentMaterialDAO;
import es.udc.rs.app.model.dao.assignmentperson.AssignmentPersonDAO;
import es.udc.rs.app.model.dao.assignmentprofile.AssignmentProfileDAO;
import es.udc.rs.app.model.dao.business.BusinessCategoryDAO;
import es.udc.rs.app.model.dao.business.BusinessSizeDAO;
import es.udc.rs.app.model.dao.business.BusinessTypeDAO;
import es.udc.rs.app.model.dao.customer.CustomerDAO;
import es.udc.rs.app.model.dao.historyperson.HistoryPersonDAO;
import es.udc.rs.app.model.dao.incident.IncidentDAO;
import es.udc.rs.app.model.dao.location.CountryDAO;
import es.udc.rs.app.model.dao.location.ProvinceDAO;
import es.udc.rs.app.model.dao.material.MaterialDAO;
import es.udc.rs.app.model.dao.profctg.ProfessionalCategoryDAO;
import es.udc.rs.app.model.dao.project.ProjectDAO;
import es.udc.rs.app.model.dao.task.TaskDAO;
import es.udc.rs.app.model.dao.taskincident.TaskIncidentDAO;
import es.udc.rs.app.model.dao.workload.WorkloadDAO;
import es.udc.rs.app.model.domain.AssignmentMaterial;
import es.udc.rs.app.model.domain.AssignmentPerson;
import es.udc.rs.app.model.domain.AssignmentProfile;
import es.udc.rs.app.model.domain.BusinessCategory;
import es.udc.rs.app.model.domain.BusinessSize;
import es.udc.rs.app.model.domain.BusinessType;
import es.udc.rs.app.model.domain.Country;
import es.udc.rs.app.model.domain.Customer;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Incident;
import es.udc.rs.app.model.domain.Material;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.TaskIncident;
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
	private TaskDAO taskDAO;
	
	@Autowired
	private IncidentDAO incidentDAO;
	
	@Autowired
	private TaskIncidentDAO taskIncidentDAO;
	
	@Autowired
	private MaterialDAO materialDAO;
	
	@Autowired
	private HistoryPersonDAO hPersonDAO;
	
	@Autowired 
	private ProfessionalCategoryDAO profCatgDAO;
	
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
	public void findTask(Task task)  throws InstanceNotFoundException {
		
		Long idTask = task.getId();
		
		if (!taskDAO.TaskExists(idTask)) {
			throw new InstanceNotFoundException(idTask, Task.class.getName());
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
	public void findProfessionalCategory(ProfessionalCategory pf) throws InstanceNotFoundException {
		
		Long idProfCatg = pf.getId();
		
		if (!profCatgDAO.ProfessionalCategoryExists(idProfCatg)) {
			throw new InstanceNotFoundException(idProfCatg, ProfessionalCategory.class.getName());
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
