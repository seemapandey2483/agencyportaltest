package com.teamup.agencyportal.domain;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.model.AgentConfigInfo;
import com.teamup.agencyportal.model.MonthlyBill;
@SuppressWarnings("unchecked")
@Component
@Repository
public class ServiceClient {
 
	
     private static final String QPM_ACCESS_TOKEN = "?access_token=";
     Logger log = Logger.getLogger(getClass());

      HttpHeaders getHeaders(){
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
      }
    
	   AuthTokenInfo  sendTokenRequest() {
    	  AuthTokenInfo tokenInfo = null;
    	try{
	          RestTemplate restTemplate = new RestTemplate(); 
	          MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	  		  map.add("username", AgencyPortalConstant.REST_SERVICE_USERNAME);
	  		  map.add("password", AgencyPortalConstant.REST_SERVICE_PASSWORD);
	  		  HttpHeaders headers = new HttpHeaders();
	  		  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	  		  HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
	  		  ResponseEntity<Object> response  = restTemplate.postForEntity(AgencyPortalConstant.AUTH_SERVER_URI+"?grant_type=password&client_id=restapp&client_secret=restapp&username="+AgencyPortalConstant.REST_SERVICE_USERNAME+"&password="+AgencyPortalConstant.REST_SERVICE_PASSWORD, request, Object.class);
	  		  LinkedHashMap<String, Object> responsemap = (LinkedHashMap<String, Object>)response.getBody();
	  	    
	  		  tokenInfo = new AuthTokenInfo();
	  	    
	  		  if(responsemap.get("value")==null)
	  	    	 tokenInfo.setAccessToken((String)responsemap.get("access_token"));
	  		  else
	  	        tokenInfo.setAccessToken((String)responsemap.get("value"));
	  	      
	  		  tokenInfo.setTokenType((String)responsemap.get("token_type"));
	  	      tokenInfo.setRefreshToken((String)responsemap.get("refresh_token"));
	  	     
    	}catch(Exception e)	{
    		
    		return tokenInfo;
    	 }
  	    return tokenInfo;
      }
    
  
public  List<MonthlyBill> listAgentsMonthlyReports(long time) {
    	List<MonthlyBill> responsemap = null;
    	try{
    	log.info(time);
    		RestTemplate restTemplate = new RestTemplate(); 
    		HttpEntity<String> request = new HttpEntity(getHeaders());
    		ResponseEntity<List> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/monthlyReport/"+time+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
    				HttpMethod.GET, request, List.class);
    		responsemap = response.getBody();
    	}catch(Exception e){log.error(e);}
    		return responsemap;
    }
    
	public  List<MonthlyBill> listTransLog(long time) {
    	List<MonthlyBill> responsemap = null;
    	try{
			RestTemplate restTemplate = new RestTemplate(); 
			HttpEntity<String> request = new HttpEntity(getHeaders());
			ResponseEntity<List> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/ListTransLog/"+time+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
					HttpMethod.GET, request, List.class);
			   responsemap = response.getBody();
    	 }catch(Exception e){log.error(e);}
		return responsemap;
    }
    
    public  MonthlyBill getTransLog(long time) {
    	MonthlyBill map = null;
    	try{
			RestTemplate restTemplate = new RestTemplate(); 
			HttpEntity<String> request = new HttpEntity(getHeaders());
			ResponseEntity<MonthlyBill> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/TransLog/"+time+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
					HttpMethod.GET, request, MonthlyBill.class);
			 map = response.getBody();
    	}catch(Exception e){log.error(e);}
		return map;
    }
    
   public  boolean isValidAgent(AgentConfigInfo agent) {
	    	boolean flag = false;
	    	try{
		    	RestTemplate restTemplate = new RestTemplate();
		    	HttpEntity<String> request = new HttpEntity(getHeaders());
		    	ResponseEntity<Boolean> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/agents/"+agent.getAgentId()+"/"+agent.getPassword()+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
		    		HttpMethod.GET, request, Boolean.class);
		    	flag = response.getBody();
	    	}catch(Exception ee){return flag;}

    	return flag;
    }
    
    public  AgentConfigInfo getAgentByIdDB(String agentId) {
    	 AgentConfigInfo agent = null;
    	try{
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<String> request = new HttpEntity(getHeaders());
	        ResponseEntity<AgentConfigInfo> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/agentsDB/"+agentId+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
	        		HttpMethod.GET, request, AgentConfigInfo.class);
	        agent = response.getBody();
    	}catch(Exception e){log.error(e);}
        return agent;
    }
    
    public  boolean disableAgent(String agentid,String flag) {
    	try{
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<String> request = new HttpEntity(getHeaders());
	        ResponseEntity<Boolean> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/disableAgent/"+agentid+"/"+flag+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
	        		HttpMethod.GET, request, Boolean.class);
	        return response.getBody();
    	}catch(Exception e){log.error(e);}
        return false;
    }
    
    public  boolean createAgent(AgentConfigInfo agent) {
    	try{
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<Object> request = new HttpEntity(agent, getHeaders());
	        ResponseEntity<Boolean> response = restTemplate.exchange(AgencyPortalConstant.REST_SERVICE_URI+"/createAgent/"+QPM_ACCESS_TOKEN+sendTokenRequest().getAccessToken(),
	        		HttpMethod.POST, request, Boolean.class);
	        return response.getBody();
		}catch(Exception e){log.error(e);}
		return false;
    }
}