package com.sdd.sindikasi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.sindikasi.dao.MdeclinecodeDAO;
import com.sdd.sindikasi.domain.Mdeclinecode;

public class MdeclinecodeListModel extends AbstractPagingListModel<Mdeclinecode> implements Sortable<Mdeclinecode> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Mdeclinecode> oList;  

	public MdeclinecodeListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Mdeclinecode> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		MdeclinecodeDAO oDao = new MdeclinecodeDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		MdeclinecodeDAO oDao = new MdeclinecodeDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	public void sort(Comparator<Mdeclinecode> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	public String getSortDirection(Comparator<Mdeclinecode> cmpr) {
		return null;
	}
}
