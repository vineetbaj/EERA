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
						dataType : 'json',
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
					key : 'bill_id',
					sortable : true,
					label: 'Bill ID'
				},

				{
					label: 'Bill Type',
					key : 'bill_type',
					sortable : true
				}, {
					label: 'Bill Amount',
					key : 'bill_printAmount',
					sortable : true
				}, {
					label: 'Uploaded by',
					key : 'bill_upBy',
					sortable : true
				},{
					label: 'No. of participants',
					key : 'numOfPart',
					sortable : true 
				},{
					label: 'Bill Date',
					key : 'bill_date',
					sortable : true 
				},{
					key: 'Action',
					formatter : '<button name="Delete" onclick="deleteBil({value})"><i class="icon-trash icon-white"></i> Delete</button>',
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
								/* A.one('#wait').setStyle('display', 'none');
								}); */
							}
						}
					});
				});
	}

	updateDiv();

	</script>
<portlet:defineObjects />
<form action="<%=callaction%>" method="post" id="redirectFrm">
	<input label="User Name" id="billid" name="<portlet:namespace />billid" type="hidden"/>
</form>
<script>
function updateBil(bill_id){
	console.log("updateUser u_id:"+bill_id);
	$('#billid').val(bill_id);
	$('#redirectFrm').submit();
}

function deleteBil(bill_id){
if (confirm("Are you sure you want to delete? ") == true) 
{
 AUI().use('aui-base','aui-io-request', function(A){
 A.io.request('<%=updaContentURL%>', {
  dataType : 'json',
  method : 'GET',
  data : {
   paramName : 'deleteBill',
   billId: bill_id
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