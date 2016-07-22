package es.udc.rs.app.model.dao.profctg;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.LevelProfCatg;
import es.udc.rs.app.model.domain.ProfessionalCategory;

@Repository
public class ProfessionalCategoryDAOImpl implements ProfessionalCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(ProfessionalCategory profCtg) {
		
		if (profCtg.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(profCtg);
		return id;
	}

	@Override
	public ProfessionalCategory find(Long id) {
		
		Session s = sessionFactory.getCurrentSession();
		ProfessionalCategory pc = (ProfessionalCategory) s.get(ProfessionalCategory.class, id);
		
		return pc;
	}

	@Override
	public List<ProfessionalCategory> findAll() {
		
		// Create the query.
		String query = "FROM ProfessionalCategory P ORDER BY P.name ASC";

		// Get the profesional categories
		Session s = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<ProfessionalCategory> pcs = (List<ProfessionalCategory>) s.createQuery(query).list();
	
		return pcs;
	}

	@Override
	public List<ProfessionalCategory> findByNameAndLevel(String name,LevelProfCatg level) {
		
		// Create the query.
		String queryString = "FROM ProfessionalCategory P ";
		Query query;
				
		if (name == null) {
			queryString = queryString + "WHERE level = :level ORDER BY P.name ASC";
			query = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("level", level);
		}
		
		else if (level == null) {
			queryString = queryString + "WHERE name LIKE :name ORDER BY P.name ASC";
			query = sessionFactory.getCurrentSession().createQuery(queryString).setParameter("name", "%"+name+"%");
		}
		
		else {
			queryString = queryString + "WHERE name LIKE :name AND level = :level ORDER BY P.name ASC";
			query = sessionFactory.getCurrentSession().createQuery(queryString)
					.setParameter("name", "%"+name+"%").setParameter("level", level);
		}
		
		@SuppressWarnings("unchecked")
		List<ProfessionalCategory> pcs = (List<ProfessionalCategory>) query.list();
		
		// Return the result
		return pcs;
	}

	@Override
	public boolean ProfessionalCategoryExists(Long id) {
		
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProfessionalCategory.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(ProfessionalCategory profCtg) {
		
		if (profCtg.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(profCtg);	
	}

	@Override
	public void remove(ProfessionalCategory profCtg) {
		sessionFactory.getCurrentSession().delete(profCtg);		
	}

}
