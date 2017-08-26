<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="anonymous-account">
	<portlet:actionURL name="/login/create_account" var="createAccountURL">
		<portlet:param name="mvcRenderCommandName" value="/login/create_account" />
	</portlet:actionURL>
<center><h1><b>BILL REIMBURSEMENT SYSTEM</b></h1></center>
<center><h2>EMPLOYEE MANAGEMENT</h2></center>	
<br>	 
         
<h3><%= renderRequest.getAttribute("username") %>, your <%= renderRequest.getAttribute("msgg") %></h3>
<h3>User ID <b><%= renderRequest.getAttribute("userid") %></b>.</h3>
</div>
<br>

<% if(renderRequest.getAttribute("footer")==null)
	{%>
<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />
<%
}%>
