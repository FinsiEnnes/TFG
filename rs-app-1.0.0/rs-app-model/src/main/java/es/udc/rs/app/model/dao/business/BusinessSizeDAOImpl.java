package es.udc.rs.app.model.dao.business;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

}
