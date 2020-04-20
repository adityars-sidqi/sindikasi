package com.sdd.sindikasi.viewmodel;

import com.sdd.sindikasi.dao.MsectorDAO;
import com.sdd.sindikasi.dao.MunitDAO;
import com.sdd.sindikasi.domain.Msector;
import com.sdd.sindikasi.domain.Munit;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Tporto;
import com.sdd.utils.SysUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.event.PagingEvent;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportPortoVm {

    private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
    private Muser oUser;

    private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
    private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

    private Msector msector;
    private Munit munit;
    private Date month;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        oUser = (Muser) zkSession.getAttribute("oUser");

        doReset();

    }

    @Command
    @NotifyChange("*")
    public void doReset() {
        msector = null;
        munit = null;
    }

    public ListModelList<Msector> getMsectors() {
        ListModelList<Msector> lm = null;
        try {
            lm = new ListModelList<Msector>(new MsectorDAO().listByFilter("0=0", "sectorname"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lm;
    }

    public ListModelList<Munit> getMunits() {
        ListModelList<Munit> lm = null;
        try {
            lm = new ListModelList<Munit>(new MunitDAO().listByFilter("0=0", "unitcode"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lm;
    }

    public Msector getMsector() {
        return msector;
    }

    public void setMsector(Msector msector) {
        this.msector = msector;
    }

    public Munit getMunit() {
        return munit;
    }

    public void setMunit(Munit munit) {
        this.munit = munit;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
