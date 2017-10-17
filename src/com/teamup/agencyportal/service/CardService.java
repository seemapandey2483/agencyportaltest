package com.teamup.agencyportal.service;

import java.util.HashMap;
import java.util.List;
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
import com.stripe.model.BankAccount;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccount;
import com.stripe.model.ExternalAccountCollection;
import com.stripe.model.Token;
import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.model.AgentPaymentConfig;
@Component
@Repository
public class CardService {
	 Logger log = Logger.getLogger(getClass());
	 CustomerService customerService = new CustomerService();
	static{
		Stripe.apiKey = AgencyPortalConstant.STRIPES_API_SEC_KEY;
	}

	public AgentPaymentConfig createCreditCard(String custId,String token,AgentPaymentConfig paymentConfig) {

			try {

			Customer cust =customerService.getCustomer(custId);
			paymentConfig = extracted(custId, token,  cust,paymentConfig);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return paymentConfig;

	}

	private AgentPaymentConfig extracted(String custId, String token,Customer cust,AgentPaymentConfig paymentConfig) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Card card = null;
		try{
			 card = cust.createCard(token);
		}catch(Exception e) {
			token = createCardToken();
		}
		try{
		Customer customer =  null;
		if (card == null) {
			 customer = Customer.retrieve(custId);
			 card = customer.createCard(token);
			
		}
		if (card == null) {
			Map<String, Object> cardParams = new HashMap<>();
			cardParams.put("source", token);
			card = customer.createCard(cardParams);
		}
		if (card != null) {
			paymentConfig.setAccontId(card.getId());
			paymentConfig.setCardType(card.getBrand());
			paymentConfig.setLastFourDigit(card.getLast4());
			paymentConfig.setName(card.getName());
			paymentConfig.setExpiryDate(card.getExpMonth()+"/"+card.getExpYear());
			paymentConfig.setSecurityCode(token);
		}
		}catch(Exception e){log.error(e);}
		return paymentConfig;
	}
	
	String createCardToken() {
		String token = null;
		try{
			Map<String, Object> tokenParams = new HashMap<>();
			Map<String, Object> cardParams = new HashMap<>();
			cardParams.put("number", "4242424242424242");
			cardParams.put("exp_month", 6);
			cardParams.put("exp_year", 2018);
			cardParams.put("cvc", "314");
			tokenParams.put("card", cardParams);
			token = Token.create(tokenParams).getId();
		
		}catch(Exception e){log.error(e);}
		return token;
	}
	public AgentPaymentConfig saveCreditCardInfor(AgentPaymentConfig paymentConfig,String token ){
		
		try {
			paymentConfig = createCreditCard(paymentConfig.getCustomerId(),token,paymentConfig);
			paymentConfig.setActive("Y");
		
		}catch(Exception e){log.error(e);}
		return paymentConfig;
	}
	
	public AgentPaymentConfig saveBankDetailsInfor(AgentPaymentConfig paymentConfig,String token ){
		BankAccount bankAccount = createBank(paymentConfig.getCustomerId(),paymentConfig,token);
		if ( bankAccount == null ) {
			bankAccount = getBankAccount(paymentConfig.getCustomerId());
		}
		paymentConfig.setAccontId(bankAccount.getId());
		paymentConfig.setBankName(bankAccount.getBankName());
		paymentConfig.setAccountNumber(bankAccount.getAccount());
		paymentConfig.setRoutingNumber(bankAccount.getRoutingNumber());
		paymentConfig.setAccountType(bankAccount.getAccountHolderType());
		paymentConfig.setName(bankAccount.getAccountHolderName());
		paymentConfig.setAgentCID(paymentConfig.getAgentCID());
		paymentConfig.setSecurityCode(token);
		paymentConfig.setAgree("1");
		paymentConfig.setActive("Y");
		return paymentConfig;
	}
	
	public Card createCreditCardFromToken(String custId) {

		final Map<String, Object> cardParams = new HashMap<>();
		Card card = null;
		try {

			Customer cust = customerService.getCustomer(custId);
			 card = cust.createCard(cardParams);
			log.info(card.getId());

		} catch (Exception  e) {
			log.error(e.getMessage());
		}
		return card;

	}
	
	public BankAccount createBank(String custId, AgentPaymentConfig pConfig,String token) {

		final Map<String, Object> bankParams = new HashMap<>();
		final Map<String, Object> params = new HashMap<>();
		final Map<String, Object> tokenParams = new HashMap<>();
		BankAccount account =  null;
		Customer cust = null;
		try {

			cust = customerService.getCustomer(custId);
			bankParams.put("country", "US");
			bankParams.put("currency", "usd");
			bankParams.put("account_holder_name", pConfig.getName());
			bankParams.put("account_holder_type", "individual");
			bankParams.put("account_number", pConfig.getAccountNumber());
			bankParams.put("routing_number", pConfig.getRoutingNumber());

			tokenParams.put("bank_account", bankParams);
			params.put("source",token );
			account = (BankAccount) cust.getSources().create(params);
		} catch (Exception  e) {
			log.error(e);
		}
		return account;

	}
	@SuppressWarnings("deprecation")
	public BankAccount getBankAccount(String custId) {
	 try {
			com.stripe.model.Customer cust = com.stripe.model.Customer.retrieve(custId);
			final Map<String, Object> cardParams = new HashMap<>();
			cardParams.put("limit", 3);
			cardParams.put("object", "bank_account");
			ExternalAccountCollection coll = cust.getSources().all(cardParams);
			List<ExternalAccount> actList = coll.getData();
			return (BankAccount)actList.get(0);
			
	 } catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException	| APIException e) {
				log.error(e.getMessage());
		}
	
	 return null;
	}
	 
	public Charge chargeWithCard(Card card) {
		 Charge charge = null;
		 try{  
			final Map<String, Object> cardParams = new HashMap<>();
		    cardParams.put("number", card.getAccount());
		    cardParams.put("exp_month", card.getExpMonth());
		    cardParams.put("exp_year",card.getExpYear());
		    cardParams.put("cvc", card.getCvcCheck());
		    cardParams.put("name", card.getName());
		    cardParams.put("address_line1", card.getAddressLine1());
		    cardParams.put("address_line2", card.getAddressLine2());
		    cardParams.put("address_city", card.getAddressCity());
		    cardParams.put("address_zip", card.getAddressZip());
		    cardParams.put("address_state", card.getAddressState());
		    cardParams.put("address_country", card.getAddressCountry());
	
		    final Map<String, Object> chargeParams = new HashMap<>();
		    chargeParams.put("amount", 100);
		    chargeParams.put("currency", "usd");
		    chargeParams.put("card", cardParams);
	
		    charge = Charge.create(chargeParams);
	   
		}catch(Exception e){log.error("chargeWithCard="+e);}
		 
		return charge;
	  }
}
