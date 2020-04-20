package com.sdd.sindikasi.dao;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class TcounterengineDAO {
	public String getLastCounter(String counterName) throws Exception {
		Integer lastCounter = 0;
		String strCounter = "";
		String finalCounter = "";
		char[] fillUploadid = new char[AppUtils.JOBID_DIGIT_LENGTH];
		Session session = StoreHibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			counterName = counterName + new SimpleDateFormat("ddMM").format(new Date());
			Query q = session
					.createQuery("select lastcounter from Tcounterengine where countername = '" + counterName + "'");
			lastCounter = (Integer) q.uniqueResult();
			if (lastCounter != null) {
				lastCounter++;
				session.createSQLQuery("update Tcounterengine set lastcounter = lastcounter + 1 where countername = '"
						+ counterName + "'").executeUpdate();
			} else {
				lastCounter = 1;
				session.createSQLQuery("insert into Tcounterengine values ('" + counterName + "', " + lastCounter + ")")
						.executeUpdate();
			}
			transaction.commit();
			session.close();
			Arrays.fill(fillUploadid, '0');
			strCounter = new String(fillUploadid) + lastCounter;
			finalCounter = counterName
					+ strCounter.substring(strCounter.length() - AppUtils.JOBID_DIGIT_LENGTH, strCounter.length());
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return finalCounter;
	}
}
