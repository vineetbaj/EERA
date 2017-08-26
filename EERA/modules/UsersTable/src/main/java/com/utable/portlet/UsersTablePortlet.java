package com.utable.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.userservice.model.role_type;
import com.userservice.model.sec_ques;
import com.userservice.model.users_profile;
import com.userservice.service.role_typeLocalServiceUtil;
import com.userservice.service.sec_quesLocalServiceUtil;
import com.userservice.service.users_profileLocalServiceUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=User Details",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=User Details",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UsersTablePortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		try {
			String getid = resourceRequest.getResourceID();
			if(getid.equals("rajat"))
			{
			JSONObject jsonuser = null;
			JSONArray userJsonArray = JSONFactoryUtil.createJSONArray();
			List<users_profile> userList = users_profileLocalServiceUtil.getusers_profiles(0, users_profileLocalServiceUtil.getusers_profilesCount());
			for (users_profile userObj : userList) {
				jsonuser = JSONFactoryUtil.createJSONObject();
				long roletype = 0;
				long id = userObj.getU_id();
				jsonuser.put("User ID", userObj.getU_id());
				jsonuser.put("User Name", userObj.getUName());
				jsonuser.put("Mobile Number", userObj.getMobile());
				jsonuser.put("E-Mail ID", userObj.getEmail());
			    long[] abb = users_profileLocalServiceUtil.getrole_typePrimaryKeys(id); 
			    for (int i=0;i<abb.length;i++)
			    {
			    roletype=abb[i];
			    }
			    role_type rt = role_typeLocalServiceUtil.getrole_type(roletype);
				jsonuser.put("Role Type", rt.getRole_type());
				boolean isEn = userObj.getIsenable();
				if (isEn)
				{
					jsonuser.put("Status", "Enabled");
				}
				else
				{
					jsonuser.put("Status", "Disabled");
				}
				jsonuser.put("Update User", userObj.getU_id());
				jsonuser.put("Enable/Disable User", userObj.getU_id());
				userJsonArray.put(jsonuser);
			}
			PrintWriter out = resourceResponse.getWriter();
			out.print(userJsonArray); 
			}
			
			else if (getid.equals("uprequest"))
			{
				String iduser = resourceRequest.getParameter("userid");
				int idofuser = Integer.parseInt(iduser);
				System.out.println(iduser);
				users_profile obj = users_profileLocalServiceUtil.getusers_profile(idofuser);
				boolean status = obj.getIsenable();
				if (status) obj.setIsenable(false);
				else obj.setIsenable(true);
			    users_profileLocalServiceUtil.updateusers_profile(obj);
				resourceResponse.setContentType("text/html");
			    PrintWriter writer = resourceResponse.getWriter();
			    String name = obj.getName();
			    if (status) writer.print(name + " has been disabled");
			    if (!status) writer.print(name + " has been enabled");
			    writer.flush();
			    writer.close();
			    
			    super.serveResource(resourceRequest, resourceResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void callaction(ActionRequest request, ActionResponse response)
			throws IOException, PortalException
	{
		long roletype=0;
		long secquess=0;
		String uid = request.getParameter("userid");
		System.out.println(uid);
		long iduser = Long.valueOf(uid).longValue();
		users_profile obj = users_profileLocalServiceUtil.getusers_profile(iduser);
		long[] roletypes = users_profileLocalServiceUtil.getrole_typePrimaryKeys(iduser);
		long[] secques = users_profileLocalServiceUtil.getsec_quesPrimaryKeys(iduser);
		for (int j=0;j<roletypes.length;j++)
		{
		roletype=roletypes[j];
		}
		for (int k=0;k<secques.length;k++)
		{
		secquess=secques[k];
		}
	    role_type rt = role_typeLocalServiceUtil.getrole_type(roletype);
	    
	    sec_ques sq = sec_quesLocalServiceUtil.getsec_ques(secquess);

	    HttpServletRequest requestu = PortalUtil.getOriginalServletRequest(
	    PortalUtil.getHttpServletRequest(request));
        HttpSession session = requestu.getSession();
        session.setAttribute("userobj",obj);
        session.setAttribute("roleobj",rt);
        session.setAttribute("secobj",sq);
	     
		response.sendRedirect("/group/bill-reimbursement/addupdate");	
	}
	
}