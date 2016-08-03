package es.udc.rs.app.model.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.rs.app.exceptions.InputValidationException;
import es.udc.rs.app.exceptions.InstanceNotFoundException;
import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.Person;
import es.udc.rs.app.model.domain.Phase;
import es.udc.rs.app.model.domain.ProfessionalCategory;
import es.udc.rs.app.model.domain.Project;
import es.udc.rs.app.model.domain.Province;
import es.udc.rs.app.model.service.customer.CustomerService;
import es.udc.rs.app.model.service.person.PersonService;
import es.udc.rs.app.model.service.project.ProjectService;

public class TestUtils {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProjectService projectService;
	
	public Person p1;
	public Person p2;
	public Person p3;
	public Person p4;
	
	public ProfessionalCategory pc1;
	public ProfessionalCategory pc2;
	public ProfessionalCategory pc3;
	public ProfessionalCategory pc4;
	public ProfessionalCategory pc5;
	public ProfessionalCategory pc6;
	
	public HistoryPerson hp1;
	public HistoryPerson hp2;
	
	public Project project;
	
	public Phase phase1;
	
	public HistoryProject planHP;
	
	
	public void createDataSet() throws ParseException, InputValidationException, InstanceNotFoundException {
		
		// ============================== Person ==============================
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		
		p1 = new Person("Raul", "Gonzalez","Costa","11111111A","costa@gmail.com", fmt.parse("2013-05-06"), "");
		p2 = new Person("Paco","Perez","Perales","22222222B","pperez@gmail.com", fmt.parse("2011-05-10"), "");
		p3 = new Person("Laura","Pois","Nada","33333333C","la.pois@gmail.com", fmt.parse("2012-01-08"), "");
		p4 = new Person("Maruja","Ramirez","Diez","44444444D","maruja@gmail.com", fmt.parse("2010-08-22"),"");
		
		personService.createPerson(p1);
		personService.createPerson(p2);
		personService.createPerson(p3);
		personService.createPerson(p4);	
		
		
		// ====================== Professional Category =======================
		LevelProfCatg jun = personService.findLevelProfCatg("JUN");
		LevelProfCatg sen = personService.findLevelProfCatg("SEN");
		LevelProfCatg sjn = personService.findLevelProfCatg("SJN");
		LevelProfCatg esp = personService.findLevelProfCatg("ESP");
		LevelProfCatg pds = personService.findLevelProfCatg("PDS");
		
		pc1 = new ProfessionalCategory("Desarrollador SW", 0, sen, 5, null);
		pc2 = new ProfessionalCategory("Desarrollador SW", 3, jun, 8, 12);
		pc3 = new ProfessionalCategory("Diseñador Personajes", 1, sjn, 6, 7);
		pc4 = new ProfessionalCategory("Arquitecto SW", 5, sen, 9, 13);
		pc5 = new ProfessionalCategory("SysAdmin", 7, esp, 10, 14);
		pc6 = new ProfessionalCategory("Jefe de proyectos", 10, pds, 12, 18);
		
		personService.createProfessionalCategory(pc1);
		personService.createProfessionalCategory(pc2);
		personService.createProfessionalCategory(pc3);
		personService.createProfessionalCategory(pc4);
		personService.createProfessionalCategory(pc5);
		personService.createProfessionalCategory(pc6);
		
		// ========================= History Person ===========================
		hp1 = new HistoryPerson(p1, pc1, fmt.parse("2013-05-06"), fmt.parse("2016-04-06"), 5, 6, null);
		hp2 = new HistoryPerson(p2, pc3, fmt.parse("2012-01-10"), null, 6, 7, null);
		
		personService.createHistoryPerson(hp1);
		personService.createHistoryPerson(hp2);
		
		// ============================= Project ==============================
		Province province = customerService.findProvince(1L);
		project = new Project("Proyecto Zeta", new Date(), false, province, fmt.parse("2016-01-06"));
		projectService.createProject(project);
		
		// ============================== Phase ===============================
		phase1 = new Phase(project,"Fase 1",fmt.parse("2016-01-20"),fmt.parse("2016-05-10"),null);
		
		// ========================= History Project ==========================
		planHP = new HistoryProject(project, projectService.findState("PLAN"), fmt.parse("2016-01-10"), null, null);
		projectService.createHistoryProject(planHP);

	}
	
	
	public void deleteDataSet () throws InstanceNotFoundException {
		
		// ============================== Person ==============================
		List<Person> persons = personService.findAllPersons();
		
		for (Person p : persons) {
			personService.removePerson(p.getId());
		}
		
		// ====================== ProfessionalCategory =======================
		List<ProfessionalCategory> profiles = personService.findAllProfessionalCategories();
		
		for (ProfessionalCategory prof : profiles) {
			personService.removeProfessionalCategory(prof.getId());
		}
		
		// ========================= History Person ===========================
		List<HistoryPerson> histories = personService.findAllHistoryPerson();
		
		for (HistoryPerson hp : histories) {
			personService.removeHistoryPerson(hp.getId());
		}
		
		// ============================= Project ==============================
		projectService.removeProject(project.getId()); // Also delete HistoryProject and Phase
	}
	
}
