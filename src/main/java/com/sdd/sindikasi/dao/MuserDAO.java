package com.sdd.sindikasi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.sindikasi.domain.Muser;
import com.sdd.utils.db.StoreHibernateUtil;

public class MuserDAO {

	private Session session;

	public Muser login(Session session, String loginid) throws HibernateException, Exception {
		Muser oForm = null;
		if (loginid != null)
			loginid = loginid.trim().toUpperCase();
		oForm = (Muser) session.createQuery("from Muser where userid = '" + loginid + "'").uniqueResult();
		return oForm;
	}

	@SuppressWarnings("unchecked")
	public List<Muser> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Muser> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		Query query = session.createQuery("from Muser where " + filter + " order by " + orderby);
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
		count = Integer.parseInt((String) session.createSQLQuery(
				"select count(*) from Muser join Musergroup on musergroupfk = musergrouppk " + "where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Muser> listByFilter(String filter, String orderby) throws Exception {
		List<Muser> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Muser where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}

	public Muser findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Muser oForm = (Muser) session.createQuery("from Muser where muserpk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Muser order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Muser oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Muser oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

}
