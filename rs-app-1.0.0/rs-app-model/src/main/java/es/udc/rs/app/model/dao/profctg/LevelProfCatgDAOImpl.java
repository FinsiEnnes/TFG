package es.udc.rs.app.model.dao.profctg;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.LevelProfCatg;

@Repository
public class LevelProfCatgDAOImpl implements LevelProfCatgDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public LevelProfCatg find(String id) {
		
		LevelProfCatg lpc = (LevelProfCatg) sessionFactory.getCurrentSession().get(LevelProfCatg.class, id);
		return lpc;
	}

	@Override
	public List<LevelProfCatg> findAll() {
		// Create the query.
		String query = "FROM LevelProfCatg L ORDER BY L.name ASC";

		// Get the persons
		@SuppressWarnings("unchecked")
		List<LevelProfCatg> lpcs = (List<LevelProfCatg>) sessionFactory.getCurrentSession()
														 .createQuery(query).list();

		return lpcs;
	}
	
	@Override
	public boolean levelProfCatgExists(String id) {
		
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LevelProfCatg.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

}
