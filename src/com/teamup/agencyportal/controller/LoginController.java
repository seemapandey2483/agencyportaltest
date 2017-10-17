package com.teamup.agencyportal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.domain.AgentForm;
import com.teamup.agencyportal.domain.ServiceClient;
import com.teamup.agencyportal.email.EmailService;
import com.teamup.agencyportal.model.AgentConfigInfo;
import com.teamup.agencyportal.model.AgentPaymentConfig;
import com.teamup.agencyportal.service.AgentConfigInfoService;
import com.teamup.agencyportal.service.AgentPaymentConfigService;
import com.teamup.agencyportal.service.CardService;
import com.teamup.agencyportal.service.CustomerService;

@Controller
public class LoginController {
	AgentForm agentForm = new AgentForm();
	private Logger log = Logger.getLogger(getClass());
	
	ModelAndView modelAndViewLogout = new ModelAndView("redirect:logoutAgent");
	
	ModelAndView modelAndViewAgentInfo = new ModelAndView("redirect:agentinfo");
	
	@Autowired
	AgentConfigInfoService agentConfigInfoService;
	@Autowired
	AgentPaymentConfigService agentPaymentConfigService;
	@Autowired
	CommonDAO dao;
	@Autowired
	CustomerService customerService;
	String message = "Welcome  to Agency Portal Payment Configuration System." ;
	@Autowired
	CardService cardService;
	@Autowired
	ServiceClient serviceClient;
	@Autowired
	HttpSession session;
	@RequestMapping(value = { "/", "/client**" }, method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request,HttpServletResponse response,AgentConfigInfo agentConfigInfo) {
		
		session.setAttribute(AgencyPortalConstant.CURRENT_AGENT, null); 
		session.setAttribute(AgencyPortalConstant.CLIENT, null);
		session.setAttribute(AgencyPortalConstant.ADMIN, null);
		ModelAndView model = new ModelAndView("login");
		model.addObject("agentConfigInfo", agentConfigInfo);
		message ="";
		return model;
	}
	@ResponseBody
	@RequestMapping(value = { "/", "/validate**" }, method = RequestMethod.GET)
	public String validate( @RequestParam("CarrierId") String carrierId,@RequestParam("agentid") String agentid,@RequestParam("password") String password) {
		AgentConfigInfo agentInfo = new AgentConfigInfo();
		agentInfo.setCarrierId(carrierId);
		agentInfo.setAgentId(agentid);
		agentInfo.setPassword(password);
		boolean isValidUser = serviceClient.isValidAgent(agentInfo);
		if(isValidUser){
			agentInfo = agentConfigInfoService.handleAgentConfigInfo(dao ,agentInfo.getAgentId(),serviceClient);
			agentInfo.setCarrierId(agentInfo.getCarrierId());
			agentInfo.setPassword(agentInfo.getPassword());
			session.setAttribute(AgencyPortalConstant.CURRENT_AGENT, agentInfo);
			session.setAttribute(AgencyPortalConstant.CLIENT, AgencyPortalConstant.CLIENT);
		}
		return String.valueOf(isValidUser);
	}
	
	@RequestMapping(value = "/payMethod", method = RequestMethod.GET)
	public ModelAndView executePayMethod(Model model,@RequestParam("id") int id) {
		try {
			AgentConfigInfo aConfig = (AgentConfigInfo) session.getAttribute(AgencyPortalConstant.CURRENT_AGENT);
			if (aConfig == null)
				return modelAndViewLogout;
			List<AgentPaymentConfig> listPaymentConfig = agentPaymentConfigService.getListPaymentConfig(dao ,(Integer)aConfig.getAgentCID());
			if (listPaymentConfig != null && !listPaymentConfig.isEmpty() ) {
				for (AgentPaymentConfig pConfig: listPaymentConfig) {
					pConfig.setAgentCID(aConfig.getAgentCID());
				    pConfig.setAgree("0");
				    dao.updateByObject(pConfig);
				}
			}
			AgentPaymentConfig paymentConfig = new AgentPaymentConfig();
			paymentConfig.setAgentCID(aConfig.getAgentCID());
			paymentConfig.setId(id);
			paymentConfig.setAgree("1");
			dao.updateByObject(paymentConfig);
			
		} catch(Exception e) {
			return modelAndViewLogout;
		}
		
		return modelAndViewAgentInfo;
	}

	@RequestMapping(value = "/agentinfo", method = RequestMethod.GET)
	public ModelAndView paymentInfo(Map<String, Object> model) {
		try {
			AgentConfigInfo agentInfo = (AgentConfigInfo) session.getAttribute(AgencyPortalConstant.CURRENT_AGENT);
			if (agentInfo == null)
				return modelAndViewLogout;
			else{
				agentForm.setAgentConfigInfo(agentInfo);
				List<AgentPaymentConfig> listPaymentConfig = agentPaymentConfigService.getListPaymentConfig(dao,agentInfo.getAgentCID());
				if (listPaymentConfig != null)
					agentForm.setListPaymentConfig(listPaymentConfig);
				model.put("message",message);
			}
		}catch(Exception e) {
			log.error(e);
			return modelAndViewLogout;
		}
		return  new ModelAndView("agentinfo", "agentForm", agentForm);
	}

	@RequestMapping(value = "/logoutAgent", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,HttpServletResponse response) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		session.setAttribute("agent", null);
		session.setAttribute(AgencyPortalConstant.CURRENT_AGENT, null);
		session.setAttribute(AgencyPortalConstant.CLIENT, null);
		session.invalidate();
		return "redirect:/client";
	}

	@RequestMapping(value = "/addBankDetail", method = RequestMethod.POST)
	public ModelAndView addBankDetail(HttpServletRequest request,@ModelAttribute("paymentConfig") AgentPaymentConfig paymentConfig)  {
		try {
			AgentConfigInfo aConfig = (AgentConfigInfo) session.getAttribute(AgencyPortalConstant.CURRENT_AGENT);
			if (aConfig == null)
				return modelAndViewLogout;

			String ccToken = request.getParameter("card");

			if (ccToken == null)
				ccToken = request.getParameter("bank");

			paymentConfig.setAgentCID(aConfig.getAgentCID());

			if (!StringUtils.isEmpty(ccToken)) {
				paymentConfig.setAgree("1");
				String customerId = customerService.createCustomer(aConfig,ccToken);
				paymentConfig.setCustomerId(customerId);
				if (request.getParameter("card") != null) {
					paymentConfig = cardService.saveCreditCardInfor(paymentConfig,ccToken);
				} else {
					if (request.getParameter("bank") != null) {
						paymentConfig = cardService.saveBankDetailsInfor(paymentConfig,ccToken);
						if (paymentConfig.getAccountNumber() == null)
							paymentConfig.setAccountNumber(request.getParameter("accountNumber"));
					}
				}
				
				if (!agentPaymentConfigService.savePaymentInfo(dao,paymentConfig))
					message = "Payment Information Already Exist." ;
				else
					message = "Payment Information added successfully.";
			}

		} catch (Exception e) {
			log.error(e);
			return modelAndViewAgentInfo;
		}

		return modelAndViewAgentInfo;

	}

	@RequestMapping(value = "/deleteBankDetail", method = RequestMethod.GET)
	public ModelAndView deleteBankDetail(@RequestParam("id") int id) {
		
		AgentConfigInfo aConfig = (AgentConfigInfo) session	.getAttribute(AgencyPortalConstant.CURRENT_AGENT);
		if (aConfig == null)
			return modelAndViewLogout;

		try {
			AgentPaymentConfig pConfig = new AgentPaymentConfig();
			pConfig.setId(id);
			dao.deleteObject(pConfig);
			message = "Payment Information deleted successfully." ;
			
		} catch (Exception e) {
			return modelAndViewAgentInfo;
		}

		return modelAndViewAgentInfo;
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ModelAndView download()  {
		
		AgentConfigInfo aConfig = (AgentConfigInfo) session	.getAttribute(AgencyPortalConstant.CURRENT_AGENT);
		
		if (aConfig == null)
			return modelAndViewLogout;
		
		try{
			message="";
			AgentPaymentConfig payment = agentPaymentConfigService.getPaymentInfo(dao,aConfig.getAgentCID(),1);
			if (payment == null || payment.getAgentCID() == 0  || aConfig.getLastDownloadDt()!= 0 || aConfig.getLastPaymentAmt() != 0 ) {
				EmailService.getInstance().sendEMail(aConfig.getEmail(), AgencyPortalConstant.EMAIL_SUBJECT_REG_AGENT, AgencyPortalConstant.EMAIL_MESSAGE_REG_AGENT);
				//ServiceClient.disableAgent(aConfig.getAgentId(),"N");
			}
			return new ModelAndView("download","","");
		
		}catch(Exception e) {
			return modelAndViewLogout;
		}
	}
}
