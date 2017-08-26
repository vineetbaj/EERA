<%@ include file="/init.jsp"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="theme"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<theme:defineObjects />
<portlet:resourceURL var="updaContentURL"></portlet:resourceURL>
<portlet:resourceURL var="loadCategories" id="loadCategories"></portlet:resourceURL>
<portlet:actionURL var="callaction" name="callaction"></portlet:actionURL>
<portlet:defineObjects />
<div id="myDataTable"></div>
<script>
var data = [];
function updateDiv(){
	AUI().use('aui-base','aui-io-request', function(A){
	var allRows="";	
	A.io.request('<%=updaContentURL%>', {
						dataType : 'json',/* 
						url : "<portlet:resourceURL id='updaContentURl'/>"  , */
						method : 'GET',
						data : {
							paramName : 'dataTable'
						},
						on : {
							success : function() {
								data = this.get('responseData');
								console.log("data:"+JSON.stringify(data));
								YUI().use('aui-datatable', 'datatable-paginator', 'datatable-sort',
										function(Y) {
											var columns = [ {
					key : 'pol_id',
					sortable : true,
					label: 'Policy ID'
				},
				{
					label: 'Bill Type',
					key : 'bill_type',
					sortable : true
				},

				{
					label: 'Bill Limit',
					key : 'pol_billlimit',
					sortable : true
				},
                 {
					label: 'Policy Description',
					key : 'pol_desc',
					sortable : true
				}, {
					label: 'Last Updated By',
					key : 'pol_lastUpBy',
					sortable : true
				}, {
					label: 'Last Updated on',
					key : 'pol_lastUpOn',
					sortable : true
				}, {
					label: 'Restriction',
					key : 'isresc',
					sortable : true
				},{
					key : 'companyId',
					sortable : true,
					label: 'Company ID'
				},{
					key: 'Action',
					formatter : '<button class="aui-button" name="Update" onclick="updatePol({value})" cssClass="btn-info" icon="icon-upload-alt"><i class="icon-upload icon-white"></i>Update</button>  <button name="Delete" onclick="deletePol({value})"><i class="icon-trash icon-white"></i> Delete</button>',
					allowHTML: true
				}
				 ];
											new Y.DataTable({
												columns : columns,
												data : data,
												pageSizes : [ 4, 'Show All' ],
												rowsPerPage : 4
											}).render("#myDataTable");
										});
							}
						}
					});
				});
	}
updateDiv();

</script>

<portlet:defineObjects />
<form action="<%=callaction%>" method="post" id="redirectFrm">
	<input label="User Name" id="userid" name="userid" type="hidden"/>
</form>
<script>
function updatePol(pol_id){
	console.log("updateUser u_id:"+pol_id);
	$('#userid').val(pol_id);
	$('#redirectFrm').submit();
}

function deletePol(pol_id){
	if (confirm("Are you sure you want to delete this policy? ") == true) 
	{
		AUI().use('aui-base','aui-io-request', function(A){
		A.io.request('<%=updaContentURL%>', {
			dataType : 'json',
			method : 'GET',
			data : {
				paramName : 'deletePolicy',
				policyId: pol_id
			},
			on : {
				success : function() 
				{
					window.location.reload(true);
				} 
				 }
			});
		});
	} else 
	{
    }
}
</script>