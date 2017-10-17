package com.teamup.agencyportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.domain.ServiceClient;
import com.teamup.agencyportal.model.AgentConfigInfo;
import com.teamup.agencyportal.model.JobActivity;
import com.teamup.agencyportal.model.JobActivityTransationDetail;
import com.teamup.agencyportal.model.JobsDetail;
import com.teamup.agencyportal.model.MonthlyBill;
import com.teamup.agencyportal.model.UserConfig;
import com.teamup.agencyportal.schedul.JobSchedul;
import com.teamup.agencyportal.service.AgentConfigInfoService;
import com.teamup.agencyportal.service.AgentPaymentConfigService;
import com.teamup.agencyportal.service.ConfigDownloadService;
import com.teamup.agencyportal.service.DepositService;
import com.teamup.agencyportal.service.JobActivityService;
import com.teamup.agencyportal.service.JobActivityTransactionService;
import com.teamup.agencyportal.service.JobsDetailService;
import com.teamup.agencyportal.service.MonthlyBillService;
import com.teamup.agencyportal.service.UserService;

@Controller
@RequestMapping(value = "/")
public class AdminController {
	private Logger log = Logger.getLogger(getClass());
	
	ModelAndView modelAndViewLogout = new ModelAndView("redirect:logoutAdmin");
	@Autowired
	CommonDAO dao;
	@Autowired
	AgentConfigInfoService agentConfigInfoService;
	@Autowired
	UserService userService;
	@Autowired
	MonthlyBillService monthlyBillService;
	@Autowired
	JobsDetailService jobsDetailService;
	@Autowired
	JobActivityTransactionService jobActivityTransactionService;
	@Autowired
	JobActivityService jobActivityService;
	@Autowired
	AgentPaymentConfigService agentPaymentConfigService;
	@Autowired
	ConfigDownloadService configDownloadService;
	@Autowired
	DepositService depositService;
	@Autowired
	ServiceClient serviceClient;
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response, HttpSession session, UserConfig user) {
		session.setAttribute(AgencyPortalConstant.ADMIN, null);
		session.setAttribute(AgencyPortalConstant.CLIENT, null);
		ModelAndView model = new ModelAndView(AgencyPortalConstant.ADMIN);
		model.addObject("user", user);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = { "/", "/validateAdmin**" }, method = RequestMethod.GET)
	public String validate( @RequestParam("userName") String userName,@RequestParam("password") String password) {
		UserConfig user = new UserConfig();
		
		user.setUserName(userName);
		user.setPassword(password);
		boolean isValidUser = userService.validateUser(dao,user.getUserName(), user.getPassword());
		if(isValidUser){
			session.setAttribute(AgencyPortalConstant.ADMIN, AgencyPortalConstant.ADMIN);
		}
		return String.valueOf(isValidUser);
	}
	
	@RequestMapping(value = "/updateAgentStatus", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> disableAgentStatus(String agentId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		try {
			serviceClient.disableAgent(agentId, "N");
			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
		} catch (Exception e) {log.error(e); }
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/listagents", method = RequestMethod.GET)
	public ModelAndView listAgents(Model model, HttpServletResponse response,HttpSession session) {

		if (session.getAttribute(AgencyPortalConstant.ADMIN) == null)
			return modelAndViewLogout;

		return new ModelAndView("listagents", "", "");
	}

	@RequestMapping(value = "/listagents", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> listAgents(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		try {
			int startPageIndex = Integer.parseInt(request.getParameter(AgencyPortalConstant.START_INDEX));
			int recordsPerPage = Integer.parseInt(request.getParameter(AgencyPortalConstant.PAGE_SIZE));
			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
			List<AgentConfigInfo> list = agentConfigInfoService.getAllAgentConfigInfoList(dao,startPageIndex, recordsPerPage);
			resultMap.put(AgencyPortalConstant.RECORDS, list);
			resultMap.put(AgencyPortalConstant.TOTAL_RECORD_COUNT,agentConfigInfoService.getAgentConfigInfoCount(dao));
		} catch (Exception e) {
			log.error(e);
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMonthlyReport", method = RequestMethod.GET)
	public ModelAndView monthlyBill(Model model, HttpServletResponse response,HttpSession session) {

		if (session.getAttribute(AgencyPortalConstant.ADMIN) == null)
			return modelAndViewLogout;

		return new ModelAndView("report", "", "");
	}

	@RequestMapping(value = "/getMonthlyReport", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> monthlyBill(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		try {

			int startIndex = Integer.parseInt(request.getParameter(AgencyPortalConstant.START_INDEX));
			int totalRecords = Integer.parseInt(request.getParameter(AgencyPortalConstant.PAGE_SIZE));
			resultMap.put(AgencyPortalConstant.RESULT,AgencyPortalConstant.OK);
			int month = 0;
			if (request.getParameter("month") != null)
				month = Integer.parseInt(request.getParameter("month"));
			int year = 0;
			if (request.getParameter("year") != null)
				year = Integer.parseInt(request.getParameter("year"));
			resultMap.put(AgencyPortalConstant.TOTAL_RECORD_COUNT,monthlyBillService.getMonthlyBillCount(dao,month, year));
			List<MonthlyBill> list = monthlyBillService.getMonthlyReportsList(dao,month, year,startIndex, totalRecords);
			resultMap.put(AgencyPortalConstant.RECORDS, list);
		} catch (Exception e) {
			log.error(e);
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/GetJobsDetail", method = RequestMethod.GET)
	public ModelAndView getJobsDetail(Model model,HttpServletResponse response, HttpSession session) {

		if (session.getAttribute(AgencyPortalConstant.ADMIN) == null)
			return modelAndViewLogout;

		return new ModelAndView("jobsDetail", "", "");
	}

	@RequestMapping(value = "/GetJobsDetail", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getJobsDetail() {
		
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
			List<JobsDetail> list = jobsDetailService.getJobsDetailsList(dao);
			resultMap.put(AgencyPortalConstant.RECORDS, list);
		} catch (Exception e) {log.error(e);}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateJobsDetail", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> updateJobsDetail(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			JobsDetail jobsDetail = new JobsDetail();
			jobsDetail.setJobId(request.getParameter("jobId"));
			jobsDetail.setJobTrigStr(request.getParameter("jobTrigStr"));
			jobsDetail.setActive(request.getParameter("active"));
			jobsDetail.setStartFlag("false");
			dao.updateByObject(jobsDetail);

			JobsDetail jd = jobsDetailService.getJobsDetails(dao,jobsDetail.getJobId());
			jd.setStartFlag(jobsDetail.getStartFlag());
			if (jd.getActive() != null && jd.getActive().equalsIgnoreCase("Y"))
				JobSchedul.configJobSchediler(jd,dao,agentConfigInfoService,agentPaymentConfigService,configDownloadService,depositService,jobActivityService,monthlyBillService,serviceClient);

			List<JobsDetail> list = jobsDetailService.getJobsDetailsList(dao);

			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
			resultMap.put(AgencyPortalConstant.RECORDS, list);
			
		} catch (Exception e) {log.error(e); }
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostConstruct
	public void init() {
		jobsDetailService.saveDefaultJobs(dao,jobsDetailService,agentConfigInfoService,agentPaymentConfigService,configDownloadService,depositService,jobActivityService,monthlyBillService,serviceClient);
		userService.addDefaultUser(dao);
		configDownloadService.addConfigDate(dao);
		
		AgencyPortalConstant.commonDao = dao;
	}

	@RequestMapping(value = "/deleteJobsDetail", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> deleteJobsDetail(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<>();
		try {
		
			String jobid = request.getParameter("jobId");
			JobsDetail jobsDetail = jobsDetailService.getJobsDetails(dao,jobid);
			jobsDetail.setActive("N");

			dao.updateByObject(jobsDetail);
			JobSchedul.deleteJob(jobsDetail);

			List<JobsDetail> list = jobsDetailService.getJobsDetailsList(dao);

			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
			resultMap.put(AgencyPortalConstant.RECORDS, list);
		
		} catch (Exception e) {log.error(e);}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/startNow", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> startNow(HttpServletRequest request, HttpServletResponse response,@RequestParam("jobId") String jobId) {
		
		Map<String, Object> resultMap = new HashMap<>();
		try {
			JobsDetail jobsDetail = jobsDetailService.getJobsDetails(dao,jobId);

			jobsDetail.setStartFlag("true");

			if (jobsDetail.getActive().equalsIgnoreCase("Y"))
				JobSchedul.configJobSchediler(jobsDetail,dao,agentConfigInfoService,agentPaymentConfigService,configDownloadService,depositService,jobActivityService,monthlyBillService,serviceClient);

			List<JobsDetail> list = jobsDetailService.getJobsDetailsList(dao);

			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);

			resultMap.put(AgencyPortalConstant.RECORDS, list);
			
		} catch (Exception e) {log.error(e);}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/getJobActivity", method = RequestMethod.GET)
	public ModelAndView getJobActivity(Model model,HttpServletResponse response, HttpSession session) {

		if (session.getAttribute("admin") == null)
			return modelAndViewLogout;

		return new ModelAndView("jobactivity", "", "");
	}

	@RequestMapping(value = "/getJobActivity", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getJobActivity(@RequestParam("jobId") String jobId, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
			int startPageIndex = Integer.parseInt(request.getParameter(AgencyPortalConstant.START_INDEX));
			int recordsPerPage = Integer.parseInt(request.getParameter(AgencyPortalConstant.PAGE_SIZE));

			List<JobActivity> list = jobActivityService.getJobActivityList(dao,jobId, startPageIndex, recordsPerPage);
			
			resultMap.put(AgencyPortalConstant.TOTAL_RECORD_COUNT,jobActivityService.getJobActivityCount(dao,jobId));
			resultMap.put(AgencyPortalConstant.RECORDS, list);
			
		} catch (Exception e) {log.error(e);}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTransanctionDetails", method = RequestMethod.GET)
	public ModelAndView getTransanctionDetails(Model model,HttpServletResponse response, HttpSession session) {

		if (session.getAttribute(AgencyPortalConstant.ADMIN) == null)
			return modelAndViewLogout;

		return new ModelAndView("jobactivity", "", "");
	}

	@RequestMapping(value = "/getTransanctionDetails", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, headers = "Accept=application/json")
	public ResponseEntity<Map<String, Object>> getTransanctionDetails(@RequestParam("jobId") int jobId, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		try {
			int startPageIndex = Integer.parseInt(request.getParameter(AgencyPortalConstant.START_INDEX));
			int recordsPerPage = Integer.parseInt(request.getParameter(AgencyPortalConstant.PAGE_SIZE));

			resultMap.put(AgencyPortalConstant.RESULT, AgencyPortalConstant.OK);
			List<JobActivityTransationDetail> list = jobActivityTransactionService.getJobActivityTransactionList(dao,jobId, startPageIndex,recordsPerPage);
			resultMap.put(AgencyPortalConstant.TOTAL_RECORD_COUNT, jobActivityTransactionService.getJobActivityTransDetailsCount(dao,jobId));
			resultMap.put(AgencyPortalConstant.RECORDS, list);

		} catch (Exception e) {log.error(e);}
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/logoutAdmin", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) 
				new SecurityContextLogoutHandler().logout(request, response,auth);
			
			session.setAttribute(AgencyPortalConstant.ADMIN, null);
			session.setAttribute("agent", null);
			session.setAttribute("currentAgent", null);
			session.setAttribute(AgencyPortalConstant.CLIENT, null);
			session.invalidate();
		} catch (Exception e) {
			log.error(e);
		}
		return "redirect:/admin";
	}

}
