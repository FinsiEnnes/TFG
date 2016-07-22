package es.udc.rs.app.model.dao.profctg;

import java.util.List;

import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.ProfessionalCategory;

public interface ProfessionalCategoryDAO {
	public Long create(ProfessionalCategory profCtg);
	
	public ProfessionalCategory find(Long id);
	
	public List<ProfessionalCategory> findAll();
	
	public List<ProfessionalCategory> findByNameAndLevel(String name, LevelProfCatg level);
	
	public boolean ProfessionalCategoryExists(Long id);
	
	public void update(ProfessionalCategory profCtg);
	
	public void remove(ProfessionalCategory profCtg);	

}
