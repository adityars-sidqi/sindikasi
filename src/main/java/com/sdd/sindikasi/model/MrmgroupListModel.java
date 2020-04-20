package com.sdd.sindikasi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.sindikasi.dao.MrmgroupDAO;
import com.sdd.sindikasi.domain.Mrmgroup;

public class MrmgroupListModel extends AbstractPagingListModel<Mrmgroup> implements Sortable<Mrmgroup> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Mrmgroup> oList;  

	public MrmgroupListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Mrmgroup> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		MrmgroupDAO oDao = new MrmgroupDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MrmgroupDAO oDao = new MrmgroupDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	public void sort(Comparator<Mrmgroup> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	public String getSortDirection(Comparator<Mrmgroup> cmpr) {
		return null;
	}
}
