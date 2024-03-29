package com.sdd.sindikasi.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sdd.sindikasi.dao.MuserDAO;
import com.sdd.sindikasi.dao.MusergroupDAO;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Musergroup;
import com.sdd.utils.StringUtils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

public class AppData {

	public static List<Musergroup> getMusergroup(String filter) throws Exception {
		List<Musergroup> list = new ArrayList<Musergroup>();
		if (filter.equals(null) || filter.trim().length() < 0)
			filter = "0=0";
		list = new MusergroupDAO().listByFilter(filter, "usergroupname");
		return list;
	}

	public static List<Muser> getMuser(String filter) throws Exception {
		List<Muser> list = new ArrayList<Muser>();
		if (filter.equals(null) || filter.trim().length() < 0)
			filter = "0=0";
		list = new MuserDAO().listByFilter(filter, "userid");
		return list;
	}

	public static String getStatusLabel(String code) {
		String label = "";
		if (code.equals(AppUtils.STATUS_WAITING_PROSES))
			label = "WAITING PROSES";
		else if (code.equals(AppUtils.STATUS_DONE))
			label = "DONE";
		return label;
	}

	public static String getStatusPipelineLabel(String code) {
		String label = "";
		if (code.equals(AppUtils.STATUS_WAITING_APPROVAL))
			label = "WAITING APPROVAL";
		else if (code.equals(AppUtils.STATUS_PROGRESS))
			label = "PROGRESS";
		else if (code.equals(AppUtils.STATUS_DECLINE))
			label = "DECLINE";
		else if (code.equals(AppUtils.STATUS_APPROVE))
			label = "APPROVE";
		return label;
	}
	
	public static String getPaytypeLabel(String code) {
		String label = "";
		if (code.equals(AppUtils.PAYTYPE_POKOK))
			label = "POKOK";
		else if (code.equals(AppUtils.PAYTYPE_BUNGA))
			label = "BUNGA";
		else if (code.equals(AppUtils.PAYTYPE_FEE))
			label = "FEE";
		return label;
	}
	
	public static String getAgentType(String isagentfac, String isagentcol, String isagentesc) {
		String agents = "";
		if (isagentfac.equals("Y"))
			agents += "Facility";
		if (isagentcol.equals("Y")) {
			if (agents.length() > 0)
				agents += ", ";
			agents += "Collateral";
		}
		if (isagentesc.equals("Y")) {
			if (agents.length() > 0)
				agents += ", ";
			agents += "Escrow";
		}
		return agents;
	}
	
	public static BigDecimal getAmountAfterDivide(BigDecimal amount) {
		return amount.divide(AppUtils.AMOUNT_DIVIDER);
	}
	
	public static String getMonthPaySchedule(String months) {
		String label = "";
		String[] arMonth = months.split("\\;");
		for (String month: arMonth) {
			label += StringUtils.getMonthshortLabel(Integer.parseInt(month)) + " ";
		}
		if (label.length() > 0)
			label = label.substring(0, label.length()-1);
		return label;
	}	

	public static void doDisableComponent(Boolean value, Div divRoot) {
		// for dibawah ini akan iterasi isi divForm
		for (Component compRoot : divRoot.getChildren()) {
			if (compRoot.getId().equals("gbMemo"))
				continue;
			// for dibawah ini akan iterasi isi groupbox
			for (Component compChild : compRoot.getChildren()) {

				// for dibawah ini akan iterasi isi grid
				for (Component compGrandChild : compChild.getChildren()) {

					// for dibawah ini akan iterasi row
					for (Component compChildGrandChild : compGrandChild.getChildren()) {
						for (Component compGrandChildGrandChild : compChildGrandChild.getChildren()) {
							for (Component comp : compGrandChildGrandChild.getChildren()) {

								if (comp instanceof Textbox) {
									((Textbox) comp).setReadonly(value);
									if (comp instanceof Combobox) {
										((Combobox) comp).setButtonVisible(!value);
										((Combobox) comp).setReadonly(value);
									}
								} else if (comp instanceof Datebox) {
									((Datebox) comp).setButtonVisible(!value);
									((Datebox) comp).setReadonly(value);
								} else if (comp instanceof Decimalbox) {
									((Decimalbox) comp).setReadonly(value);
								}

							}
						}

					}
				}
			}
		}
	}
}
