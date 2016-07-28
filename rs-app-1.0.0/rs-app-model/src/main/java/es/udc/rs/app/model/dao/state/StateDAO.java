package es.udc.rs.app.model.dao.state;

import java.util.List;

import es.udc.rs.app.model.domain.State;

public interface StateDAO {
	
	public State find(String id);
	
	public List<State> findAll();

}
