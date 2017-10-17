<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="head.jsp" />
<script type="text/javascript">

function test(){

    //e.preventDefault();
     $('#reports').jtable('load', {
        month: $('#month').val(),
        year: $('#year').val()
    }); 
    
     $.ajax({
         type: 'GET',
         url: "getMonthlyReport",
         success:function(data){
         
         }
     });
}

	
</script>

  <form method="GET" action="getMonthlyReport" >
  		<div class="panel panel-primary">
    <div class="panel-heading ">Monthly Record</div>
     <div class="panel-body" >
  		<div class="form-group">
  		 <div class="col-md-2"> </div>
           <label class="control-label col-md-1" for="Month">Month</label>
           <div class="col-md-2"> 
           <% String[] month = {"January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"};%>
           <select name="month" id="month" class="selectpicker">
           <%
           
           for( int i=0 ;i<month.length;i++) 
        	   {%>
  				<option value="<%=i +1%>"><%=month[i] %></option>
  			<%
  			} %>
 
				</select>
            </div>
           
            <label class="control-label col-md-1" for="year">Year</label>
            <div class="col-md-2"> 
           <% String[] Year = {"2010", "2011", "2012", "2013", "2014", "2015","2016", "2017", "2018", "2019", "2020", "2021"};%>
           <select name="year" id="year" class="selectpicker">
           <%
           for( String str :Year) 
        	   {%>
  				<option value="<%=str %>" ><%=str %></option>
  			<%} %>
 
			</select>
            </div>
             <div class="col-md-2"> 
              <button type="button" onclick="test()" class="btn  btn-primary btn-block" id="LoadRecordsButton">Get Report</button>
            </div>
            <div class="col-md-2"> </div>
            </div>
</div></div>

</form>


<script>
 $(document).ready(function() {
	 
	 $.ajax({
         url : 'report.jsp',
         success : function(data) {
             $('#result').html(data);
         }
     }),
     
		$('#reports').jtable({
			title : 'List Monthly Report',
			    paging: true, 
			    pageSize: 3,
	          
			actions : {
				method:"POST",
				listAction : 'getMonthlyReport',
				
			},
			fields : {
				
				agentCID : {
					title : 'Agent Id',
					width : '10%',
					edit : false,
					key:true,
					list:true
				},
			
				month : {
					title : 'Month',
					width : '10%',
					key : true
				},year : {
					title : 'Year',
					width : '10%',
					key : true
				},amount : {
					title : 'Amount',
					width : '10%'
				},creationDt : {
					title : 'Creation Date',
					width : '10%',
					display: function (data) {
						return new Date(data.record.creationDt).toUTCString();
				    }
				},transationDt : {
					title : 'Transaction Date',
					width : '10%',
						display: function (data) {
							return new Date(data.record.transationDt).toUTCString();
					    }
				}
				
				
			}
		});
		$('#reports').jtable('load');
	});
</script>

 <div id="reports"></div>



 
