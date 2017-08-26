package com.New.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.userservice.model.users_profile;
import com.userservice.service.users_profileLocalServiceUtil;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Change Password",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Change Password Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NewpassmvcPortlet extends MVCPortlet
{
	 public void changepass(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception
	 {
		    HttpServletRequest request = PortalUtil.getOriginalServletRequest(
		    PortalUtil.getHttpServletRequest(actionRequest));
			HttpSession session = request.getSession();
			users_profile obj = (users_profile) session.getAttribute("mainsession");
			long userid = obj.getU_id();
		    users_profile profile=users_profileLocalServiceUtil.getusers_profile(userid);
		 
			String curpass = ParamUtil.getString(actionRequest, "oldpassword");
			String newpass= ParamUtil.getString(actionRequest,"confirmpassword");
			String databasepass=profile.getPass();

			if(curpass.equals(databasepass))
			{
					profile.setPass(newpass);
					users_profileLocalServiceUtil.updateusers_profile(profile);
			}	
			}
	 }		 