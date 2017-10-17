<jsp:include page="head.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#listjobs').jtable({
			title : 'Job Details',
			    columnResizable: true, //Actually, no need to set true since it's default
	            saveUserPreferences: true, //Actually, no need to set true since it's default
	            defaultSorting: 'Name ASC',
			actions : {
				method:"POST",
				listAction : 'GetJobsDetail',
				updateAction: 'updateJobsDetail',
				deleteAction: 'deleteJobsDetail',
			},
			fields : {
				  Phones: {
	                    title: 'Start Now',
	                    width: '10%',
	                    edit: false,
	                    create: false,
	                    display: function (jobData) {
	                        var $img = $('<img src="images/startnow.png" title="Start Now" />');
	                        $img.click(function () {
	                        	
	                         	bootbox.confirm({
	                         	    message: "<h3><strong>Do You want start This job now? </strong></h3>",
	                         	    buttons: {
	                         	        confirm: {
	                         	            label: 'Yes',
	                         	            className: 'btn-success'
	                         	        },
	                         	        cancel: {
	                         	            label: 'No',
	                         	            className: 'btn-danger'
	                         	        }
	                         	    },
	                         	    callback: function (result) {
	                         	    	if(result==true){
	                         	    		$.ajax({
	        	                                type: 'POST',
	        	                                url: "startNow?jobId=" + jobData.record.jobId,
	        	                                success:function(data){
	        	                                }
	        	                            });
	                         	    	}
	                         	    }
	                         	}); 
	                        });
	                        return $img;
	                    }
	                },
				jobId : {
					title : 'Job ID',
					width : '7%',
					 key : true,
                     list : true,
                     edit : false,
                     create : false
				},
			
				jobName : {
					title : 'Job Name',
					width : '10%',
					edit : false
				},jobDesc : {
					title : 'Job Desc',
					width : '10%',
					edit : false
				},jobGrpName : {
					title : 'Group Name',
					width : '10%',
					edit : false
				},jobTrigName : {
					title : 'Trigger Name',
					width : '10%',
					edit : false
				},
				jobClassName : {
					title : 'Class Name',
					width : '10%',
					edit : false
				}
				,jobTrigStr : {
					title : 'Trigger Str',
					width : '10%',
				    create: false,
	                 edit: true,
					
				},active : {
					title : 'Active',
					width : '5%',
					maxlength: 1,
					edit: true,
					create: false,
					inputClass: 'validate[max[1]]'
					
				},lastRunDate : {
					title : 'Last Run Date',
					width : '12%',
					edit : false,
					create: false
				}
			}
		});
		$('#listjobs').jtable('load');
	});
</script>


 <div id="listjobs"></div>

