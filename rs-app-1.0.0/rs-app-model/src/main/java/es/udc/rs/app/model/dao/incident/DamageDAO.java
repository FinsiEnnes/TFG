package es.udc.rs.app.model.dao.incident;

import java.util.List;

import es.udc.rs.app.model.domain.Damage;

public interface DamageDAO {

	public Damage find(String id);
	
	public List<Damage> findAll();
}
