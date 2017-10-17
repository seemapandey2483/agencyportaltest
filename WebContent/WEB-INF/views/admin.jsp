
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  



 <script type="text/javascript" src="js/captchamatch.js"></script>

<body onload="Captcha();">
<div class="container" >
	<div class="row">
		<div class="col-sm-6 col-md-4 col-md-offset-4">
		<div class="account-wall">
		<div>
    <form:form id="loginForm" method="post" action="admin"  modelAttribute="user">
 <div class="row" align="center">
    <img src="images/user.png" alt="User" width="10%" >
  </div>
         <div class="form-horizontal">
       		<h3 align="center">TEAM-UP Login</h3>
           	 <div class="form-group " id="ErrorMessageDiv" style="display:none ">
       		  <div class="col-md-1"> </div>
       <div class="col-md-10 alert-danger">
            <label  class="control-label" > 
 				<p id="ErrorMessage" style="text-align:center;solid red">       </p>    
              </label>  
               </div>
               <div class="col-md-1"> </div>
            </div> 
             <div class="form-group">
           	 <div class="col-md-1"> </div>
            <label  class="control-label col-md-4" for="userName" >User Id:</label>
            <div class="col-md-6"> 
          		<form:input id="userName" name="userName"   path="userName"  class="form-control" placeholder="userName" autofocus="true"/>
                 </div>  
                  <div class="col-md-1"> </div>
            </div> 
                <div class="form-group">
            <div class="col-md-1"> </div>
              <label  class="control-label col-md-4" for="password" >Password:</label>
            <div class="col-md-6"> 
            <form:password id="password" name="password" path="password"  class="form-control" placeholder="Password"/>
            <span>${error}</span>
              </div>  
               <div class="col-md-1"> </div>
            </div> 
              <div class="form-group">
        		 <label  class="control-label col-md-5" for="password">Captcha:</label>
					<div class="col-md-3"> 
						<div>
							<input class="btn-lg btn-default disabled " type="button" id="mainCaptcha"/>
						</div>
					</div>  
               <div class="col-md-4" align="center">
					<a href="#" >
						<img src="images/refresh.png" id="refresh"  onclick="Captcha();" />
					</a>
			</div> 
		</div> 
			
		
            
				<div class="form-group">
				
         		 <label class="control-label col-md-5" for="password">Enter Captcha:</label>
					<div class="col-md-6">    
					<input required="required" type="text" maxlength="6" name="txtInput" id="txtInput"   class="form-control"/>
					</div>
					 <div class="col-md-1"> </div>
				</div>
          <div class="form-group">
             <div class="col-md-5"></div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
 			<div class="col-md-4"> 
           		 <button name="paymethodBTN" id="paymethodBTN" class="btn btn-md btn-primary btn-block" type="button"  onclick="return LoginUser();"  >Login</button>
              </div>   
               <div class="col-md-3"> </div> 
            </div> 
            
        </div>

    </form:form>
    </div>
                <span class="clearfix"></span>
    </div>
    </div>
    </div>
</div>

</body>