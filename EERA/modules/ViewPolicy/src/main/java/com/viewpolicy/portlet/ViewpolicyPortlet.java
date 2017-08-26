package com.viewpolicy.portlet;

import com.billpol.model.Pol;
import com.billpol.service.PolLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
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
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.ers",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=View All Policies",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class ViewpolicyPortlet extends MVCPortlet {
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest)); 
		String myArticleId = httpReq.getParameter("paramName");
		
		System.out.println("ParamName:"+myArticleId);
		String s= ParamUtil.getString(resourceRequest, "paramName");
		
		if(myArticleId.equals("dataTable"))
		{
		 try {
			Boolean resc;
			String sbool;
			JSONObject jsonPol = null;
			JSONArray usersJsonArray = JSONFactoryUtil.createJSONArray();
			List<Pol> polList = PolLocalServiceUtil.getPols(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			for (Pol userObj : polList) {
				resc = userObj.getIsResc();
				if(resc==true)
					sbool="yes";
				else
					sbool="no";
				jsonPol = JSONFactoryUtil.createJSONObject();
				jsonPol.put("pol_id", userObj.getPol_id());
				jsonPol.put("bill_type", userObj.getBill_type());
				jsonPol.put("pol_billlimit", userObj.getPol_billLimit());
                jsonPol.put("pol_lastUpOn", userObj.getPol_lastUpOn().toLocaleString());
                jsonPol.put("pol_lastUpBy", userObj.getPol_lastUpBy());
                jsonPol.put("isresc", sbool);
                jsonPol.put("companyId", userObj.getCompanyId());
                jsonPol.put("Action", userObj.getPol_id());
				usersJsonArray.put(jsonPol);
			}
			PrintWriter out = resourceResponse.getWriter();
			out.print(usersJsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	
	    else
	    {
		 HttpServletRequest httpReq1 = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));      
         try 
         {
		  PolLocalServiceUtil.deletePol(Long.parseLong(httpReq.getParameter("policyId")));
	     } catch (PortalException e) {
		     e.printStackTrace();
	     }
	    }
   } 
	
	  public void callaction(ActionRequest request, ActionResponse response)
				throws IOException, PortletException, PortalException{
			String polId = request.getParameter("polid");
			long policyId = Long.valueOf(polId).longValue();
			Pol pol = PolLocalServiceUtil.getPol(policyId);
			HttpServletRequest reques = PortalUtil.getOriginalServletRequest(
				    PortalUtil.getHttpServletRequest(request));
				    HttpSession session1 = reques.getSession();
				    session1.setAttribute("PolSend",pol);
			response.sendRedirect("/group/bill-reimbursement/add-new-policy");
   }
	 
}