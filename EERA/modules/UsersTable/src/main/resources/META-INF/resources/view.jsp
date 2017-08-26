<%@ include file="/init.jsp"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:resourceURL var="updContenURL" id="rajat">
</portlet:resourceURL>
<portlet:resourceURL var="newid" id="uprequest">
</portlet:resourceURL>

<portlet:actionURL var="callaction" name="callaction"></portlet:actionURL>

<p>
<div id="myDataTable"></div>
</p>
<script type="text/javascript">
var data = [];
function updateDiv(){
	AUI().use('aui-base','aui-io-request', function(A){
	var allRows="";
	A.io.request('<%=updContenURL%>', {
						dataType : 'json',
						method : 'GET',
						data : {
							paranName : 'ParamValue'
						},
						on : {
							success : function() {
								data = this.get('responseData');
								console.log("data:"+JSON.stringify(data));
								YUI().use('aui-datatable', 'datatable-paginator', 'datatable-sort',
										function(Y) {
											var columns = [ {
												key : 'User ID',
												sortable : true
											}, {
												label : 'Display Name',
												key : 'User Name',
												sortable : true
											}, {
												key : 'Mobile Number',
												sortable : true
											}, {
												key : 'E-Mail ID',
												sortable : true
											}, {
												key : 'Role Type',
												sortable : true
											},{
												key : 'Status',
												sortable : true
											},
											{
												key: 'Update User',
												formatter : '<button class="aui-button" name="Update" onclick="updateUser({value})" cssClass="btn-info" icon="icon-upload-alt"><i class="icon-upload icon-white"></i>Update</button>',
												allowHTML: true
											},
											{
												key: 'Enable/Disable User',
												formatter : '<button class="aui-button" name="Update" onclick="disableUser({value})" cssClass="btn-info" icon="icon-upload-alt"><i class="icon-upload icon-white"></i>Change Status</button>',
												allowHTML: true
											},
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
function disableUser(u_id){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=newid%>', {
               method: 'post',
               dataType : 'json',
               data:{"<portlet:namespace />userid" : u_id},
               on: {
                   success: function() {
                    alert(this.get('responseData'));
                    window.location.reload();
                   }
              }
            });
    });
}


</script>

<portlet:defineObjects />
<form action="<%=callaction%>" method="post" id="redirectFrm">
	<input label="User Name" id="userid" name="<portlet:namespace />userid" type="hidden"/>
</form>
<script>
function updateUser(u_id)
{
	console.log("updateUser u_id:"+u_id);
	$('#userid').val(u_id);
	$('#redirectFrm').submit();
	
	// $("form[name='redirectFrm']").submit();
	<%-- jQuery.ajax({
	 type : 'POST',
	 url  : '<%=callaction%>
	',
			data : {
				"<portlet:namespace />userid" : u_id
			}
		}); --%>
	}
</script>
