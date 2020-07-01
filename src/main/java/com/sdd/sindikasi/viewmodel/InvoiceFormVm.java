package com.sdd.sindikasi.viewmodel;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.event.PagingEvent;

import com.sdd.sindikasi.domain.Tpipeline;
import com.sdd.sindikasi.domain.Tportopayschedule;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sdd.sindikasi.dao.MsysparamDAO;
import com.sdd.sindikasi.dao.TcounterengineDAO;
import com.sdd.sindikasi.domain.Msysparam;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.model.TpipelineListModel;
import com.sdd.sindikasi.model.TportopayscheduleListModel;
import com.sdd.sindikasi.utils.AppData;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.SysUtils;

public class InvoiceFormVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private SimpleDateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat datetimeLocalFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
	private DecimalFormat decimalLocalFormatter = new DecimalFormat("#,###");

	private int pageTotalSize;
	private Date invoicedate;
	private List<Tportopayschedule> objList;

	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objList") List<Tportopayschedule> objList) {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");
		this.objList = objList;
		pageTotalSize = objList.size();
		invoicedate = new Date();
		grid.setModel(new ListModelList<Tportopayschedule>(objList));
		grid.setRowRenderer(new RowRenderer<Tportopayschedule>() {

			public void render(Row row, Tportopayschedule data, int index) throws Exception {
				row.getChildren().add(new Label(String.valueOf(index + 1)));
				A a = new A(data.getTporto().getProject());
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {

					}
				});
				row.getChildren().add(a);
				row.getChildren().add(new Label(data.getTporto().getDebitur()));
				row.getChildren().add(new Label(data.getTporto().getMsector().getSectorname()));
				row.getChildren().add(new Label(data.getTporto().getCurrency()));
				row.getChildren().add(new Label(decimalLocalFormatter.format(data.getTporto().getProjectamount())));
				row.getChildren().add(new Label(decimalLocalFormatter.format(data.getTporto().getSelfportion())));
				row.getChildren().add(new Label(decimalLocalFormatter.format(data.getTporto().getFeepercent())));
				row.getChildren().add(new Label(AppData.getPaytypeLabel(data.getPaytype())));
				row.getChildren().add(new Label(AppData.getMonthPaySchedule(data.getMonthschedule())));

				Decimalbox decboxAmount = new Decimalbox();
				decboxAmount.setFormat("#,###");
				decboxAmount.setStyle("text-align: right");
				row.getChildren().add(decboxAmount);
			}
		});
	}

	@Command
	public void doInvoice() {
		if (invoicedate == null) {
			Messagebox.show("Silahkan isi tanggal invoice", "Confirm Dialog", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			Messagebox.show("Anda ingin generate data Invoice?", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener<Event>() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								
								String filename = "INV" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".pdf";
								Font font = new Font(Font.FontFamily.HELVETICA, 10);
								Font fonttable = new Font(Font.FontFamily.HELVETICA, 9);
								String output = Executions.getCurrent().getDesktop().getWebApp().getRealPath(
												AppUtils.PATH_TMP + filename);
								Document document = new Document(new Rectangle(PageSize.A4));
								PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(output));
								document.open();			
							    
								int index = 0;
								for (Tportopayschedule obj : objList) {
									Row row = (Row) grid.getRows().getChildren().get(index++);
									Decimalbox amount = (Decimalbox) row.getChildren().get(row.getChildren().size() - 1);
									doInvoiceGenerator(document, font, fonttable, obj, amount.getValue());
								}
								
							    document.close();		
							    
							    Filedownload.save(new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath(
										AppUtils.PATH_TMP + filename)),
										"application/pdf");
							    							
							}
						}
					});
		}		

	}
	
	private void doInvoiceGenerator(Document document, Font font, Font fonttable, Tportopayschedule obj, BigDecimal amount) throws Exception {
		try {
			String companyname = "";
			String divisiname = "";
			String groupname = "";
			String address1 = "";
			String address2 = "";
			String address3 = "";
			String address4 = "";
			String city = "";
			String pemimpin = "";
			List<Msysparam> objParam = new MsysparamDAO()
					.listByFilter("paramgroup = '" + AppUtils.PARAM_GROUP_COMPANYDATA + "'", "orderno");
			for (Msysparam obj2 : objParam) {
				if (obj2.getParamname().equals(AppUtils.PARAM_COMPANYNAME))
					companyname = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_DIVISINAME))
					divisiname = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_GROUPNAME))
					groupname = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_ADDRESS1))
					address1 = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_ADDRESS2))
					address2 = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_ADDRESS3))
					address3 = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_ADDRESS4))
					address4 = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_CITY))
					city = obj2.getParamvalue();
				else if (obj2.getParamname().equals(AppUtils.PARAM_PEMIMPIN))
					pemimpin = obj2.getParamvalue();				
			}
			
			
			PdfPTable table = null;
			PdfPCell cell = null; 
			
			table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 60,40 });
			cell = new PdfPCell(new Paragraph(companyname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);				
			
			String logo_path = Executions.getCurrent().getDesktop().getWebApp().getRealPath(AppUtils.PATH_RESOURCES_IMAGE + "/corp_logo.png");
		    com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(logo_path);
		    //img.scaleAbsolute(135f, 40f);
		    cell = new PdfPCell(img);
		    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setRowspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);			    
			table.addCell(cell);			
		    
		    cell = new PdfPCell(new Paragraph(divisiname, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);							
			cell = new PdfPCell(new Paragraph(address1, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
		    cell = new PdfPCell(new Paragraph(address2, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
		    cell = new PdfPCell(new Paragraph(address3, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(address4, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			document.add(table);
			
			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15,85 });
			cell = new PdfPCell(new Paragraph("No", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(": " + new TcounterengineDAO().getLastCounter("INV/2020/"), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Tanggal", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(": " + dateLocalFormatter.format(invoicedate), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
		    document.add(table);
		    
		    table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    		    		   
		    table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Kepada", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(obj.getTporto().getDebitur(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(obj.getTporto().getOfficeaddress(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		   
		    table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15,85 });
			cell = new PdfPCell(new Paragraph("Hal", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(": Penagihan", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
		    document.add(table);
		    table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Berikut data tagihannya :" , font));
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15,85 });
			cell = new PdfPCell(new Paragraph("Nama Project", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(": " + obj.getTporto().getProject(), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
		    document.add(table);
		    table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15,85 });
			cell = new PdfPCell(new Paragraph("Tipe Tagihan", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(": " + AppData.getPaytypeLabel(obj.getPaytype()), font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
		    document.add(table);
		    table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 15,85 });
			cell = new PdfPCell(new Paragraph("Jumlah Tagihan", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(": " + amount != null ? decimalLocalFormatter.format(amount) : "-", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
		    document.add(table);
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("Demikianlah agar Saudara dapat menerima dengan baik." , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(companyname , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);					
		    document.add(table);
		    
		    PdfPTable tablefoot = new PdfPTable(2);
		    tablefoot.setWidthPercentage(100);
		    tablefoot.setWidths(new int[] { 60,40 });
		    
		    PdfPCell cellfoot1 = new PdfPCell();
		    cellfoot1.setBorder(PdfPCell.NO_BORDER);
		    table = new PdfPTable(1);
			table.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph(divisiname , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph(groupname , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			document.add(table);
		   
			cell = new PdfPCell();		   
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setRowspan(5);
			cell.setBorder(PdfPCell.NO_BORDER);			    
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("\n", font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);				
			cell = new PdfPCell(new Paragraph(pemimpin, font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cell = new PdfPCell(new Paragraph("Pemimpin Kelompok " , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cellfoot1.addElement(table);
			cell = new PdfPCell(new Paragraph("\n" , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cellfoot1.addElement(table);
		    tablefoot.addCell(cellfoot1);
		    cell = new PdfPCell(new Paragraph("\n" , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cellfoot1.addElement(table);
			cell = new PdfPCell(new Paragraph(pemimpin , font));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);	
			cellfoot1.addElement(table);
		    document.add(tablefoot);
		    			    
		    document.newPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getPageTotalSize() {
		return pageTotalSize;
	}

	public void setPageTotalSize(int pageTotalSize) {
		this.pageTotalSize = pageTotalSize;
	}

	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

}
