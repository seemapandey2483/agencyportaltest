package com.teamup.agencyportal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccount;
import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.model.AgentConfigInfo;
@Component
@Repository
public class CustomerService {
	Logger log = Logger.getLogger(getClass());

	static{
		Stripe.apiKey = AgencyPortalConstant.STRIPES_API_SEC_KEY;
	}
	public String createCustomer(AgentConfigInfo config,String token) {
		
		 Map<String, Object> customerParams = new HashMap<>();
		 Map<String, Object> customerMetaParams = new HashMap<>();
		String custId = null;
		try {
			 customerParams.put("description", config.getAgencyName());
			 customerParams.put("email", config.getEmail());
			 customerMetaParams.put("AGENTID", config.getAgentId());
			 customerMetaParams.put("CARRIERID", config.getCarrierId());
			 customerParams.put("source", token);
			 customerParams.put("metadata", customerMetaParams);
			 Customer cust = Customer.create(customerParams);
			 custId = cust.getId();
			
    	} catch (Exception  e) {
				log.error("createCustomer="+e);
			}
		return 	 custId;
	}
	
	
	public Customer getCustomer(String custId) {
		
		Customer cust = null;
		try {
			cust = Customer.retrieve(custId);
		} catch (Exception  e) {
			log.error(" getCustomer = "+e);
		}
		return cust;
	}
	public Charge chargeCustomer(String custId, long amount,String accountId) {
		   Charge charge =  null;
		   try {
				Customer cust = getCustomer(custId);
				final Map<String, Object> chargeParams = new HashMap<>();
			    chargeParams.put("amount", amount);
			    chargeParams.put("currency", "usd");
			    chargeParams.put("description", cust.getDescription());
			    chargeParams.put("customer", cust.getId());
			    chargeParams.put("receipt_email", cust.getEmail());
			    
			    if(accountId != null)
			    	verifyAccount(cust.getId(),accountId);
			    
			    charge = Charge.create(chargeParams);
			   
			    return charge;
				
		   } catch (Exception  e) {
					log.error("chargeCustomer= "+e);
				 return charge;
			}
	}
	
	void verifyAccount(String customerid,String accountId){
	 try {
		com.stripe.model.Customer cust = com.stripe.model.Customer.retrieve(customerid);
		ExternalAccount source = cust.getSources().retrieve(accountId);
		
		//verify the account
		Map<String, Object> params = new HashMap<>();
		ArrayList<Integer>amounts = new ArrayList<>();
		amounts.add(32);
		amounts.add(45);
		params.put("amounts", amounts);
		source.verify(params);
		
		
	 } catch (Exception  e) {
		log.error("verifyCostomer= "+e);
	}
	
	}
	
	public Charge retriveCharge(String chargeId) {
		   Charge charge =  null;
		   try {
			    charge = Charge.retrieve(chargeId);
			    charge.capture();

			    return charge;
				
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
					| APIException e) {
					log.error("chargeCustomer= "+e);
				 return charge;
			}
	}
	

}
