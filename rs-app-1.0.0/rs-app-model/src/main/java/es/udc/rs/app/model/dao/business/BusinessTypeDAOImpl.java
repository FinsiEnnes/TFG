package es.udc.rs.app.model.dao.business;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.BusinessType;

@Repository
public class BusinessTypeDAOImpl implements BusinessTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public BusinessType find(String id) {
		BusinessType bt = (BusinessType) sessionFactory.getCurrentSession().get(BusinessType.class, id);
		return bt;
	}

	@Override
	public List<BusinessType> findAll() {
		String query = "FROM BusinessType B ORDER BY B.name";
		
		@SuppressWarnings("unchecked")
		List<BusinessType> bts = (List<BusinessType>) sessionFactory.getCurrentSession()
													 				.createQuery(query).list();
		
		return bts;
	}

	@Override
	public boolean businessTypeExists(String id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BusinessType.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}
	

}
