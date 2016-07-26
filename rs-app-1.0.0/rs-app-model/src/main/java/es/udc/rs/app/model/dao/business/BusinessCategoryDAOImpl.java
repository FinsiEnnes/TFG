package es.udc.rs.app.model.dao.business;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

}
