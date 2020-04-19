package com.sdd.sindikasi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.sindikasi.domain.Tportopart;
import com.sdd.utils.db.StoreHibernateUtil;

public class TportopartDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tportopart> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tportopart> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		Query query = session.createQuery("from Tportopart where " + filter + " order by " + orderby);
		query.setFirstResult(first* second);
		query.setMaxResults(second);

		oList = query.getResultList();
		return oList;
	}	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tportopart "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tportopart> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tportopart> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tportopart where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Tportopart findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tportopart oForm = (Tportopart) session.createQuery("from Tportopart where Tportopartpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tportopart findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tportopart oForm = (Tportopart) session.createQuery("from Tportopart where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tportopart order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tportopart oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tportopart oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

