package com.sdd.sindikasi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.sindikasi.dao.McurrencyDAO;
import com.sdd.sindikasi.domain.Mcurrency;

public class McurrencyListModel extends AbstractPagingListModel<Mcurrency> implements Sortable<Mcurrency> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Mcurrency> oList;  

	public McurrencyListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Mcurrency> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		McurrencyDAO oDao = new McurrencyDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		McurrencyDAO oDao = new McurrencyDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	public void sort(Comparator<Mcurrency> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	public String getSortDirection(Comparator<Mcurrency> cmpr) {
		return null;
	}
}
