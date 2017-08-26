package com.addpolicy.portlet;

import com.billpol.model.Pol;
import com.billpol.service.PolLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.userservice.model.users_profile;

import java.io.IOException;
import java.util.Date;

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
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.ers",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=1",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Add New Policy",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AddpolicyPortlet extends MVCPortlet {
	
	
	Pol pol1;
	public void policyadd(ActionRequest actionRequest, ActionResponse actionResponse)
		    throws Exception{
		 HttpServletRequest request = PortalUtil.getOriginalServletRequest(
				  PortalUtil.getHttpServletRequest(actionRequest));
			      HttpSession session = request.getSession();
				  users_profile obj = (users_profile) session.getAttribute("mainsession");
				  long iduser = obj.getU_id();
				  String name = obj.getUName();
					
				          
				  String polType = ParamUtil.getString(actionRequest,"billType");  
			      Double polLmt = ParamUtil.getDouble(actionRequest, "billLmt");
			      String polDesc = ParamUtil.getString(actionRequest, "billDesc");  
				  Boolean b = ParamUtil.getBoolean(actionRequest, "pollResc");
	  Pol pol = null;
	  String policyId = ParamUtil.getString(actionRequest, "PolId");
	  if(policyId.length()==0)
	  {	  
		  System.out.println("-------------------"+name);
	  pol = PolLocalServiceUtil.createPol(CounterLocalServiceUtil.increment());
	  pol.setBill_type(polType);
	  pol.setPol_billLimit(polLmt);
	  pol.setPol_desc(polDesc);
	  pol.setIsResc(b);
	  pol.setPol_lastUpBy(name);  //through session
	  pol.setPol_lastUpOn(new Date());
      PolLocalServiceUtil.updatePol(pol);    
      }
	  else
	  {
		  long policyId2 = Long.valueOf(policyId).longValue();
		  Pol pol1 = PolLocalServiceUtil.getPol(policyId2);
		  pol1.setBill_type(polType);
		  pol1.setU_id(iduser);             //through session
		  pol1.setPol_billLimit(polLmt);
		  pol1.setPol_desc(polDesc);
		  pol1.setIsResc(b);
		  pol1.setPol_lastUpBy(name);  //through session
		  pol1.setPol_lastUpOn(new Date());
	      PolLocalServiceUtil.updatePol(pol1);
	      actionResponse.sendRedirect("/group/bill-reimbursement/policies");
	  }
	}
	  @Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		  HttpServletRequest requestu = PortalUtil.getOriginalServletRequest(
		  PortalUtil.getHttpServletRequest(renderRequest));
	      HttpSession session = requestu.getSession();
	   	  Pol obj = session.getAttribute("PolSend")==null?null:(Pol) session.getAttribute("PolSend");
          renderRequest.setAttribute("polcy", obj);
          
          if(obj!=null)
          {  
              session.removeAttribute("PolSend"); 
          }    
          super.doView(renderRequest, renderResponse);
	}	
}