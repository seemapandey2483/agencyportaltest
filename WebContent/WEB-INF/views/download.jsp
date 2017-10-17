
<%@ page import="com.teamup.agencyportal.model.AgentConfigInfo" %>

  <%
         
         AgentConfigInfo agentInfo = (AgentConfigInfo) session.getAttribute("currentAgent");
         %>
  <div class="panel-group" id="accordion">
  <div class="panel panel-primary">
    <div class="panel-heading">
      <dt>
       Download Setup
        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne2">
        </a>
        </dt>
      </div>
       <div id="collapseOne2" class="accordion-body expand">
      <div class="panel-body">
      <div class="form-group">
        <div class="col-xs-12">
                <p>ANANDTEST is upgrading to a new download system in order to better serve you, our agents.
                  We have attempted to make the transition as seamless as possible to keep from interrupting your daily schedule. However
                , we will need for you to complete a quick, one-time setup and registration</p>
           
        </div>
         </div>
          <div class="form-group">
         <div class="col-xs-12">
                <p>Please <a target="_blanck" style="color: red" href="http://teamup.ebix.com/teamupreg/agents?carid=<%=agentInfo.getCarrierId() %>&shortname=ANAND&avs=AL3DNLD&agentid=<%=agentInfo.getAgentId() %>&mig=N"><h2>click here</h2></a>  to install TEAM-UP Download. If you are already using TEAM-UP Download with other companies, the installation process will automatically detect and skip parts of the installation. You will not lose any information or settings when adding a new company to the download process</p>
        </div>
         
    </div>
<div class="form-group">
         <div class="col-xs-12">
       
           <p>After installing TEAM-UP Download, you will be asked to login to the application and verify some setup information. Please use the following values when asked to login:</p>
           <p><h1>Carrier ID:  	<%=agentInfo.getCarrierId() %></h1></p>
             <p><h1>Agent ID:  	<%=agentInfo.getAgentId() %></h1></p>
            <p><h1>Password ID:  	<%=agentInfo.getPassword() %></h1></p>
           <br/>
             <p> Thank you for placing your business with ANANDTEST! </p>
        </div>
    </div>
    </div></div>  </div></div>  



  