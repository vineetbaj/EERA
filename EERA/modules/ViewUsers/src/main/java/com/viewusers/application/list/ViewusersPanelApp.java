package com.viewusers.application.list;

import com.viewusers.constants.ViewusersPanelCategoryKeys;
import com.viewusers.constants.ViewusersPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + ViewusersPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class ViewusersPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ViewusersPortletKeys.Viewusers;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + ViewusersPortletKeys.Viewusers + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}