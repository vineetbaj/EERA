package com.viewbills.application.list;

import com.viewbills.constants.ViewbillsPanelCategoryKeys;
import com.viewbills.constants.ViewbillsPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + ViewbillsPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class ViewbillsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ViewbillsPortletKeys.Viewbills;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + ViewbillsPortletKeys.Viewbills + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}