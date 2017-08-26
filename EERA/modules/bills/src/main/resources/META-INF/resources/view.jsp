<%@page import="com.userservice.model.role_type"%>
<%@page import="com.billpol.service.PolLocalServiceUtil"%>
<%@ page import="java.io.*"%>
<%@page import="com.billpol.service.PolLocalService"%>
<%@page import="com.billpol.model.Pol"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.billpol.model.Bil"%>
<%@ include file="/init.jsp"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<style>
input[type=text], select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
</style>

<portlet:defineObjects />
<portlet:actionURL var="addbill" name="addbill"></portlet:actionURL>

<%!String date = "02/02/2017";%>


<%!Bil uobj = null; 
   String docs[] = null;
   %>
<%
	uobj = renderRequest.getAttribute("bill4") == null ? null : (Bil) renderRequest.getAttribute("bill4");
    role_type rtype = (role_type) renderRequest.getAttribute("userd");
%>



<script>
	function getText(slction) {
		txtSelected = slction.options[slction.selectedIndex].value;

		var tempArr = txtSelected.split(',');
		txtSelected = tempArr[1];
		document.getElementById('div1').innerHTML = "<br>Per Person Allowed Bill Amount Rs. "
				+ txtSelected;
	}
</script>

	<center><b><h2>Upload Bills</h2></b></center>


<aui:form method="post" action="<%=addbill%>">
	
		<aui:input name="id_bill" type="hidden" label="id_bill" width="250px:" value="<%= (uobj==null)?"":uobj.getBill_id()%>">
	</aui:input>
							
							    <label for="btyp">Select Bill Type</label>
    <select name="<portlet:namespace/>billtype" onchange="getText(this)" id="btyp">
    <option value="null">Bill Type</option>
		<%
			List<Pol> poltype = PolLocalServiceUtil.getPols(0, PolLocalServiceUtil.getPolsCount());
				for (Pol ptype : poltype) {
		%>
		<option value="<%=ptype.getPol_id()%>,<%=ptype.getPol_billLimit()%>">><%=ptype.getBill_type()%></option>
		<%
			}
		%>
        <br>
        <br>
        </select>


		<div id="div1"
			style="color: blue; font-family: Calibri; font-size: large; font-weight: bold"></div>

        <br>
        <% if (uobj!=null)
        {
        %>
        <b>Chosen Bill Date :</b> &nbsp
		<%=(uobj == null) ? "" : new SimpleDateFormat("mm/dd/yyyy").format(uobj.getBill_date())%>
		<br>
		<br>
		<%
        }
		%>
		
		<aui:input class="form-control" id="date" name="date"
			placeholder="MM/DD/YYYY" type="text" value="<%=date%>">
			<aui:validator name="required" />
			</aui:input>
			<br>
			
					<aui:input label="Number Of Participants" type="text" name="nop" value='<%=(uobj == null) ? "" : uobj.getNumOfPart()%>'>
			<aui:validator name="required" />
			<aui:validator name="number" />
		</aui:input>
		<br>

		<aui:input label="Bill Amount" type="text" 
			name="amount"
			value='<%=(uobj == null) ? "" : uobj.getBill_printAmount()%>'>
			<aui:validator name="required" />
			<aui:validator name="number" />
		</aui:input>
		<br>

        Bill Description: <br>
		
		<textarea class="textarea" rows="4" cols="50"
			name="<portlet:namespace/>desc" id="desc" placeholder=" "
			value='<%=(uobj == null) ? "" : uobj.getComment()%>'><%=(uobj == null) ? "" : uobj.getComment()%></textarea>
			<br>
			<br>

        Name of Participants: <br>
		<textarea class="textarea" rows="4" cols="50"
			name="<portlet:namespace/>nmp" id="participants" placeholder=" "
			value='<%=(uobj == null) ? "" : uobj.getNameOfPart()%>'><%=(uobj == null) ? "" : uobj.getNameOfPart()%></textarea>		
		<br>
        
		<enctype="multipart/form-data"> <aui:row>
			<aui:col columnWidth="50">
			Upload Bill Images : &nbsp
				<input type="file" class="multi accept-gif|jpg|png|pdf|doc|docx"
					maxlength="10" name="attachedFile"/>
			</aui:col>
			</aui:row>
			<br>
		
		  <aui:button type="submit" value="SUBMIT"></aui:button>
				
</aui:form>
<br>
<br>

<script>
	$('.form').find('input, textarea').on('keyup blur focus', function(e) {

		var $this = $(this), label = $this.prev('label');

		if (e.type === 'keyup') {
			if ($this.val() === '') {
				label.removeClass('active highlight');
			} else {
				label.addClass('active highlight');
			}
		} else if (e.type === 'blur') {
			if ($this.val() === '') {
				label.removeClass('active highlight');
			} else {
				label.removeClass('highlight');
			}
		} else if (e.type === 'focus') {

			if ($this.val() === '') {
				label.removeClass('highlight');
			} else if ($this.val() !== '') {
				label.addClass('highlight');
			}
		}

	});

	$('.tab a').on('click', function(e) {

		e.preventDefault();

		$(this).parent().addClass('active');
		$(this).parent().siblings().removeClass('active');

		target = $(this).attr('href');

		$('.tab-content > div').not(target).hide();

		$(target).fadeIn(600);

	});
</script>


<script type="text/javascript">
	function SetCard(sel) {
		alert(sel.options[sel.selectedIndex].value);
	}
</script>



<script>
	$(document).ready(
			function() {
				var date_input = $('input[name="<portlet:namespace/>date"]'); //our date input has the name "date"
				var container = $('.bootstrap-iso form').length > 0 ? $(
						'.bootstrap-iso form').parent() : "body";
				date_input.datepicker({
					format : 'mm/dd/yyyy',
					container : container,
					todayHighlight : true,
					autoclose : true,
				})
			})
</script>