package com.example.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

import com.billpol.model.Pol;
import com.billpol.service.PolLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
@Component(immediate = true, 
          property = {  
        		  "com.liferay.portlet.display-category=category.ers",
		          "com.liferay.portlet.instanceable=true", 
		          "javax.portlet.display-name=result Portlet",
		          "javax.portlet.init-param.template-path=/", 
		          "com.liferay.portlet.private-session-attributes=false",
		  		"com.liferay.portlet.private-request-attributes=false",
		          "javax.portlet.init-param.view-template=/view.jsp",
		          "javax.portlet.resource-bundle=content.Language",
		          "javax.portlet.security-role-ref=power-user,user" 
		}, 
          service = Portlet.class)
public class PolicymvcportletPortlet extends MVCPortlet {
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
			System.out.println("====serveResource========s:"+resourceRequest.toString());
			JSONObject jsonPol = null;
			JSONArray usersJsonArray = JSONFactoryUtil.createJSONArray();
			List<Pol> polList = PolLocalServiceUtil.getPols(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			for (Pol userObj : polList) {
				jsonPol = JSONFactoryUtil.createJSONObject();
				jsonPol.put("pol_id", userObj.getPol_id());
				jsonPol.put("bill_type", userObj.getBill_type());
				jsonPol.put("pol_billlimit", userObj.getPol_billLimit());
                jsonPol.put("pol_desc", userObj.getPol_desc());
                jsonPol.put("pol_lastUpOn", userObj.getPol_lastUpOn());
                jsonPol.put("pol_lastUpBy", userObj.getPol_lastUpBy());
                jsonPol.put("isresc", userObj.getIsResc());
                jsonPol.put("companyId", userObj.getCompanyId());
                jsonPol.put("Action", userObj.getPol_id());
				usersJsonArray.put(jsonPol);
			}
			PrintWriter out = resourceResponse.getWriter();
			System.out.println(usersJsonArray.toString());
			out.print(usersJsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		else if(myArticleId.equals("updatePolicy"))
		{
			System.out.println("got success--------");
			HttpServletRequest httpReq2 = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest)); 
			String myArticleId2 = httpReq2.getParameter("updateId");
			System.out.println(myArticleId2);
			
			 HttpServletRequest request = PortalUtil.getOriginalServletRequest(
					    PortalUtil.getHttpServletRequest(resourceRequest));
					    HttpSession session1 = request.getSession();
					    session1.setAttribute("value",myArticleId2);

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
				throws IOException, PortletException{
			String sampleParam = ParamUtil.get(request, "userid", "defaultValue");
			//String sampleParam = ParamUtil.getString(request, "userid");  
			System.out.println("uu"+sampleParam);
			request.setAttribute("idd", sampleParam);
			//response.setRenderParameter("jspPage", "/jsp/new/useradd.jsp");
			response.sendRedirect("/group/guest/~/control_panel/manage?p_p_id=Addpolicy&p_p_lifecycle=0&p_p_state=maximized&p_p_auth=IjEjTEgm");
}

}	