package com.billtable.portlet;

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

import com.billpol.model.Bil;
import com.billpol.service.BilLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;;
@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=category.ers",
		"com.liferay.portlet.instanceable=true", 
		"javax.portlet.display-name=BillTable Portlet",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"javax.portlet.init-param.template-path=/", 
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
		}, service = Portlet.class)
public class billtableportlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		super.doView(renderRequest, renderResponse);
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException, ArrayIndexOutOfBoundsException{
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest)); 
		  String myArticleId = httpReq.getParameter("paramName");
		  
		  System.out.println("ParamName:"+myArticleId);
		  ParamUtil.getString(resourceRequest, "paramName");
		if(myArticleId.equals("dataTable"))
		  {
		try {
			System.out.println("====serveResource========");
			JSONObject jsonBil = null;
			JSONArray usersJsonArray = JSONFactoryUtil.createJSONArray();
			System.out.println(usersJsonArray.length());
			List<Bil> BilList = BilLocalServiceUtil.getBils(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			System.out.println("BilList:"+BilList.size());
			for (Bil billObj : BilList) {
				// long polid = 0;
				jsonBil = JSONFactoryUtil.createJSONObject();
				jsonBil.put("bill_id", billObj.getBill_id());
				long bid = billObj.getBill_id(); 
				System.out.println(bid);
				jsonBil.put("bill_type", billObj.getBill_type());
                jsonBil.put("comment", billObj.getComment());
                jsonBil.put("bill_printAmount", billObj.getBill_printAmount());
                jsonBil.put("bill_eligibleLimit", billObj.getBill_eligibleLimit());
                jsonBil.put("bill_upBy", billObj.getBill_upBy());
                jsonBil.put("bill_timeStamp", billObj.getBill_timeStamp());
                jsonBil.put("numOfPart", billObj.getNumOfPart());
                jsonBil.put("nameOfPart", billObj.getNameOfPart());
                jsonBil.put("vendorDesc", billObj.getVendorDesc());
                jsonBil.put("bill_date", billObj.getBill_date());
                jsonBil.put("bill_img", billObj.getBill_img());
                jsonBil.put("companyId", billObj.getCompanyId());
                
                // Fetching the policyId of the current Bill
                /*long[] pid = BilLocalServiceUtil.getPolPrimaryKeys(bid);
               // polid = pid[0];
                System.out.println("avas");
                Pol pl = PolLocalServiceUtil.getPol(pid[0]);
                jsonBil.put("pol_id", pl.getPol_id());*/
                jsonBil.put("Action", billObj.getBill_id());
				usersJsonArray.put(jsonBil);
			}
			PrintWriter out = resourceResponse.getWriter();
			System.out.println(usersJsonArray.toString());
			out.print(usersJsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 if(myArticleId.equals("updateBill"))
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
	   PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest)); 
	   String myArticleId1 = httpReq.getParameter("billId");
	         Long l = Long.parseLong(myArticleId1);
	       
	         try 
	         {
	    BilLocalServiceUtil.deleteBil(l);
	      } catch (PortalException e) {
	       e.printStackTrace();
	      }
	     }
}
	 public void callaction(ActionRequest request, ActionResponse response)
				throws IOException, PortletException, PortalException{
			String billId = request.getParameter("billid");
			System.out.println("byeeeeeeeeeeeeeeeeee"+billId);
			long billId2 = Long.valueOf(billId).longValue();
			System.out.println("tieeeeeeeeeeeeeeee"+billId2);
			Bil bil = BilLocalServiceUtil.getBil(billId2);
			HttpServletRequest reques = PortalUtil.getOriginalServletRequest(
				    PortalUtil.getHttpServletRequest(request));
				    HttpSession session1 = reques.getSession();
				    session1.setAttribute("BilSend",bil);
				    Bil sessionBill = (Bil)session1.getAttribute("BilSend");
				    System.out.println(sessionBill);
			response.sendRedirect("/web/guest/bill-upload");
}
}