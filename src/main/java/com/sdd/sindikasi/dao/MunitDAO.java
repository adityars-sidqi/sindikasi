package com.sdd.sindikasi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.sindikasi.domain.Munit;
import com.sdd.utils.db.StoreHibernateUtil;

public class MunitDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Munit> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Munit> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		Query query = session.createQuery("from Munit where " + filter + " order by " + orderby);
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Munit "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Munit> listByFilter(String filter, String orderby) throws Exception {		
    	List<Munit> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Munit where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public Munit findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Munit oForm = (Munit) session.createQuery("from Munit where Munitpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Munit findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Munit oForm = (Munit) session.createQuery("from Munit where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Munit order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Munit oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Munit oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

