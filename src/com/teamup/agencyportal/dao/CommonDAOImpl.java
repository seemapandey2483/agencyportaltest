package com.teamup.agencyportal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class CommonDAOImpl implements CommonDAO {
	static Logger log = Logger.getLogger(CommonDAOImpl.class);
	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public List<Object> getAllObjectsByClassName(String className, Class cls) {
		List<Object> objects = null;
		try {
			String query =  "Select a From " + className + " a";
			objects = manager.createQuery(query,cls).getResultList();
		} catch (Exception e) {
			log.error("getAllObjectsByClassName=" + e);
		}
		return objects;
	}

	@Transactional
	@Override
	public boolean addObject(Object object) {
		try {
			manager.persist(object);
			manager.flush();
			return true;
		} catch (Exception e) {
			log.error("addObject=" + e);
			return false;
		}
	}

	@Transactional
	@Override
	public List<Object> getAllObjectsByQuery(String query, Class cls) {
		List<Object> list = null;
		try {
			log.info(query);
			Query q = null;
			if (cls != null)
				q = manager.createQuery(query, cls);
			else
				q = manager.createQuery(query);
			if (q != null)
				list = q.getResultList();
			

		} catch (Exception e) {
			log.error(query + " failed getAllObjectsByQueryWithquery=" + e);
		}

		return list;
	}

	@Transactional
	@Override
	public Object getObjectByID(Class clas, int id) {
		Object obj = null;
		try {
			obj = manager.find(clas, id);
		} catch (Exception e) {
			log.error("getObjectByID=" + e);
		}
		return obj;
	}

	@Transactional
	@Override
	public void updateByObject(Object object) {
		try {
			manager.merge(object);
			manager.flush();
		} catch (Exception e) {
			log.error("updateByObject=" + e);
		}
	}

	@Transactional
	@Override
	public void updateByQuery(String query) {
		try {
			log.info(query);
			manager.createQuery(query).executeUpdate();
		} catch (Exception e) {
			log.error("updateObjectByQuery=" + e);
		}
	}

	@Transactional
	@Override
	public void deleteObject(Object object) {
		try {
			manager.remove(manager.contains(object) ? object : manager
					.merge(object));
			manager.flush();
		} catch (Exception e) {
			log.error("deleteObject=" + e);
		}
	}

	@Transactional
	@Override
	public Object getObjectByQuery(String str, Class cls) {
		Object result = null;
		try {
			
			Query query = manager.createQuery(str, cls);
			if (query != null)
				result = query.getSingleResult();

		} catch (Exception e) {
			log.error(str + " Failed query=" + e);
			return result;
		}
		return result;

	}

	@Override
	public List getAllObjectsByQuery(String query) {
		
		List list = null;
		try {
			log.info(query);
			Query q =  manager.createQuery(query);
			if (q != null)
				list = q.getResultList();

		} catch (Exception e) {
			log.error(query + " failed getAllObjectsByQueryOnly=" + e);
		}

		return list;
	}
	
	@Transactional
	@Override
	public List<Object> getAllObjectsForPaging(String query,Class cls,int startIndex,int totalRecords) {
		List<Object> objects = null;
		try {
			Query qur = null;
			if(cls!=null)
				qur = manager.createQuery(query, cls);
			else
				qur = manager.createQuery(query);
			if ( qur != null ) 
				objects = (List<Object>)qur.setMaxResults(totalRecords).setFirstResult(startIndex * totalRecords).getResultList();
			} catch (Exception e) {
			log.error(query+" getAllObjectsForPaging=" + e);
		}
		return objects;
	}
	
}