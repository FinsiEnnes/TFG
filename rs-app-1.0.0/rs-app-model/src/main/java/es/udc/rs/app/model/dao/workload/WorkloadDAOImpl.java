package es.udc.rs.app.model.dao.workload;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.udc.rs.app.model.domain.HistoryPerson;
import es.udc.rs.app.model.domain.Task;
import es.udc.rs.app.model.domain.Workload;

@Repository
public class WorkloadDAOImpl implements WorkloadDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Long create(Workload workload) {
		
		if (workload.getId() != null) {
			throw new RuntimeException("The object is already persistent.");
		}
		
		Long id = (Long) sessionFactory.getCurrentSession().save(workload);
		return id;
	}

	@Override
	public Workload find(Long id) {
		Workload workload = (Workload) sessionFactory.getCurrentSession().get(Workload.class, id);
		return workload;
	}

	@Override
	public List<Workload> findByTask(Task task) {
		
		// Create the queryString 
		String queryString = "FROM Workload W WHERE W.task =:task ORDER BY W.dayDate";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("task", task);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Workload> workloads = query.list();
		
		return workloads;
	}

	@Override
	public List<Workload> findByHistoryPerson(HistoryPerson historyPerson) {
		// Create the queryString 
		String queryString = "FROM Workload W WHERE W.historyPerson =:historyPerson ORDER BY W.dayDate";
		
		// Execute the query
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("historyPerson", historyPerson);
		
		// Get the result
		@SuppressWarnings("unchecked")
		List<Workload> workloads = query.list();
		
		return workloads;
	}

	@Override
	public boolean WorkloadExists(Long id) {
		// Create the criteria in function of the id.
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Workload.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setProjection(Projections.rowCount());
		
		// Get the number of rows.
	    long count = (Long) criteria.uniqueResult();
	    
	    return (count==1L);	
	}

	@Override
	public void update(Workload workload) {
		
		if (workload.getId() == null) {
			throw new RuntimeException("The object is no persistent.");
		}
		
		sessionFactory.getCurrentSession().update(workload);		
	}

	@Override
	public void remove(Workload workload) {
		sessionFactory.getCurrentSession().delete(workload);			
	}

}
