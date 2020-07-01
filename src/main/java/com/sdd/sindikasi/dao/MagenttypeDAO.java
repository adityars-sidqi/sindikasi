package com.sdd.sindikasi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.sindikasi.domain.Magenttype;
import com.sdd.utils.db.StoreHibernateUtil;

public class MagenttypeDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Magenttype> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Magenttype> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		Query query = session.createQuery("from Magenttype where " + filter + " order by " + orderby);
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Magenttype "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Magenttype> listByFilter(String filter, String orderby) throws Exception {		
    	List<Magenttype> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Magenttype where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Magenttype findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Magenttype oForm = (Magenttype) session.createQuery("from Magenttype where Magenttypepk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Magenttype findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Magenttype oForm = (Magenttype) session.createQuery("from Magenttype where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Magenttype order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Magenttype oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Magenttype oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

