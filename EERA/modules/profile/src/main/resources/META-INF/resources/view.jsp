<%@page import="com.userservice.service.sec_quesLocalServiceUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.userservice.model.role_type"%>
<%@page import="com.userservice.model.sec_ques"%>
<%@page import="com.userservice.model.users_profile"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<% users_profile uobj = (users_profile)renderRequest.getAttribute("userobj");
sec_ques sqobj = (sec_ques)renderRequest.getAttribute("usersq");
role_type rtobj = (role_type)renderRequest.getAttribute("userrt");
%>
<center><b><h2>Update your personal details</h2></b></center><br>
<portlet:defineObjects />
<portlet:actionURL var="updprofile" name="updprofile"></portlet:actionURL>
<aui:form action="<%=updprofile%>" method="post" name="fm">

	<b>User ID : <%= uobj.getU_id() %></b>
	<br>
	<br>
	
			<aui:input label="Display Name" name="uname" type="text" value='<%= uobj.getUName() %>'>
		<aui:validator name="required" />
	</aui:input>
	
	<aui:input label="E-Mail ID" name="email" type="text" value='<%= uobj.getEmail() %>'>
		<aui:validator name="required" />
		<aui:validator name="email" />
	</aui:input>

	<aui:input label="Mobile No." name="mobile" type="text" value='<%= uobj.getMobile() %>'>
		<aui:validator name="required" />
		<aui:validator name="maxLength">12</aui:validator>
		<aui:validator name="minLength">10</aui:validator>
		<aui:validator name="digits"></aui:validator>
	</aui:input>
    <br>

	<b>Current Role Type : <%= rtobj.getRole_type() %></b>
	<br>
	
    <br>
	<b>Current Security Question : <%= sqobj.getSec_ques() %></b>
	<br>

	<br>
		        Select Security Question
    <select name="<portlet:namespace/>sec_quesid">
		<%
			List<sec_ques> secQuestions = sec_quesLocalServiceUtil.getsec_queses(0,
						sec_quesLocalServiceUtil.getsec_quesesCount());
				for (sec_ques secQuesObj : secQuestions) {
		%>
		<option value="<%=secQuesObj.getSec_quesId()%>" 
		
		<% if(secQuesObj.getSec_quesId()==sqobj.getSec_quesId()){ %> selected <% 
			} %>
			
			 ><%=secQuesObj.getSec_ques()%></option>
		<%
			}
		%>

	</select>
	<br><br>
	
		<aui:input label="Security Answer" name="sec_ans" type="text" value='<%= uobj.getSec_ans() %>'>
		<aui:validator name="required" />
	</aui:input>
   <br>
   
	<aui:button type="submit" value="Update"></aui:button>
	<br>

</aui:form>