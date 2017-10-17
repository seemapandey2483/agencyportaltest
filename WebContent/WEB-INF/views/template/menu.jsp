<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

 <% 
 String uri = (String)request.getAttribute("javax.servlet.forward.request_uri");
 System.out.println(uri);
  if(session.getAttribute("admin")!=null && session.getAttribute("admin").equals("admin")) {
  %>
 <ul  class="nav nav-tabs" >
      
      <div class="nav navbar-nav navbar-right">
               <a href="logoutAdmin" title="Logout" class="navbar-brand glyphicon glyphicon-off text-danger"><strong>Logout</strong></a>
            </div>
	    <li id="listagents" >
	    <a  href="listagents" >
    	<dt>Home</dt></a>
	    </li>
 		<li id="getMonthlyReport">
			<a href="getMonthlyReport"> <dt>Monthly Report</dt></a>
		</li>
		<li id="GetJobsDetail">
			<a href="GetJobsDetail"> <dt>Configure Job</dt></a>
		</li>
		<li id="getJobActivity">
			<a href="getJobActivity" > <dt>Job Activity</dt></a>
		</li >
		
		
	</ul>
	
<%} %>

 <% 
  if(session.getAttribute("client")!=null && session.getAttribute("client").equals("client")) {
  %>
  <ul  class="nav nav-tabs" >
 <%if(!uri.endsWith("agentinfo")){ %>
  <li >
			<a href="agentinfo" > <dt>Home</dt></a>
		</li >
		<%} %>
       <div class="nav navbar-nav navbar-right">
               <a href="logoutAgent" title="Logout" class="navbar-brand glyphicon glyphicon-off text-danger"><strong>Logout</strong></a>
            </div>
      </ul> 
 	
<%} %>



