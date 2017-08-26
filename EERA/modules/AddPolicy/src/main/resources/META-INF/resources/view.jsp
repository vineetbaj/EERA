<%@page import="com.billpol.service.PolLocalServiceUtil"%>
<%@page import="com.billpol.model.Pol"%>
<%@page import="javax.portlet.ActionRequest"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@ include file="/init.jsp"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<portlet:defineObjects />

<portlet:defineObjects />
<%! Pol uobj = null;
%>
<% uobj = renderRequest.getAttribute("polcy")==null?null:(Pol) renderRequest.getAttribute("polcy"); 
%>

<style type="text/css">
input[type=text] {
	width: 100%;
	padding: 3px;
	margin: 8px 0;
	box-sizing: border-box;
	border: 1px solid #555;
	outline: none;
}

.aui-form-validator-error {
	background: lightblack;
}

.aui-form-validator-valid {
	background: lightblue;
}
</style>



<portlet:defineObjects />
<portlet:actionURL var="policyadd" name="policyadd"></portlet:actionURL>
<aui:form action="<%=policyadd%>" id="policyadd" method="post" name="policyadd">
	<div>
	    <tr>
			<td width="50%">
				<div class="field-wrap">
					<p>
						<aui:input name="PolId" type="hidden" label="  Pol Type" width="250px:" value="<%= (uobj==null)?"":uobj.getPol_id()%>">
						</aui:input>
					</p>
				</div>
			</td>
		</tr>
		<tr>
			<td width="50%">
				<div class="field-wrap">
					<p>
						<aui:input name="billType" type="text" label="  Bill Type" width="250px:" value="<%= (uobj==null)?"":uobj.getBill_type()%>">
							<aui:validator name="required" />
							<aui:validator name="alphanum" />
						</aui:input>
					</p>
				</div>
			</td>
		</tr>

		<tr>
			<td width="50%">
				<div class="field-wrap">
					<p>
						<aui:input name="billLmt" type="number" label=" Bill Limit (Applicable per person)"
							 width="250px" value="<%= (uobj==null)?"":uobj.getPol_billLimit() %>">
							<aui:validator name="required" />
							<aui:validator name="number" />
						</aui:input>
					</p>
				</div>
			</td>
		</tr>
		<tr>
			<td width="50%">
				<div class="field-wrap">
					<p>
						<aui:input name="billDesc" type="textarea" label=" Bill description" width="250px" value="<%= (uobj==null)?"":uobj.getPol_desc() %>">
							<aui:validator name="required" />

						</aui:input>
					</p>
				</div>
			</td>
		</tr>
		<tr>
			<td width="50%">
				<div class="field-wrap">
					<p>
						<aui:input name="pollResc" label="  Fixed Allowance" type="checkbox"
							width="250px" value="<%= (uobj==null)?"":uobj.getIsResc() %>">
						</aui:input>
					</p>
				</div>
			</td>
		</tr>
		<tr>
			<td width="50%">
				<div class="field-wrap">
					<p>
						<aui:button-row>
							<aui:button type="submit" value="SAVE"/>
							<aui:button type="reset" value="Cancel" onclick="cancel()"/>
						</aui:button-row>
					</p>
				</div>
			</td>
		</tr>
	</div>
</aui:form>
<script>
function cancel(){
	window.location.reload();
}
</script>