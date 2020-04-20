package com.sdd.sindikasi.viewmodel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.dao.TportoDAO;
import com.sdd.sindikasi.domain.Vsummary;
import com.sdd.sindikasi.utils.AppUtils;

public class IndexPortofolioVm {
	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datesystemFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private TportoDAO tportoDao = new TportoDAO();

	@Wire
	private Window windowPortofolio;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		int i = 0;
		int dataPerRow = 3;
		int iColor = 2;

		int totaldata = 0;
		BigDecimal amountdata = new BigDecimal(0);

		Div divRow = null;
		Div divCol = null;
		try {

			for (Vsummary obj : tportoDao.listSummary("0=0")) {
				totaldata = obj.getTotaldata();
				amountdata = obj.getAmounttotal();
			}

			if (i % dataPerRow == 0) {
				divRow = new Div();
				divRow.setClass("row");

				divRow.setParent(windowPortofolio);
			}
			divCol = new Div();
			divCol.setClass("col-md-5");
			divCol.setParent(divRow);

			Div divCard = new Div();
			divCard.setClass("card text-white " + AppUtils.COLORS[iColor] + " mb-3");
			divCard.setParent(divCol);
			Div divCardheader = new Div();
			divCardheader.setClass("card-header");
			divCardheader.setParent(divCard);

			Div divCardbody = new Div();
			divCardbody.setClass("card-body");
			divCardbody.setParent(divCard);

			addContentTotal(divCardbody, "Total Data", totaldata, totaldata);
			addContentAmount(divCardbody, "Total Amount", amountdata, amountdata);
			HtmlNativeComponent hr = new HtmlNativeComponent("hr");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addContentTotal(Component parent, String prologue, int qty, int qtytotal) throws Exception {
		Div divBodyRow = new Div();
		divBodyRow.setClass("row");
		divBodyRow.setParent(parent);
		Div divBodyCol = new Div();
		divBodyCol.setClass("col-6");
		divBodyCol.setParent(divBodyRow);
		HtmlNativeComponent nativecomp = new HtmlNativeComponent("h6");
		nativecomp.setClientAttribute("style", "color: white");
		nativecomp.setPrologContent(prologue);
		nativecomp.setParent(divBodyCol);
		divBodyCol = new Div();
		divBodyCol.setClass("col-3");
		divBodyCol.setAlign("right");
		divBodyCol.setParent(divBodyRow);
		nativecomp = new HtmlNativeComponent("h6");
		nativecomp.setClientAttribute("style", "color: white");
		nativecomp.setPrologContent(NumberFormat.getInstance().format(qty));
		nativecomp.setParent(divBodyCol);
		divBodyCol = new Div();
		divBodyCol.setClass("col-3");
		divBodyCol.setAlign("right");
		divBodyCol.setParent(divBodyRow);
		Double val = ((double) qty / (double) qtytotal) * 100;
		nativecomp = new HtmlNativeComponent("h6");
		nativecomp.setClientAttribute("style", "color: white");
		nativecomp.setPrologContent(String.format("%.1f", val) + " %");
		nativecomp.setParent(divBodyCol);
	}

	private void addContentAmount(Component parent, String prologue, BigDecimal qty, BigDecimal qtytotal)
			throws Exception {
		Div divBodyRow = new Div();
		divBodyRow.setClass("row");
		divBodyRow.setParent(parent);
		Div divBodyCol = new Div();
		divBodyCol.setClass("col-6");
		divBodyCol.setParent(divBodyRow);
		HtmlNativeComponent nativecomp = new HtmlNativeComponent("h6");
		nativecomp.setClientAttribute("style", "color: white");
		nativecomp.setPrologContent(prologue);
		nativecomp.setParent(divBodyCol);
		divBodyCol = new Div();
		divBodyCol.setClass("col-3");
		divBodyCol.setAlign("right");
		divBodyCol.setParent(divBodyRow);
		nativecomp = new HtmlNativeComponent("h6");
		nativecomp.setClientAttribute("style", "color: white");
		nativecomp.setPrologContent(NumberFormat.getInstance().format(qty));
		nativecomp.setParent(divBodyCol);
		divBodyCol = new Div();
		divBodyCol.setClass("col-3");
		divBodyCol.setAlign("right");
		divBodyCol.setParent(divBodyRow);
		Double val = ((double) qty.doubleValue() / (double) qtytotal.doubleValue()) * 100;
		nativecomp = new HtmlNativeComponent("h6");
		nativecomp.setClientAttribute("style", "color: white");
		nativecomp.setPrologContent(String.format("%.1f", val) + " %");
		nativecomp.setParent(divBodyCol);
	}
}
