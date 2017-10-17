<jsp:include page="head.jsp" />
 <script type="text/javascript">
 
    $(document).ready(function () {
 
        $('#jobactivity').jtable({
            title: 'Job List',
            defaultSorting: 'Name ASC',
            actions: {
                listAction: 'GetJobsDetail',
               
            },
            fields: {
            	Phones: {
                    title: 'Jobs',
                    width: '5%',
                    paging: true, 
                    pageSize: 5, 
                    
                    display: function (jobData) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="images/Activity.png" title="Job Activity" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#jobactivity').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title:  'Job Activity For Job ID::'+jobData.record.jobId,
                                        paging: true, 
                                        pageSize: 10, 
                                        defaultSorting: 'Name ASC',
                                        actions: {
                                            listAction: 'getJobActivity?jobId=' + jobData.record.jobId,
                                           
                                        },
                                        fields: {
                                        	
                                        	
                                        	  Phones: {
                                                  title: 'Activities',
                                                  width: '5%',
                                                 
                                                  display: function (jobData1) {
                                                      //Create an image that will be used to open child table
                                                      var $img = $('<img src="images/transaction.png" title="Transaction Details" />');
                                                      //Open child table when user clicks the image
                                                      $img.click(function () {
                                                          $('#jobactivity').jtable('openChildTable',
                                                                  $img.closest('tr'),
                                                                  {
                                                        	  			title:  'Transaction For Job ID::'+jobData1.record.jobId,
                                                        	  			 paging: true, //Enable paging
                                                                         pageSize: 7, //Set page size (default: 10)
                                                        	  			actions: {
                                                                          listAction: 'getTransanctionDetails?jobId=' + jobData1.record.id,
                                                                         
                                                                      },
                                                                      fields: {
                                                                    	  jactId: {
                                                                              type: 'hidden',
                                                                              defaultValue: jobData1.record.jactId
                                                                          },
                                                                          jactId : {
                                                          					title : 'Activity Id',
                                                          					width : '10%',
                                                          					 key : true,
                                                                              list : true,
                                                                      	},
                                                          			
                                                          				amount : {
                                                          					title : 'Amount',
                                                          					width : '10%'
                                                          				},lastFourDigit : {
                                                          					title : 'Last Four Digit',
                                                          					width : '10%'
                                                          				},routingNumber : {
                                                          					title : 'Routing Number',
                                                          					width : '10%'
                                                          				},creationDt : {
                                                          					title : 'Creation Date',
                                                          					width : '10%',
                                                    						display: function (data) {
                                                    							return new Date(data.record.creationDt).toUTCString();
                                                    					    }
                                                          				},txnDt : {
                                                          					title : 'Transaction Date',
                                                          					width : '10%',
                                                    						display: function (data) {
                                                    							return new Date(data.record.txnDt).toUTCString();
                                                    					    }
                                                          				},description : {
                                                          					title : 'Description',
                                                          					width : '10%',
                                                          				},status : {
                                                          					title : 'Status',
                                                          					width : '10%'
                                                          				}
                                                                      }
                                                                  }, function (data) { //opened handler
                                                                      data.childTable.jtable('load');
                                                                  });
                                                      });
                                                      //Return image to show on the person row
                                                      return $img;
                                                  }
                                          
                                              },
                                        	
                                        	
                                        	jobId: {
                                                type: 'hidden',
                                                defaultValue: jobData.record.jobId
                                            },
                                            jobId : {
                            					title : 'Job ID',
                            					width : '5%',
                            					 key : true,
                                                 list : true,
                                                
                                         	},
                            			
                            				jobStartTime : {
                            					title : 'Job Start Time',
                            					width : '10%',
                            						display: function (data) {
                            							return new Date(data.record.jobStartTime).toUTCString();
                            					    }
                            				},jobEndTime : {
                            					title : 'Job End Time',
                            					width : '10%',
                        						display: function (data) {
                        							return new Date(data.record.jobEndTime).toUTCString();
                        					    }
                            				},noOfTransactionFailed : {
                            					title : 'Failed Transaction Count',
                            					width : '15%'
                            				},noOfTransactionSuccess : {
                            					title : 'Success Transaction Count',
                            					width : '15%'
                            				},status : {
                            					title : 'Status',
                            					width : '10%'
                            				},active : {
                            					title : 'Active',
                            					width : '10%'
                            				}
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                        });
                        //Return image to show on the person row
                        return $img;
                    }
            
                },
             
                jobId : {
					title : 'Job ID',
					width : '10%',
					 key : true,
                     list : true   ,
            	},
				jobDesc : {
					title : 'Job Desc',
					width : '10%'
				},jobGrpName : {
					title : 'Job Group Name',
					width : '10%'
				},jobTrigName : {
					title : 'Job Trigger Name',
					width : '10%'
				},active : {
					title : 'Active',
					width : '10%'
				}
            }
        });
 	  $('#jobactivity').jtable('load');
 
    });
 
</script>

 
<div id="jobactivity"></div>
