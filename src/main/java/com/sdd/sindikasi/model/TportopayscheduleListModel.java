package com.sdd.sindikasi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.sindikasi.dao.TportopayscheduleDAO;
import com.sdd.sindikasi.domain.Tportopayschedule;

public class TportopayscheduleListModel extends AbstractPagingListModel<Tportopayschedule> implements Sortable<Tportopayschedule> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Tportopayschedule> oList;  

	public TportopayscheduleListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tportopayschedule> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TportopayscheduleDAO oDao = new TportopayscheduleDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TportopayscheduleDAO oDao = new TportopayscheduleDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	public void sort(Comparator<Tportopayschedule> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	public String getSortDirection(Comparator<Tportopayschedule> cmpr) {
		return null;
	}
}
