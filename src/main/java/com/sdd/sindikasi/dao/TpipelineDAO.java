package com.sdd.sindikasi.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Vpipelineperiod;
import com.sdd.sindikasi.domain.Vpipelinesector;
import com.sdd.sindikasi.domain.Vpipelineunit;
import com.sdd.sindikasi.domain.Vsummary;
import com.sdd.utils.db.StoreHibernateUtil;

public class TpipelineDAO {

	private Session session;

	@SuppressWarnings("unchecked")
	public List<Tpipeline> listPaging(int first, int second, String filter, String orderby) throws Exception {
		List<Tpipeline> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tpipeline join Mdebitur on mdebiturfk = mdebiturpk "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(Tpipeline.class).list();	
		return oList;
	}

	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(tpipelinepk) from Tpipeline join Mdebitur on mdebiturfk = mdebiturpk where " + filter)
				.uniqueResult().toString());
		session.close();
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Tpipeline> listByFilter(String filter, String orderby) throws Exception {
		List<Tpipeline> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from Tpipeline where " + filter + " order by " + orderby).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tpipeline> listNativeByFilter(String filter, String orderby) throws Exception {
		List<Tpipeline> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		oList = session.createSQLQuery("select * from Tpipeline join Mdebitur on mdebiturfk = mdebiturpk where " + filter + " order by " + orderby).addEntity(Tpipeline.class).list();
		session.close();
		return oList;
	}

	public Tpipeline findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpipeline oForm = (Tpipeline) session.createQuery("from Tpipeline where Tpipelinepk = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public Tpipeline findByFilter(String filter) throws Exception {
		session = StoreHibernateUtil.openSession();
		Tpipeline oForm = (Tpipeline) session.createQuery("from Tpipeline where " + filter).uniqueResult();
		session.close();
		return oForm;
	}

	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
		session = StoreHibernateUtil.openSession();
		oList = session.createQuery("select " + fieldname + " from Tpipeline order by " + fieldname).list();
		session.close();
		return oList;
	}

	public void save(Session session, Tpipeline oForm) throws HibernateException, Exception {
		session.saveOrUpdate(oForm);
	}

	public void delete(Session session, Tpipeline oForm) throws HibernateException, Exception {
		session.delete(oForm);
	}

	@SuppressWarnings("unchecked")
	public List<Vsummary> listSummary(String filter) throws Exception {
		List<Vsummary> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";

		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery(
						"SELECT count(*) as totaldata,  coalesce(sum(projectamount),0)  as amounttotal FROM TPIPELINE WHERE " + filter)
				.addEntity(Vsummary.class).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vpipelineunit> getSumByUnit(String filter) throws Exception {
		List<Vpipelineunit> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";

		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery(
						"select munitpk, isbumn, unitcode, count(tpipelinepk) as tdebitur, sum(projectamount) as tpc, sum(creditfacility) as tcredit, sum(selfportion) as tsp, sum(feeamount) as tfee "
						+ "from munit left join tpipeline on munitpk = munitfk where " + filter + " group by munitpk, isbumn, unitcode order by isbumn desc, unitcode")
				.addEntity(Vpipelineunit.class).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vpipelinesector> getSumBySector(String filter) throws Exception {
		List<Vpipelinesector> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";

		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery(
						"select msectorpk, sectorname, count(tpipelinepk) as tdebitur, sum(projectamount) as tpc, sum(creditfacility) as tcredit, sum(selfportion) as tsp, sum(feeamount) as tfee "
						+ "from msector left join tpipeline on msectorpk = msectorfk where " + filter + " group by msectorpk, sectorname order by msectorpk")
				.addEntity(Vpipelinesector.class).list();
		session.close();
		return oList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vpipelineperiod> getSumByPeriod(String filter) throws Exception {
		List<Vpipelineperiod> oList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";

		session = StoreHibernateUtil.openSession();
		oList = session
				.createSQLQuery(
						"select yearperiod, qperiod, munitpk, isbumn, unitcode, count(tpipelinepk) as tdebitur, sum(projectamount) as tpc, sum(creditfacility) as tcredit, sum(selfportion) as tsp, sum(feeamount) as tfee "
						+ "from munit left join tpipeline on munitpk = munitfk where " + filter + " group by yearperiod, qperiod, munitpk, isbumn, unitcode order by yearperiod, qperiod, isbumn desc, unitcode")
				.addEntity(Vpipelineperiod.class).list();
		session.close();
		return oList;
	}

}
