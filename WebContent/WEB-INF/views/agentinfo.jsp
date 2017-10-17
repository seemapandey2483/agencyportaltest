<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <jsp:useBean id="myDate" class="java.util.Date"/>  
<!-- <meta http-equiv="refresh" content="5"/> -->
<style>
 .panel-heading .accordion-toggle:after {
    /* symbol for "opening" panels */
    font-family: 'Glyphicons Halflings';  /* essential for enabling glyphicon */
    content: "\e114";    /* adjust as needed, taken from bootstrap.css */
    float: right;        /* adjust as needed */
    color: white;         /* adjust as needed */
}
.panel-heading .accordion-toggle.collapsed:after {
    /* symbol for "collapsed" panels */
    content: "\e080";    /* adjust as needed, taken from bootstrap.css */
}

</style>
<script>

function resetFields() {
	if	(document.getElementById("bankTerms") != null)
		document.getElementById("bankTerms").style.display = "none";
	if	(document.getElementById("cardTerms") != null)
		document.getElementById("cardTerms").style.display = "none";
	if	(document.getElementById("routingNumber") != null)
		document.getElementById("routingNumber").value = "";
	if	(document.getElementById("accountNumber") != null)
		document.getElementById("accountNumber").value = "";
	if	(document.getElementById("name") != null)
		document.getElementById("name").value = "";
	if	(document.getElementById("agree") != null)
		document.getElementById("agree").checked = false;
	if	(document.getElementById("agree1") != null)
		document.getElementById("agree1").checked = false;

}

function payMethod(){
	var radios = document.getElementsByName('defaultid');
	if(radios){
	  document.location = "download";
	}
}
function paymentMethod(object,message){
	var radios = document.getElementsByName('defaultid');
	object.checked = true;
 	bootbox.confirm({
    message: "<h3>Do you want "+message+" as Default?</h3>",
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
            type: 'GET',
            url: "payMethod?id="+object.value,
            success:function(data){
            
            }
        });
    	}
    }
});  
}

function deletePaymentMethod(id,info){
	
 	bootbox.confirm({
    message: "<h3>    Do you want to delete  "+info+" ? </h3>",
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
            type: 'GET',
            url: "deleteBankDetail?id="+id,
            success:function(data){
            	document.location = "agentinfo";
            }
        });
    	}
    }
});  
}


</script>

<c:if test="${not empty message}">

<div id="errorBox" class="alert alert-success">
<div class="form-group">
<div class="col-md-11"> ${message} </div> 
       <div class="col-md-1" align="right"> 
        <a  href="#"  onclick="javascript:{document.getElementById('errorBox').style.display='none';}"><span style="text-align: right;color: red;font-weight: bold">X </span></a>
       </div>  
    </div>      
 </div>
</c:if>

	 <div class="panel-group" id="accordion">
  	<div class="panel panel-primary">
    <div class="panel-heading ">
    	  <dt>Agent Information<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"></a></dt>
      </div>
              <div id="collapseOne" class="accordion-body expand">
             <div class="panel-body" >
             <div class="form-horizontal ${error != null ? 'has-error' : ''}">
             
			<div class="form-group">
            <label class="control-label col-md-2" for="agentid">Agent ID</label>
            <div class="col-md-3"> 
           <input name="agentId" type="text" maxlength="10" readonly="readonly" value="${agentForm.agentConfigInfo.agentId}" class="form-control" />
           </div>
            <div class="col-md-2"> </div>
            <label class="control-label col-md-2" for="AgencyName">Agency Name</label>
           <div class="col-md-3"> 
           <input name="agencyName" maxlength="50"  title="${agentForm.agentConfigInfo.agencyName}" value="${agentForm.agentConfigInfo.agencyName}"   readonly="readonly" type="text" class="form-control" />
             </div>
             <div class="col-md-2"> </div>
            </div>
           <div class="form-group">
           <label class="control-label col-md-2" for="ContactName">Contact Name</label>
           <div class="col-md-3"> 
           <input name="Name"  maxlength="50" title="${agentForm.agentConfigInfo.name}" readonly="readonly" value="${agentForm.agentConfigInfo.name}" type="text" class="form-control" />
           </div>
            <div class="col-md-2"> </div>
            <label class="control-label col-md-2" for="Email">Email</label>
           <div class="col-md-3"> 
           <input name="Email" type="text" maxlength="50"  readonly="readonly" value="${agentForm.agentConfigInfo.email}"  class="form-control"  />
            </div>
             <div class="col-md-2"> </div>
            </div>
           <div class="form-group">
           <label class="control-label col-md-2" for="Phone">Phone</label>
           <div class="col-md-3"> 
           <input name="Phone" type="text" readonly="readonly" maxlength="20"   value="${agentForm.agentConfigInfo.phone}"  class="form-control"  />
           </div>
           <c:if test="${agentForm.agentConfigInfo.state ne 'null'}">
            <div class="col-md-2"> </div>
            <label class="control-label col-md-2" for="StateId">State</label>
           <div class="col-md-3"> 
           <input name="State" type="text" maxlength="5"  readonly="readonly"  value="${agentForm.agentConfigInfo.state}"  class="form-control" />
            </div>
            </c:if>
             <div class="col-md-2"> </div>
            </div>
		  <div class="form-group">
		    <c:if test="${agentForm.agentConfigInfo.zip ne 'null'}">
           <label class="control-label col-md-2" for="Zip">Zip</label>
           <div class="col-md-3"> 
           <input name="Zip" type="text" maxlength="6"  readonly="readonly" value="${agentForm.agentConfigInfo.zip}"  class="form-control"  /> </div>
           
            <div class="col-md-2"> </div>
            </c:if>
            <label class="control-label col-md-2" for="LASTDL_DT">Last Downloaded Date</label>
           <div class="col-md-3"> 
        		<c:set target="${myDate}" property="time" value="${agentForm.agentConfigInfo.lastDownloadDt}"/>    
           <input name="LastDownloadDt" maxlength="20"  type="text" readonly="readonly"  value="${myDate}"  class="form-control"  /> </div>
            <div class="col-md-2"> </div>
            </div>
             </div>
            </div>
            </div>  
            </div> 	
   </div> 
   
   <div class="panel-group" id="accordion1">
  <div class="panel panel-primary">
    <div class="panel-heading">
      <dt>
       Payment Method
        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion1" href="#collapseOne2">
        </a>
        </dt>
      </div>
        <div id="collapseOne2" class="panel-collapse collapse in">
          <div class="panel-body">
           <div class="form-group">
           <label class="control-label col-md-2"><h4>Bank Accounts</h4></label>
           <div class="col-md-9"> 
           </div>
           <div class="col-md-1"> 
            <button type="button" class="btn btn-danger" name="bankButton" id="bankButton" data-toggle="modal" data-target="#myModalBank" style="float:right;" >Add Bank Account</button>
           </div>
     	</div>
   		<table id="myTableBank" class="table table-striped table-bordered table-hover table-condensed text-center">
     	 	<tr>
       			<th class="text-center">Select Default</th>
       		 	<th class="text-center">Name on Account</th>
       		 	<th class="text-center">Bank Name</th>
       		 	<th class="text-center">Account Number</th>
			 	<th class="text-center">Routing Number</th>
			 	<th class="text-center">Account Type</th>
			 	<th class="text-center">Delete</th>
			</tr>
   		
   		 <%! int counter = 0 ;%>
    	 <c:forEach items="${agentForm.listPaymentConfig}" var="bankDetails" varStatus="status">
			<c:if test="${not empty bankDetails.accountNumber}">
			<tr>
			<c:if test="${bankDetails.agree==1}">
			 <td><input name='defaultid' id="default${bankDetails.id}" onclick="paymentMethod(this,'Bank Account');" checked="checked" type="radio" value="${bankDetails.id}"  /> </td>
			 </c:if>
			<c:if test="${bankDetails.agree==0}">
			 <td><input name='defaultid' id="default${bankDetails.id}" onclick="paymentMethod(this,'Bank Account');"  type="radio" value="${bankDetails.id}"  /> </td>
			</c:if>
			<%counter =  counter+1 ;%>
			 <td> ${bankDetails.name} </td>
			 <td>${bankDetails.bankName} </td>
			<td>${bankDetails.accountNumber}</td>
			<td>${bankDetails.routingNumber} </td>
			<td>${bankDetails.accountType} </td>
			<td style="display:none;">${bankDetails.id}</td>
			<td align="center"><a href="javascript:deletePaymentMethod('${bankDetails.id}','Bank Account');" type="button"  class="glyphicon glyphicon-trash"  ></a></td>
				</tr>
			</c:if>
	</c:forEach>
	 </table>
	<%
	if ( counter == 0 ) {
	%>
	 <table  class="table  table-bordered">
	<tr> <th class="text-center">Record not available</th></tr>
	 </table>
	<%}
	counter = 0 ;
	%>
 
 
 <div class="form-group">
           <label class="control-label col-md-2"><h4>Credit Cards</h4></label>
           <div class="col-md-9"> 
           </div>
           <div class="col-md-1"> 
  			 <button type="button" class="btn btn-danger" name="cardButton" id="cardButton" data-toggle="modal" data-target="#myModalCard" style="float:right;" >Add Card Details</button>
           </div>
     	</div>
   <table id="myTableCard" class="table table-striped table-bordered table-hover table-condensed text-center">
   
      <tr > 
      <th class="text-center">Select Default</th>
      <th class="text-center">Card Number</th>
        <th class="text-center">Name On Card</th>
        <th class="text-center">Expiry Date</th>
         <th class="text-center">Card Type</th>
           <!-- <th>Security Code</th> -->
	<th class="text-center">Delete</th>
    </tr>
   
  
   
        <c:forEach items="${agentForm.listPaymentConfig}" var="cardDetails" varStatus="status">
        	<c:if test="${not empty cardDetails.lastFourDigit}">
		<tr>
		<c:if test="${cardDetails.agree==1}">
			 		<td><input name='defaultid' id="default${cardDetails.id}" onclick="paymentMethod(this,'Credit Card');" checked="checked" type="radio" value="${cardDetails.id}"  /> </td>
		</c:if>
		<c:if test="${cardDetails.agree==0}">
			 	<td><input name='defaultid' id="default${cardDetails.id}" onclick="paymentMethod(this,'Credit Card');"  type="radio" value="${cardDetails.id}"  /> </td>
		</c:if>
		<% 
			counter = counter + 1; 
		%>
			<td>XXXX XXXX XXXX ${cardDetails.lastFourDigit}</td>
			<td>${cardDetails.name}</td>
			<td>${cardDetails.expiryDate} </td>
			<td>${cardDetails.cardType} </td>
			<%-- <td>${cardDetails.securityCode}</td> --%>
			<td style="display:none;">${cardDetails.id}</td>
			<td align="center"><a href="#" onclick="javascript:deletePaymentMethod('${cardDetails.id}','Credit Card');" type="button"  class="glyphicon glyphicon-trash"  ></a></td>
			</tr>
			</c:if>
		
	</c:forEach>
	  </table>
	<%
	if ( counter == 0 ){ 
	%>
	  <table  class="table  table-bordered">
	<tr> <th class="text-center">Record not available</th></tr>
	 </table>
	<%} %>

  </div>
 </div>	 
</div>


</div>     



 
<div>
 				  <div class="form-group">
               <div class="col-md-5"> </div>
 				<div class="col-md-2"> 
           		 <a onclick="payMethod();" class="btn btn-lg btn-primary btn-block" type="button" >Download Setup</a>
         		 </div>
         		 <div class="col-md-5"></div>
         		 </div> 
         		 
         		 
      <div class="modal fade" id="myModalBank" role="dialog">
     <div class="modal-dialog modal-lg" >
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close"  onclick="javascript:{resetFields();}" data-dismiss="modal">&times;</button>
          <h4 class="modal-title panel">Bank Account Details</h4>
        </div>
        <div class="modal-body">
           <input name="id" type="hidden"   />
           <jsp:include page = "bank.jsp"/>
          </div>
      </div>
    </div>
  </div> 
  <div class="modal fade" id="myModalCard" role="dialog">
     <div class="modal-dialog modal-lg" >
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="javascript:{resetFields();close();}"  data-dismiss="modal">&times;</button>
          <h4 class="modal-title panel">Card Details </h4>
        </div>
        <div class="modal-body " >
         <input name="id1" type="hidden"   />
        <%@ include file = "card.jsp" %>
        
      </div>
      </div>
    </div>
  </div> 
</div>
