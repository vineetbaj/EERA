package com.example.portlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import com.billpol.model.Bil;
import com.billpol.model.Pol;
import com.billpol.service.BilLocalServiceUtil;
import com.billpol.service.PolLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.userservice.model.role_type;
import com.userservice.model.users_profile;
import com.userservice.service.role_typeLocalServiceUtil;
import com.userservice.service.users_profileLocalServiceUtil;

@Component(immediate = true, property = { 
		"com.liferay.portlet.header-portlet-javascript=/js/jQuery.MultiFile.min.js",
		
		"com.liferay.portlet.header-portlet-javascript=/datepicker/formden.js",
		"com.liferay.portlet.header-portlet-javascript=/datepicker/bootstrap-datepicker.min.js",
		
		"com.liferay.portlet.header-portlet-css=/datepicker/bootstrap-iso.css",
		"com.liferay.portlet.header-portlet-css=/datepicker/font-awesome.min.css",
		"com.liferay.portlet.header-portlet-css=/datepicker/bootstrap-datepicker3.css",
		
		
		"com.liferay.portlet.display-category=Add Bill", "com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.private-request-attributes=false", "com.liferay.portlet.private-session-attributes=false",
		"javax.portlet.display-name=bills Portlet", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", "javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class BillsmvcportletPortlet extends MVCPortlet {
	int step=0;
	long bilId;
	File[] files;
	String chkrdrct = null;

	public static Long folderId = System.currentTimeMillis();
	public static String pathToTmpFolder = "C://savedbills//";

	public void addbill(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception, IOException, PortletException 
	{
	    HttpServletRequest requestu = PortalUtil.getOriginalServletRequest(
	    PortalUtil.getHttpServletRequest(actionRequest));
        HttpSession session = requestu.getSession();
		users_profile obj = (users_profile) session.getAttribute("mainsession");
		long uidsession = obj.getU_id();
		String upbyname = obj.getName();
		
		String fileNameWithCommaSeparated = "";
		String billtype = ParamUtil.getString(actionRequest, "billtype");
		String nop = ParamUtil.getString(actionRequest, "nop");
		String date = ParamUtil.getString(actionRequest, "date");
		String amount = ParamUtil.getString(actionRequest, "amount");
		String stus = ParamUtil.getString(actionRequest, "status");
		String desc = ParamUtil.getString(actionRequest, "desc");
		String nmp = ParamUtil.getString(actionRequest, "nmp");
		String id_bill = ParamUtil.getString(actionRequest, "id_bill");
		String fileuploadexample = ParamUtil.getString(actionRequest, "file-upload-example");
		
		String temp[] = billtype.split(",");
		String pid = temp[0];
		long policyid = Long.valueOf(pid).longValue();
		
		int ptcp = Integer.parseInt(nop);
		
		Pol polobj = PolLocalServiceUtil.getPol(policyid);
		String btype = polobj.getBill_type();
		double limit = polobj.getPol_billLimit();
		String blimit = String.valueOf(limit);

		if (id_bill.length()==0) {
			
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			files = uploadRequest.getFiles("attachedFile");
			if(files[0]==null)
			{
				
			}
			else
			{
			try {
				
				for (File sourceFile : files) {
					String sourceFileName = sourceFile.getName();
					if(fileNameWithCommaSeparated.equals("")){
						fileNameWithCommaSeparated = folderId + File.separator + sourceFileName;
					}
					else{
						fileNameWithCommaSeparated = fileNameWithCommaSeparated +","+ folderId + File.separator + sourceFileName;
					}
					
					File newFolder = null;
					newFolder = new File(pathToTmpFolder + File.separator + folderId);

					if (!newFolder.exists()) {
						newFolder.mkdirs();
					}

					InputStream in = new FileInputStream(sourceFile);
					OutputStream out = new FileOutputStream(newFolder.getAbsolutePath() + File.separator + sourceFileName);

					// Copy the bits from input stream to output stream
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				}

			} 
		
			catch (Exception e) {
				UploadException uploadException = (UploadException) actionRequest.getAttribute(WebKeys.UPLOAD_EXCEPTION);
				if ((uploadException != null) && uploadException.isExceededSizeLimit()) {
					try {
						throw new FileSizeException(uploadException.getCause());
					} catch (FileSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						throw e;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			}
			
			long id = CounterLocalServiceUtil.increment(BillsmvcportletPortlet.class.getName());
			long bid = id + 10000;
			Bil bil1 = BilLocalServiceUtil.createBil(bid);	
		//	bil1.setStatus("In Progress");
			bil1.setBill_type(btype);
			bil1.setU_id(uidsession);
			bil1.setBill_upBy(upbyname);
			bil1.setNumOfPart(ptcp);
			Date date1 = new SimpleDateFormat("mm/dd/yyyy").parse(date);
			bil1.setBill_date(date1);
			double d = Double.parseDouble(amount);
			bil1.setBill_printAmount(d);
			bil1.setComment(desc);
			bil1.setNameOfPart(nmp);
			if (files[0]==null) bil1.setBill_img("No Image");
			else bil1.setBill_img(fileNameWithCommaSeparated);
			bil1.setBill_timeStamp(new Date());
			bil1.setBill_eligibleLimit(blimit);

			BilLocalServiceUtil.addBil(bil1);
			
			actionResponse.sendRedirect("/web/bill-reimbursement/view-bills");
		} 
		else 
		{
			
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			files = uploadRequest.getFiles("attachedFile");
			if(files[0]==null)
			{
				
			}
			else
			{
				
			try {
				
				for (File sourceFile : files) {
					String sourceFileName = sourceFile.getName();
					if(fileNameWithCommaSeparated.equals("")){
						fileNameWithCommaSeparated = folderId + File.separator + sourceFileName;
					}
					else{
						fileNameWithCommaSeparated = fileNameWithCommaSeparated +","+ folderId + File.separator + sourceFileName;
					}
					
					File newFolder = null;
					newFolder = new File(pathToTmpFolder + File.separator + folderId);

					if (!newFolder.exists()) {
						newFolder.mkdirs();
					}

					InputStream in = new FileInputStream(sourceFile);
					OutputStream out = new FileOutputStream(newFolder.getAbsolutePath() + File.separator + sourceFileName);

					// Copy the bits from input stream to output stream
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				}

			} 
		
			catch (Exception e) {
				UploadException uploadException = (UploadException) actionRequest.getAttribute(WebKeys.UPLOAD_EXCEPTION);
				if ((uploadException != null) && uploadException.isExceededSizeLimit()) {
					try {
						throw new FileSizeException(uploadException.getCause());
					} catch (FileSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						throw e;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			}
			
			long actualId = Long.valueOf(id_bill).longValue();
			Bil bil3 = BilLocalServiceUtil.getBil(actualId);
			bil3.setBill_type(btype);
			bil3.setNumOfPart(ptcp);
			bil3.setBill_date(new Date());
			bil3.setBill_upBy(upbyname);
			double d = Double.parseDouble(amount);
			bil3.setBill_printAmount(d);
			bil3.setComment(desc);
			bil3.setNameOfPart(nmp);
			if (files[0]==null) 
			{ 
				
			}
			else
			{	
				if (bil3.getBill_img().equals("No Image"))
				{
					bil3.setBill_img(fileNameWithCommaSeparated);
				}
				
				else
				{	
			       String extbill = bil3.getBill_img();
			       String updbill = extbill + "," +fileNameWithCommaSeparated;
			       bil3.setBill_img(updbill);
			     }
			}
			bil3.setBill_timeStamp(new Date());
			bil3.setBill_eligibleLimit(blimit);
	//		bil3.setStatus(stus);
			BilLocalServiceUtil.updateBil(bil3);
		
		}
	}

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException 
	{
        long roletype=0;
        role_type rt = null;
		File newFolder = null;
		newFolder = new File(pathToTmpFolder + File.separator + folderId);

		HttpServletRequest requestu = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		HttpSession session = requestu.getSession();
		users_profile userobj = (users_profile) session.getAttribute("mainsession");
		long uidsession = userobj.getU_id();
	    long[] abb = users_profileLocalServiceUtil.getrole_typePrimaryKeys(uidsession);
	    
	    for (int i=0;i<abb.length;i++)
	    {
	    	roletype=abb[0];
	    }
	    
	    try 
	    {
			rt = role_typeLocalServiceUtil.getrole_type(roletype);
		} catch (PortalException e) 
	    {
			e.printStackTrace();
		}
		Bil obj = session.getAttribute("BilSend") == null ? null : (Bil) session.getAttribute("BilSend");
		if (obj != null) 
		{
			session.removeAttribute("BilSend");
		}
		renderRequest.setAttribute("bill4", obj);
		renderRequest.setAttribute("userd", rt);

		super.doView(renderRequest, renderResponse);
	}
}