<%@ include file="/init.jsp" %>

<center><b><h2>Change your Password</h2></b></center><br>
<portlet:actionURL var="changepass" name="changepass"></portlet:actionURL>
<aui:form action="<%=changepass%>" method="post">

  	<aui:input label="Current Password" name="oldpassword" type="password">
		<aui:validator name="required" />
	</aui:input>

 <aui:input name="newpassword" type="password" label="New Password" >
 <aui:validator name="required" />
</aui:input>
<aui:input name="confirmpassword" type="password" label="Confirm Password">
<aui:validator name="required" />
 <aui:validator name="equalTo">'#<portlet:namespace />newpassword'</aui:validator>
</aui:input>
 <aui:button-row>
<aui:button type="submit" value="Update Password"/>
</aui:button-row>
</aui:form>