package es.udc.rs.app.model.dao.aptitude;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.AptitudeType;

@Repository
public class AptitudeTypeDAOImpl implements AptitudeTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public AptitudeType find(String id) {
		AptitudeType aptitudeType = (AptitudeType) sessionFactory.getCurrentSession().get(AptitudeType.class, id);	
		return aptitudeType;
	}
	
	@Override
	public List<AptitudeType> findAll() {
		// Create the query.
		String query = "FROM AptitudeType A ORDER BY A.name ASC";

		// Get the persons
		@SuppressWarnings("unchecked")
		List<AptitudeType> aptitudeTypes = (List<AptitudeType>) sessionFactory.getCurrentSession().createQuery(query).list();

		return aptitudeTypes;
	}

}
