<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  




<HEAD>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href = "images/Ebix.png" rel="icon" type="image/png">
<%-- <LINK rel="stylesheet" href="<c:url value="css/jtable.css"/>" type="text/css">
 --%>
<TITLE>TEAM-UP Agency Carrier Administration</TITLE>
<STYLE type="text/css">
 h5{
 
    width:28%;
    
      margin-left:35%;
     align:center;
      height:10%;
     background-color:#D3D3D3;
}

#mainCaptcha
{
	border: 0;
	width: 130px;
	outline: none;
}

.account-wall
{
    margin-top: 20px;
    padding: 10px 0px 10px 0px;
    background-color: #D3D3D3;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

</STYLE>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script src="<spring:url value="js/bootbox.min.js"/>" type="text/javascript"></script>
    <script src="<spring:url value="js/jquery.validate.js"/>" type="text/javascript"></script>
   <script src="<spring:url value="js/jquery-1.8.2.js"/>" type="text/javascript"></script>
  
<%

response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String path = "http:\\\\"+request.getServerName()+":"+request.getServerPort()+"\\"+request.getContextPath();
   if(session.getAttribute("token")==null){
    response.sendRedirect(request.getContextPath() + "/logout");

}
%>
<script>
window.location.hash="no-back-button";
window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history
window.onhashchange=function(){window.location.hash="";}
window.onhashchange=function(){location.hash='';}

</script> 
</HEAD>
<body >
<div class="container" >
<div style="width:100%;background-color: #D3D3D3;">
   <h1 style="padding: 10px 10px 10px 10px"><img src="images/header.png" width="20%" height="10%" > 
      <% if(session.getAttribute("agent")!=null){%>
       <div align="right"><a href="logoutAgent" ><span  class="glyphicon glyphicon-log-out">Logout</span></a> </div>
        <% }%>
   </h1>
   </div>
   
