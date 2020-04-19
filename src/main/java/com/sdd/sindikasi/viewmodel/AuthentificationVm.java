package com.sdd.sindikasi.viewmodel;

import org.hibernate.Session;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

import com.sdd.sindikasi.dao.MuserDAO;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.utils.SysUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class AuthentificationVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private String userid;
	private String password;
	private String lblMessage;

	private MuserDAO oDao = new MuserDAO();

	private Session session;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange("lblMessage")
	public void doLogin() {
		try {
			if (userid != null && !userid.trim().equals("") && password != null && !password.trim().equals("")) {
				session = StoreHibernateUtil.openSession();
				Muser oForm = oDao.login(session, userid);
				if (oForm != null) {

					if (password != null)
						password = password.trim();
					String passencrypted = SysUtils.encryptionCommand(password);
					if (oForm.getPassword().equals(passencrypted)) {
						zkSession.setAttribute("oUser", oForm);
						Executions.sendRedirect("/view/index.zul");
					} else {
						lblMessage = "Invalid your password";
					}
				} else {
					lblMessage = "Invalid your login id";
				}
				session.close();
			}
		} catch (Exception e) {
			lblMessage = "Error : " + e.getMessage();
			e.printStackTrace();
		}
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLblMessage() {
		return lblMessage;
	}

	public void setLblMessage(String lblMessage) {
		this.lblMessage = lblMessage;
	}

}
