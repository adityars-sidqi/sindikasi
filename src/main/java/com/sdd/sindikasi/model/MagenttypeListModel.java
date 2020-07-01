package com.sdd.sindikasi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.sindikasi.dao.MagenttypeDAO;
import com.sdd.sindikasi.domain.Magenttype;

public class MagenttypeListModel extends AbstractPagingListModel<Magenttype> implements Sortable<Magenttype> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Magenttype> oList;  

	public MagenttypeListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Magenttype> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		MagenttypeDAO oDao = new MagenttypeDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MagenttypeDAO oDao = new MagenttypeDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	public void sort(Comparator<Magenttype> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	public String getSortDirection(Comparator<Magenttype> cmpr) {
		return null;
	}
}
