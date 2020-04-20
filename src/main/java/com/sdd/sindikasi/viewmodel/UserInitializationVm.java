package com.sdd.sindikasi.viewmodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.sdd.sindikasi.dao.MmenuDAO;
import com.sdd.sindikasi.dao.MusergroupmenuDAO;
import com.sdd.sindikasi.domain.Mmenu;
import com.sdd.sindikasi.domain.Muser;
import com.sdd.sindikasi.domain.Musergroupmenu;
import com.sdd.sindikasi.utils.AppUtils;
import com.sdd.utils.ListModelFlyweight;

public class UserInitializationVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private Mmenu mmenu;
	private MmenuDAO mmenuDao = new MmenuDAO();

	@Wire
	private Treechildren root;
	@Wire
	private Div divContent;
	@Wire
	private Combobox cbMenu;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		try {
			oUser = (Muser) zkSession.getAttribute("oUser");
			setMenuAutocomplete();
			boolean isOpen = true;
			boolean isSubgroup = false;

			String menugroup = "";
			String menusubgroup = "";
			Treechildren treechildrenGroup = null;
			Treechildren treechildrenSub = null;

			List<Musergroupmenu> oList = new MusergroupmenuDAO().listByFilter(
					"musergroup.musergrouppk = " + oUser.getMusergroup().getMusergrouppk(),
					"mmenu.menuorderno, mmenu.menuname");

			for (final Musergroupmenu obj : oList) {

				if (!menugroup.equals(obj.getMmenu().getMenugroup())) {
					menugroup = obj.getMmenu().getMenugroup();

					Treeitem treeitem = new Treeitem();
					treeitem.setOpen(isOpen);
					root.appendChild(treeitem);

					Treerow treerow = new Treerow();
					Treecell treecell = new Treecell();
					Label menugrouplabel = new Label(menugroup);

					if (obj.getMmenu().getMenugroupicon() != null && obj.getMmenu().getMenugroupicon() != ""
							&& obj.getMmenu().getMenugroupicon().trim().length() > 0) {
						Image menugroupicon = new Image();
						menugroupicon.setSrc("/resources/images/" + obj.getMmenu().getMenugroupicon());
						menugroupicon.setWidth("24px");
						menugroupicon.setHeight("24px");

						treecell.appendChild(menugroupicon);
					}

					treecell.appendChild(menugrouplabel);

					treerow.appendChild(treecell);
					treeitem.appendChild(treerow);

					treechildrenGroup = new Treechildren();
					treeitem.appendChild(treechildrenGroup);
				}

				if (!obj.getMmenu().getMenugroup().equals(obj.getMmenu().getMenusubgroup())) {
					if (!menusubgroup.equals(obj.getMmenu().getMenusubgroup())) {
						menusubgroup = obj.getMmenu().getMenusubgroup();
						isSubgroup = true;

						Treeitem treeitem = new Treeitem();
						treeitem.setOpen(isOpen);
						treechildrenGroup.appendChild(treeitem);

						Treerow treerow = new Treerow();

						Treecell treecell = new Treecell();
						Label menusubgrouplabel = new Label(menusubgroup);

						if (obj.getMmenu().getMenusubgroupicon() != ""
								&& obj.getMmenu().getMenusubgroupicon().trim().length() > 0) {
							Image menusubgroupicon = new Image();
							menusubgroupicon.setSrc("/resources/images/" + obj.getMmenu().getMenusubgroupicon());
							menusubgroupicon.setWidth("24px");
							menusubgroupicon.setHeight("24px");

							treecell.appendChild(menusubgroupicon);
						}

						treecell.appendChild(menusubgrouplabel);

						treerow.appendChild(treecell);
						treeitem.appendChild(treerow);

						treechildrenSub = new Treechildren();
						treeitem.appendChild(treechildrenSub);
					}
				} else
					isSubgroup = false;

				Treeitem treeitem = new Treeitem();
				treeitem.setOpen(isOpen);
				if (isSubgroup)
					treechildrenSub.appendChild(treeitem);
				else
					treechildrenGroup.appendChild(treeitem);

				Treerow treerow = new Treerow();

				Treecell treecell = new Treecell();
				Label menulabel = new Label(obj.getMmenu().getMenuname());

				if (obj.getMmenu().getMenuicon() != "" && obj.getMmenu().getMenuicon().trim().length() > 0) {

					Image menuicon = new Image();
					menuicon.setSrc("/resources/images/" + obj.getMmenu().getMenuicon());
					menuicon.setWidth("24px");
					menuicon.setHeight("24px");

					treecell.appendChild(menuicon);
				}

				treecell.appendChild(menulabel);

				treerow.appendChild(treecell);
				treeitem.appendChild(treerow);
				
				treecell.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event event) throws Exception {
						divContent.getChildren().clear();
						Map<String, String> map = new HashMap<String, String>();
						map.put(obj.getMmenu().getMenuparamname(), obj.getMmenu().getMenuparamvalue());
						Executions.createComponents(obj.getMmenu().getMenupath(), divContent, map);
					}
				});
			}

			Executions.createComponents("/view/welcome.zul", divContent, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doRedirect(@BindingParam("item") Mmenu obj) {
		if (obj != null) {
			try {
				divContent.getChildren().clear();
				Map<String, String> map = new HashMap<String, String>();
				map.put(obj.getMenuparamname(), obj.getMenuparamvalue());

				map.put("a", null);
				map.put("b", null);

				Executions.createComponents(obj.getMenupath(), divContent, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Command
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public void setMenuAutocomplete() {
		try {
			List<Musergroupmenu> oList = new MusergroupmenuDAO().listByFilter(
					"musergroup.musergrouppk = " + oUser.getMusergroup().getMusergrouppk(), "mmenu.menuorderno");
			cbMenu.setModel(new SimpleListModel(oList) {
				public ListModel getSubModel(Object value, int nRows) {
					if (value != null && value.toString().trim().length() > AppUtils.AUTOCOMPLETE_MINLENGTH) {
						String nameStartsWith = value.toString().trim().toUpperCase();
						List data = mmenuDao.startsWith(AppUtils.AUTOCOMPLETE_MAXROWS, nameStartsWith);
						return ListModelFlyweight.create(data, nameStartsWith, "menuname");
					}
					return ListModelFlyweight.create(Collections.emptyList(), "", "menuname");
				}
			});

			cbMenu.setItemRenderer(new ComboitemRenderer<Mmenu>() {
				public void render(Comboitem item, Mmenu data, int index) throws Exception {
					item.setLabel(data.getMenuname());
					// item.setDescription(data.getProducttype());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Mmenu getMmenu() {
		return mmenu;
	}

	public void setMmenu(Mmenu mmenu) {
		this.mmenu = mmenu;
	}

}
