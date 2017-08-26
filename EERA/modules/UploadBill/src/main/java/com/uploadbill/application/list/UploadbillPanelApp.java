package com.uploadbill.application.list;

import com.uploadbill.constants.UploadbillPanelCategoryKeys;
import com.uploadbill.constants.UploadbillPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + UploadbillPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class UploadbillPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return UploadbillPortletKeys.Uploadbill;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + UploadbillPortletKeys.Uploadbill + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}