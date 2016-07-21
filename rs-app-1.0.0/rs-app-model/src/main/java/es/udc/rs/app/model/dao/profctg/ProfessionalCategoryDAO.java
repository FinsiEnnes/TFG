package es.udc.rs.app.model.dao.profctg;

import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.ProfessionalCategory;

public interface ProfessionalCategoryDAO {
	public Long create(ProfessionalCategory profCtg);
	
	public ProfessionalCategory find(Long id);
	
	public List<ProfessionalCategory> findAll();
	
	public List<ProfessionalCategory> findComplex(String name, LevelProfCatg level);
	
	public boolean ProfessionalCategoryExists(Long id);
	
	public void update(ProfessionalCategory ProfessionalCategory);
	
	public void remove(ProfessionalCategory ProfessionalCategory);	

}
