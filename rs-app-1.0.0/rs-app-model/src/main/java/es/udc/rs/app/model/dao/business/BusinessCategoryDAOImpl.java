package es.udc.rs.app.model.dao.business;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.BusinessCategory;

@Repository
public class BusinessCategoryDAOImpl implements BusinessCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public BusinessCategory find(String id) {	
		Session s = sessionFactory.getCurrentSession();
		BusinessCategory businessCatg = (BusinessCategory) s.get(BusinessCategory.class, id);
		return businessCatg;
	}

	@Override
	public List<BusinessCategory> findAll() {
		
		// Create the query.
		String queryString = "FROM BusinessCategory B ORDER BY B.name";
		
		// Execute the query.
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		@SuppressWarnings("unchecked")
		List<BusinessCategory> businessCatgs = (List<BusinessCategory>) query.list();
		
		return businessCatgs;
	}

	@Override
	public boolean businessCategoryExists(String id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BusinessCategory.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

}
