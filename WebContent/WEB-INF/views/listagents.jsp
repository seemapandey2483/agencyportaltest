
<jsp:include page="head.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#listAgents').jtable({
			title : 'List Of Registered Agents',
			paging: true, 
	        pageSize: 10,
	        defaultSorting: 'Name ASC',
			actions : {
				method:"POST",
				listAction : 'listagents',
			},
			fields : {
				 
				agentId : {
					title : 'Agent Id',
					width : '10%',
					
					key:true,
					list:true
				},
			
				agencyName : {
					title : 'Agency Name',
					width : '10%'
				
				},name : {
					title : 'Name',
					width : '10%'
				},email : {
					title : 'Email',
					width : '10%'
				},phone : {
					title : 'Phone',
					width : '10%'
				},city : {
					title : 'City',
					width : '10%'
				},address1 : {
					title : 'Local Address',
					width : '10%'
				},address2 : {
					title : 'Permanent Address',
					width : '15%'
				},state : {
					title : 'State',
					width : '5%'
				},zip : {
					title : 'Zip',
					width : '5%'
				},Phones: {
                    title: 'DeActivate',
                    width: '10%',
                    display: function (Data) {
                        var $img = $('<img src="images/deactivate.jpg" title="DeActivate Agent" />');
                        $img.click(function () {
                        	
                        	$.ajax({
                                type: 'POST',
                                url: "updateAgentStatus=agentId" + Data.record.agentId,
                                success:function(data){
                                }
                            });
                                   
                        });
                        return $img;
                    }
                },
				
				
			}
		});
		$('#listAgents').jtable('load');
	});
</script>

 

 <div id="listAgents"></div>





