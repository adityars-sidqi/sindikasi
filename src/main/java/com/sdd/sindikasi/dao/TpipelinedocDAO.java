package com.sdd.sindikasi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.sindikasi.domain.Tpipelinedoc;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpipelinedocDAO {
	
	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpipelinedoc> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tpipelinedoc> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		Query query = session.createQuery("from Tpipelinedoc where " + filter + " order by " + orderby);
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
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from Tpipelinedoc "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<Tpipelinedoc> listByFilter(String filter, String orderby) throws Exception {		
    	List<Tpipelinedoc> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpipelinedoc where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	@SuppressWarnings("unchecked")
	public List<Tpipelinedoc> listNativeByFilter(String filter, String orderby) throws Exception {		
    	List<Tpipelinedoc> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tpipelinedoc join Tpipeline on tpipelinefk = tpipelinepk join Mdebitur on mdebiturfk = mdebiturpk where " + filter + " order by " + orderby).addEntity(Tpipelinedoc.class).list();
		session.close();
        return oList;
    }	
	
	public Tpipelinedoc findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpipelinedoc oForm = (Tpipelinedoc) session.createQuery("from Tpipelinedoc where Tpipelinedocpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	public Tpipelinedoc findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpipelinedoc oForm = (Tpipelinedoc) session.createQuery("from Tpipelinedoc where " + filter).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from Tpipelinedoc order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session, Tpipelinedoc oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}
	
	public void delete(Session session, Tpipelinedoc oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

}

