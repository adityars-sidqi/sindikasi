package com.sdd.sindikasi.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sdd.sindikasi.dao.TpipelineDAO;
import com.sdd.sindikasi.domain.Tpipeline;

public class TpipelineListModel extends AbstractPagingListModel<Tpipeline> implements Sortable<Tpipeline> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<Tpipeline> oList;  

	public TpipelineListModel(int startPageNumber, int pageSize, String filter, String orderby) {
		super(startPageNumber, pageSize, filter, orderby);
	}
	
	@Override
	protected List<Tpipeline> getPageData(int itemStartNumber, int pageSize, String filter, String orderby) {		
		TpipelineDAO oDao = new TpipelineDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		TpipelineDAO oDao = new TpipelineDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	public void sort(Comparator<Tpipeline> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	public String getSortDirection(Comparator<Tpipeline> cmpr) {
		return null;
	}
}
