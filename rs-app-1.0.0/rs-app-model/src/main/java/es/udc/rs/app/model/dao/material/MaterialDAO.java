package es.udc.rs.app.model.dao.material;

import java.util.List;

import es.udc.rs.app.model.domain.Material;

public interface MaterialDAO {

	public Long create(Material material);
	
	public Material find(Long id);
	
	public List<Material> findAll();
	
	public List<Material> findByNameAndInner(String name, Boolean inner);
	
	public boolean MaterialExists(Long id);
	
	public void update(Material material);
	
	public void remove(Material material);
}
