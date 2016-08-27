package es.udc.rs.app.model.dao.business;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.BusinessSize;

@Repository
public class BusinessSizeDAOImpl implements BusinessSizeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public BusinessSize find(String id) {
		BusinessSize bs = (BusinessSize) sessionFactory.getCurrentSession().get(BusinessSize.class, id);
		return bs;
	}

	@Override
	public List<BusinessSize> findAll() {
		
		// Create the query
		String queryString = "FROM BusinessSize B ORDER BY B.name";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		
		@SuppressWarnings("unchecked")
		List<BusinessSize> bss = (List<BusinessSize>) query.list();
		
		return bss;
	}

	@Override
	public boolean businessSizeExists(String id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BusinessSize.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

}
