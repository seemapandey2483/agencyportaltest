package com.teamup.agencyportal.domain;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.stripe.model.Charge;
import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.email.EmailService;
import com.teamup.agencyportal.model.AgentConfigInfo;
import com.teamup.agencyportal.model.AgentPaymentConfig;
import com.teamup.agencyportal.model.ConfigDownload;
import com.teamup.agencyportal.model.Deposit;
import com.teamup.agencyportal.model.JobActivity;
import com.teamup.agencyportal.model.JobActivityTransationDetail;
import com.teamup.agencyportal.model.MonthlyBill;
import com.teamup.agencyportal.service.AgentConfigInfoService;
import com.teamup.agencyportal.service.AgentPaymentConfigService;
import com.teamup.agencyportal.service.ConfigDownloadService;
import com.teamup.agencyportal.service.CustomerService;
import com.teamup.agencyportal.service.DepositService;
import com.teamup.agencyportal.service.JobActivityTransactionService;
import com.teamup.agencyportal.service.MonthlyBillService;

public class ProcessPayment {
	
	private Logger log = Logger.getLogger(getClass());
	
	public void saveDefaultJobActivity(CommonDAO dao,String jobId) {
		try {
			JobActivity jobActivity = new JobActivity();
			jobActivity.setJobId(jobId);
			jobActivity.setStatus(AgencyPortalConstant.STATUS_PENDING);
			jobActivity.setJobStartTime(System.currentTimeMillis());
			jobActivity.setNoOfTransactionFailed(0);
			jobActivity.setNoOfTransactionSuccess(0);

			dao.addObject(jobActivity);

		} catch (Exception e) {
			log.error("saveDefaultJobActivity =" + e);
		}
	}

	public void updateDefaultJobActivity(CommonDAO dao,int id, String jobId,int failedTransCount, int succeessTransCount,String status) {
		try {
			JobActivity jobActivity = new JobActivity();
			jobActivity.setId(id);
			jobActivity.setJobId(jobId);
			jobActivity.setStatus(status);
			jobActivity.setJobEndTime(System.currentTimeMillis());
			jobActivity.setNoOfTransactionFailed(failedTransCount);
			jobActivity.setNoOfTransactionSuccess(succeessTransCount);

			dao.updateByObject(jobActivity);
			
		} catch (Exception e) {
			log.error("updateDefaultJobActivity =" + e);
		}
	}

	public void addDeposit(CommonDAO dao,int agentCID, String status, int month,int year,String type,long amount) {
		try {
			Deposit deposit = new Deposit();
			deposit.setAgentCID(agentCID);
			deposit.setStatus(status);
			deposit.setAmount(amount);
			deposit.setCreationDt(System.currentTimeMillis());
			deposit.setMonth(month);
			deposit.setType(type);
			deposit.setYear(year);
			dao.addObject(deposit);
			
		} catch (Exception e) {
			log.error("addDeposit =" + e);
		}
	}

	public void updateAgentconfigInfo(CommonDAO dao,int agentCID,long amount) {
		try {
			AgentConfigInfo aInfo = new AgentConfigInfo();
			aInfo.setAgentCID(agentCID);
			aInfo.setLastPaymentDt(System.currentTimeMillis());
			aInfo.setLastPaymentAmt(amount);
			dao.updateByObject(aInfo);
	
		} catch (Exception e) {
			log.error("updateAgentconfigInfo =" + e);
		}
	}

	public void createTransanction(CommonDAO dao,int jacId, int agentCID,String status, String description, String lastFourDigit,String routingNumber, String chargeId,long amount) {
		try {
			JobActivityTransationDetail transationDetail = new JobActivityTransationDetail();
			transationDetail.setJactId(jacId);
			transationDetail.setAgentCId(agentCID);
			transationDetail.setTxnDt(System.currentTimeMillis());
			transationDetail.setCreationDt(System.currentTimeMillis());
			transationDetail.setAmount(amount);
			transationDetail.setLastFourDigit(lastFourDigit);
			transationDetail.setRoutingNumber(routingNumber);
			transationDetail.setStatus(status);
			transationDetail.setDescription(description);
			transationDetail.setChargeId(chargeId);

			dao.addObject(transationDetail);

		} catch (Exception e) {
			log.error("updateAgentPaymentInfo =" + e);
		}

	}

	public void saveMonthlyBill(CommonDAO dao,MonthlyBill monthlyBill) {

		try {
			monthlyBill.setCreationDt(System.currentTimeMillis());
			monthlyBill.setType("D");
			boolean flag = dao.addObject(monthlyBill);
			
			if (!flag)
				dao.updateByObject(monthlyBill);
			
	} catch (Exception e) {
			log.error("updateAgentPaymentInfo =" + e);
		}
	}

	public void updateConfigDownload(CommonDAO dao,String agentCID, long lastPaymentDt) {
		try {
			ConfigDownload aInfo = new ConfigDownload();
			aInfo.setAgentId(agentCID);
			aInfo.setLastDownload(lastPaymentDt);

			dao.updateByObject(aInfo);

		} catch (Exception e) {
			log.error("updateConfigDownload =" + e);
		}
	}

	public String processPaymentJob(int jobID , CommonDAO dao,AgentConfigInfoService agentConfigInfoService,AgentPaymentConfigService agentPaymentConfigService,
			ConfigDownloadService configDownloadService,MonthlyBillService monthlyBillService) {

		int noOfSucceessTransaction = 0;
		int noOfFailedTransaction = 0;

		try {
			log.info("********processPaymentJob***************");
			long cdate = configDownloadService.getConfigDate(dao,AgencyPortalConstant.CARRIER_ID);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(cdate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);

			List<MonthlyBill> monthlyBillList = monthlyBillService.getMonthalyReportsPayment(dao,month, year);
			
			if (monthlyBillList != null) {
				
				for (MonthlyBill monthlyBill : monthlyBillList) {

					int agentCID = monthlyBill.getAgentCID();
					long transationDt = monthlyBill.getTransationDt();
					
					if ( monthlyBill.getAmount() < AgencyPortalConstant.AMOUNT ) {
						AgentConfigInfo agentInfo = agentConfigInfoService.getAgentConfigInfoByAgentCid(dao,agentCID);

						if (agentInfo != null && agentInfo.getAgentCID() != 0) {
						
							long days = (System.currentTimeMillis() - transationDt) / AgencyPortalConstant.DAYS_IN_MILLI_SECOND;

						if ((transationDt == 0) || (transationDt != 0 && days > 30)) {
							String status = "";
							String description = "";
							String emailMassage = "";
							Charge charge = null;
							int paymentId = agentPaymentConfigService.getPaymentId(dao,agentInfo.getAgentCID(), 1);

							AgentPaymentConfig agentPaymentConfig = agentPaymentConfigService.getPaymentConfig(dao,agentInfo.getAgentCID(),paymentId);

							if (agentPaymentConfig != null && agentPaymentConfig.getAgentCID() != 0) {

								cal.setTimeInMillis(cdate);
								cal.add(Calendar.MONTH, 1);
								String customerId = agentPaymentConfig.getCustomerId();

								String accontId = agentPaymentConfig.getCardType() == null ? agentPaymentConfig.getAccontId() : null;

								charge = chargeCustomer(customerId, accontId,AgencyPortalConstant.AMOUNT);

								if (charge != null && (charge.getStatus().contains("suc") || charge.getStatus().contains("pen"))) {
						
									monthlyBill.setDueDate(cal.getTimeInMillis());
									monthlyBill.setAmount(monthlyBill.getAmount()+charge.getAmount());
									saveMonthlyBill(dao,monthlyBill);
									
									noOfSucceessTransaction = noOfSucceessTransaction + 1;
									
									updateAgentconfigInfo(dao,agentInfo	.getAgentCID(),monthlyBill.getAmount());

									if (charge.getStatus().contains("pen"))
										status = AgencyPortalConstant.STATUS_PENDING;
									else
										status = AgencyPortalConstant.STATUS_SUC;

									addDeposit(dao,agentInfo.getAgentCID(), status, month,year,"D",charge.getAmount());

									emailMassage = AgencyPortalConstant.EMAIL_MESSAGE_SUCCESS;
									
									status = charge.getStatus();
									
									description = charge.getDescription();
								}else{
									///////////////failed transaction
								}
							}

							if (charge == null || agentPaymentConfig == null || agentPaymentConfig.getAgentCID() == 0 || charge.getStatus().contains("fail")) {
								description = AgencyPortalConstant.PAYMENT_NOT_CONFIGURED_MESSAGE;
								status = AgencyPortalConstant.PAYMENT_FAILED;
								noOfFailedTransaction = noOfFailedTransaction + 1;
								emailMassage = AgencyPortalConstant.EMAIL_MESSAGE_FAIL;
							}

							createTransanction(dao,jobID,agentInfo.getAgentCID(), status, description,agentPaymentConfig.getLastFourDigit(),agentPaymentConfig.getRoutingNumber(),charge.getId(),monthlyBill.getAmount());
							//updateConfigDownload(dao,AgencyPortalConstant.CARRIER_ID,cal.getTimeInMillis());


							EmailService.getInstance().sendEMail(agentInfo.getEmail(),status + " " + description, emailMassage);

						}
					  }
					}
				}
			}
		} catch (Exception e) {
			log.error("processPayment =" + e);
		}

		return noOfSucceessTransaction + "," + noOfFailedTransaction;
	}

	public Charge chargeCustomer(String customerId, String accontId,long amount) {
		 CustomerService customerService = new CustomerService();
		return customerService.chargeCustomer(customerId,amount, accontId);

	}

	public void updatePendingTansaction(CommonDAO dao,JobActivityTransactionService jobActivityTransactionService) {
		try {
			List<JobActivityTransationDetail> listJDetail = jobActivityTransactionService.getListTransationDetail(dao,"status", "pending");
			log.info(listJDetail.size());
			for (JobActivityTransationDetail JDetail : listJDetail) {
				log.info(JDetail.getChargeId());
				 CustomerService customerService = new CustomerService();
				Charge charge = customerService.retriveCharge(JDetail.getChargeId());
				if (charge != null && charge.getStatus().contains("suc")) {
					JDetail.setStatus(charge.getStatus());
					JDetail.setDescription(charge.getDescription());
					JDetail.setChargeId(charge.getId());
					dao.updateByObject(JDetail);
				}
			}
		} catch (Exception e) {
			log.error("updatePendingTansaction =" + e);
		}
	}

	@SuppressWarnings("rawtypes")
	public String processMonthlyBillJob(CommonDAO dao,AgentConfigInfoService agentConfigInfoService,ConfigDownloadService configDownloadService,DepositService depositService,MonthlyBillService monthlyBillService,ServiceClient serviceClient) {

		int noOfSucceessTransaction = 0;
		int noOfFailedTransaction = 0;
		try {
			long cdate = configDownloadService.getConfigDate(dao,AgencyPortalConstant.CARRIER_ID);
			
			if (cdate != 0 ) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(cdate);
				List<MonthlyBill> list =  serviceClient.listAgentsMonthlyReports(cdate);
				if (list != null) {
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH);
					monthlyBillService.saveMonthlyReportsBILL(dao,list,agentConfigInfoService);
					log.info(dao+""+month+""+year);
					List depositList = monthlyBillService.getMonthalyReportsForDeposit(AgencyPortalConstant.commonDao,month, year);
					if (depositList != null)
						depositService.saveDepositList(dao,depositList,month,year);
			  }
			}
		} catch (Exception e) {
			log.error("processMonthlyBill =" + e);
		}

		return noOfSucceessTransaction + "," + noOfFailedTransaction;

	}

	
}
