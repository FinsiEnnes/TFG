package es.udc.rs.app.model.dao.profctg;

import java.util.List;

import es.udc.rs.app.model.domain.LevelProfCatg;

public interface LevelProfCatgDAO {
	
	public LevelProfCatg find(String id);
	
	public List<LevelProfCatg> findAll();

}
