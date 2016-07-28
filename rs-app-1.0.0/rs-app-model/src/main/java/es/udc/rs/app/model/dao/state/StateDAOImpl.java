package es.udc.rs.app.model.dao.state;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.State;

@Repository
public class StateDAOImpl implements StateDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public State find(String id) {
		State state = (State) sessionFactory.getCurrentSession().get(State.class, id);
		return state;
	}

	@Override
	public List<State> findAll() {
		
		// Create the query
		String queryString = "FROM State S ORDER BY S.name";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Return the result
		@SuppressWarnings("unchecked")
		List<State> states = query.list();
		return states;
	}

}
