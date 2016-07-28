package es.udc.rs.app.model.dao.historyproject;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.HistoryProject;
import es.udc.rs.app.model.domain.Project;

@Repository
public class HistoryProjectDAOImpl implements HistoryProjectDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(HistoryProject historyProject) {
		
		if (historyProject.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(historyProject);
		return id;
	}

	@Override
	public HistoryProject find(Long id) {
		Session s = sessionFactory.getCurrentSession();
		HistoryProject historyProject = (HistoryProject) s.get(HistoryProject.class, id);
		
		return historyProject;
	}

	@Override
	public List<HistoryProject> findByProject(Project project) {
		
		// Create the query
		String queryString = "FROM HistoryProject H WHERE H.project = :project ORDER BY H.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<HistoryProject> historiesproject = (List<HistoryProject>) query.list();
		
		return historiesproject;
	}

	@Override
	public HistoryProject findCurrentHistoryProject(Project project) {
		// Create the query
		String queryString = "FROM HistoryProject H "
						   + "WHERE H.id = (SELECT max(P.id) FROM HistoryProject P WHERE P.project = :project)"
						   + "ORDER BY H.id";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("project", project);

		// Get the result
		HistoryProject historyProject = (HistoryProject) query.list().get(0);
		return historyProject;
	}

	@Override
	public boolean HistoryProjectExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HistoryProject.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(HistoryProject historyProject) {
		if (historyProject.getId() == null) {
			throw new RuntimeException("The object is not persistent.");
		}
		
		sessionFactory.getCurrentSession().update(historyProject);
		
	}

	@Override
	public void remove(HistoryProject historyProject) {
		sessionFactory.getCurrentSession().delete(historyProject);		
	}

}
