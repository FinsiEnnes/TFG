package es.udc.rs.app.model.dao.material;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.Material;

@Repository
public class MaterialDAOImpl implements MaterialDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Material material) {
		
		if (material.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(material);
		return id;
	}

	@Override
	public Material find(Long id) {
		Material material = (Material) sessionFactory.getCurrentSession().get(Material.class, id);
		return material;
	}

	@Override
	public List<Material> findAll() {
		
		// Create the query
		String queryString = "FROM Material M ORDER BY M.name";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Material> materials = (List<Material>) query.list();
		
		return materials;
	}

	@Override
	public List<Material> findByNameAndInner(String name, Boolean inner) {
		
		// Create the query and set parameters
		String queryString = "FROM Material M WHERE M.name LIKE :name ";
		
		if (inner != null) {
			queryString = queryString + "AND M.inner = :inner ";
		}
		
		queryString = queryString + "ORDER BY M.name";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("name", "%"+name+"%");
		
		if (inner != null) {
			query.setParameter("inner", inner);
		}
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Material> materials = (List<Material>) query.list();
		
		return materials;
	}

	@Override
	public boolean MaterialExists(Long id) {
		
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Material.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Material material) {
		
		if (material.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(material);		
	}

	@Override
	public void remove(Material material) {
		sessionFactory.getCurrentSession().delete(material);		
	}

}
