package com.profile.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.userservice.model.role_type;
import com.userservice.model.sec_ques;
import com.userservice.model.users_profile;
import com.userservice.service.role_typeLocalServiceUtil;
import com.userservice.service.sec_quesLocalServiceUtil;
import com.userservice.service.users_profileLocalServiceUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Edit Profile",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Edit Profile Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class profilePortlet extends MVCPortlet 
{
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
	throws IOException, PortletException 
	{
		
	    HttpServletRequest request = PortalUtil.getOriginalServletRequest(
	    	    PortalUtil.getHttpServletRequest(renderRequest));
	    	    HttpSession session = request.getSession();
	    		users_profile obj = (users_profile) session.getAttribute("mainsession");
	    		
	    		long roletype = 0;
	    		long secques = 0;
	    		role_type rt = null;
	    		sec_ques sq = null;
	    		long id = obj.getU_id();
	    		long[] rtyp = users_profileLocalServiceUtil.getrole_typePrimaryKeys(id); 
	    	    for (int i=0;i<rtyp.length;i++)
	    	    {
	    	    roletype=rtyp[i];
	    	    }
	    		long[] styp = users_profileLocalServiceUtil.getsec_quesPrimaryKeys(id); 
	    	    for (int i=0;i<styp.length;i++)
	    	    {
	    	    secques=styp[i];
	    	    }
	    	    try 
	    	    {
	    			rt = role_typeLocalServiceUtil.getrole_type(roletype);
	    			sq = sec_quesLocalServiceUtil.getsec_ques(secques);
	    		} catch (PortalException e) 
	    	    {
	    			e.printStackTrace();
	    		}
	    	    
	    		renderRequest.setAttribute("userobj", obj);
	    		renderRequest.setAttribute("userrt", rt);
	    		renderRequest.setAttribute("usersq", sq);
	
	super.doView(renderRequest, renderResponse);
	}
	 
	 public void updprofile(ActionRequest actionRequest,ActionResponse actionResponse)throws PortalException,IOException, PortletException
	{
		    HttpServletRequest request = PortalUtil.getOriginalServletRequest(
		    PortalUtil.getHttpServletRequest(actionRequest));
		    HttpSession session = request.getSession();
		    users_profile obj = (users_profile) session.getAttribute("mainsession");
		    long id = obj.getU_id();
		    		
			users_profile userpro=users_profileLocalServiceUtil.getusers_profile(id);
				
			String DisplayName  = ParamUtil.getString(actionRequest, "uname");
			String email = ParamUtil.getString(actionRequest, "email");
			String mobile = ParamUtil.getString(actionRequest, "mobile");
			String sec_quesid = ParamUtil.getString(actionRequest, "sec_quesid");
			String sec_ans = ParamUtil.getString(actionRequest, "sec_ans");	
			
			long secquesid = Long.valueOf(sec_quesid).longValue();
			sec_ques s1 = sec_quesLocalServiceUtil.getsec_ques(secquesid);

			userpro.setEmail(email);
			userpro.setMobile(mobile);
			userpro.setUName(DisplayName);
			userpro.setSec_ans(sec_ans);
			userpro.setLastUpBy(null);
			userpro.setLastUpOn(null);
		    users_profileLocalServiceUtil.updateusers_profile(userpro);
		    
		    sec_quesLocalServiceUtil.setusers_profilesec_queses(userpro.getU_id(), new long[] {s1.getSec_quesId()});
		    actionResponse.sendRedirect("/web/bill-reimbursement/edit-profile");	
		    
			}
	 }
	
 
