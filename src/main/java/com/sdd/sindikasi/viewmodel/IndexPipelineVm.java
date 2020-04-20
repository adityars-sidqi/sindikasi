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
import com.sdd.sindikasi.domain.Vsummary;
import com.sdd.sindikasi.utils.AppUtils;

public class IndexPipelineVm {
	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datesystemFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private TpipelineDAO tpipelineDao = new TpipelineDAO();

	@Wire
	private Window windowPipeline;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		int i = 0;
		int dataPerRow = 3;
		int iColor = 1;

		int totaldata = 0;
		BigDecimal amountdata = new BigDecimal(0);
		int totalprogess = 0;
		BigDecimal amountprogress = new BigDecimal(0);
		int totalapprove = 0;
		BigDecimal amountapprove = new BigDecimal(0);
		int totaldecline = 0;
		BigDecimal amountdecline = new BigDecimal(0);

		Div divRow = null;
		Div divCol = null;
		try {
			String filterprogress = "status = '" + AppUtils.STATUS_PROGRESS + "'";
			String filterapprove = "status = '" + AppUtils.STATUS_APPROVE + "'";
			String filterdecline = "status = '" + AppUtils.STATUS_DECLINE + "'";
			
			for(Vsummary obj : tpipelineDao.listSummary("0=0")) {
				totaldata = obj.getTotaldata();
				amountdata = obj.getAmounttotal();
			}
			for(Vsummary obj : tpipelineDao.listSummary(filterprogress)) {
				totalprogess = obj.getTotaldata();
				amountprogress = obj.getAmounttotal();
			}
			for(Vsummary obj : tpipelineDao.listSummary(filterapprove)) {
				totalapprove = obj.getTotaldata();
				amountapprove = obj.getAmounttotal();
			}
			for(Vsummary obj : tpipelineDao.listSummary(filterdecline)) {
				totaldecline = obj.getTotaldata();
				amountdecline = obj.getAmounttotal();
			}
			
			if (i % dataPerRow == 0) {
				divRow = new Div();
				divRow.setClass("row");

				divRow.setParent(windowPipeline);
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
			hr.setClientAttribute("style", "color: white");
			divCardbody.appendChild(hr);
			addContentTotal(divCardbody, "Total Progress", totalprogess, totaldata);
			addContentAmount(divCardbody, "Total Amount Progress", amountprogress, amountdata);
			addContentTotal(divCardbody, "Total Approve", totalapprove, totaldata);
			addContentAmount(divCardbody, "Total Amount Approve", amountapprove, amountdata);
			addContentTotal(divCardbody, "Total Decline", totaldecline, totaldata);
			addContentAmount(divCardbody, "Total Amount Decline", amountdecline, amountdata);

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
