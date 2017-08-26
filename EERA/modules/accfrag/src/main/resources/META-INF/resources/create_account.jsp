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

<%@page import="com.userservice.model.sec_ques"%>
<%@page import="java.util.List"%>
<%@page import="com.userservice.service.sec_quesLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<center><h1><b>BILL REIMBURSEMENT SYSTEM</b></h1></center>
<center><h2>EMPLOYEE REGISTRATION</h2></center>

<i>Please note the password policies:<br>
1. It should be more than 8 characters in length.<br>
2. It should contain at least one upper case and at least one lower case character.<br>
3. It should have at lease one special character.<br>
4. It should contain at least one number.<br><br><br></i>

<portlet:actionURL name="/login/create_account" secure="<%= PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS || request.isSecure() %>" var="createAccountURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/login/create_account" />
</portlet:actionURL>

<aui:form action="<%= createAccountURL %>" method="post" name="fm">

	<aui:input label="User Name" name="uname" type="text">
		<aui:validator name="required" />
	</aui:input>

	<aui:input label="Password" name="pass" type="password">
		<aui:validator name="required" />
		<aui:validator name="minLength">8</aui:validator>
	</aui:input>
	
		<aui:input label="Your Name" name="name" type="text">
		<aui:validator name="required" />
	</aui:input>

	<aui:input label="E-Mail ID" name="email" type="text">
		<aui:validator name="required" />
		<aui:validator name="email" />
	</aui:input>

	<aui:input label="Mobile No." name="mobile" type="text">
		<aui:validator name="required" />
	</aui:input>
	<br>
	        Select Your Security Question
    <select name="<portlet:namespace/>sec_quesid">
		<%
			List<sec_ques> secQuestions = sec_quesLocalServiceUtil.getsec_queses(0,
						sec_quesLocalServiceUtil.getsec_quesesCount());
				for (sec_ques secQuesObj : secQuestions) {
		%>
		<option value="<%=secQuesObj.getSec_quesId()%>"><%=secQuesObj.getSec_ques()%></option>
		<%
			}
		%>

	</select>
	<br>
	<br>

	<aui:input label="Answer" name="sec_ans" type="text">
		<aui:validator name="required" />
	</aui:input>
	
				<c:if test="<%= PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT %>">
				<portlet:resourceURL id="/login/captcha" var="captchaURL" />

				<liferay-ui:captcha url="<%= captchaURL %>" />
			</c:if>

	<aui:button type="submit" value="Register"></aui:button>

</aui:form>
<br>
<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />