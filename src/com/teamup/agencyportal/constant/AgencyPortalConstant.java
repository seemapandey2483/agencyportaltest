package com.teamup.agencyportal.constant;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.teamup.agencyportal.dao.CommonDAO;

public class AgencyPortalConstant {
	static Logger log = Logger.getLogger(AgencyPortalConstant.class);
	
	public static final String MONTHLYBILL_REPORT_CLASS_NAME = "MonthlyBillJob";
	
	public static final String MONTHLYBILL_REPORT_JOB_ID = "BJOB0001";
	
	public static final String DOWNLOAD_REPORT_JOB_NAME = "MONTHLYBILL_REPORT";
	
	public static final String MONTHLYBILL_REPORT_JOB_GROUP_NAME = "MONTHLYBILL_GROUP_REPORT";
	
	public static final String MONTHLYBILL_REPORT_JOB_DESC = "Scheduled Task to Run Download Report";
	
	public static final String MONTHLYBILL_REPORT_TRIG_NAME = "MONTHLYBILL_REPORT_TRIG";

	public static final String PAYMENT_REPORT_JOB_ID = "PJOB0002";
	
	public static final String PAYMENT_REPORT_JOB_NAME = "PAYMENT_REPORT";
	
	public static final String PAYMENT_REPORT_JOB_GROUP_NAME = "PAYMENT_GROUP_REPORT";
	
	public static final String PAYMENT_REPORT_JOB_DESC = "Scheduled Task to Run PAYMENT Report";
	
	public static final String PAYMENT_REPORT_TRIG_NAME = "PAYMENT_REPORT_TRIG";
	
	public static final String PAYMENT_REPORT_CLASS_NAME = "PaymentJob";
	
	static Properties prop = new Properties();
	
	static {
			try {
				prop.load(AgencyPortalConstant.class.getClassLoader().getResourceAsStream("config.properties"));
			} catch (IOException e) {
				log.error(e);
			}
	}

	public static final String REST_SERVICE_URI = prop.getProperty("REST_SERVICE_URI");
	
	public static final String AUTH_SERVER_URI = REST_SERVICE_URI+"/oauth/token";
	
	public static final String REST_SERVICE_USERNAME = prop.getProperty("REST_SERVICE_USERNAME");
	
	public static final String REST_SERVICE_PASSWORD = prop.getProperty("REST_SERVICE_PASSWORD");
	   
	public static final int AMOUNT =  Integer.parseInt(prop.getProperty("AMOUNT"));
	
	public static final String MONTHLYBILL_REPORT_TRIG_STR = prop.getProperty("MONTHLYBILL_REPORT_TRIG_STR");
	
	public static final String PAYMENT_REPORT_TRIG_STR = prop.getProperty("PAYMENT_REPORT_TRIG_STR");
	
	public static final String EMAIL_HOST = prop.getProperty("EMAIL_HOST");
	
	public static final String EMAIL_FROM = prop.getProperty("EMAIL_FROM");
	
	public static final String EMAIL_TO = prop.getProperty("EMAIL_TO");
	
	public static final String EMAIL_USERNAME = prop.getProperty("EMAIL_USERNAME");
	
	public static final String EMAIL_PASSWORD = prop.getProperty("EMAIL_PASSWORD");
	
	public static final String EMAIL_SUBJECT = prop.getProperty("EMAIL_SUBJECT");
	
	public static final String EMAIL_MESSAGE_SUCCESS = prop.getProperty("EMAIL_MESSAGE_SUCCESS");
	
	public static final String EMAIL_MESSAGE_FAIL = prop.getProperty("EMAIL_MESSAGE_FAIL");

	public static final String EMAIL_SUBJECT_REG_AGENT = prop.getProperty("EMAIL_SUBJECT_REG_AGENT");
	
	public static final String EMAIL_MESSAGE_REG_AGENT = prop.getProperty("EMAIL_MESSAGE_REG_AGENT");

	public static final String PAYMENT_NOT_CONFIGURED_MESSAGE =  prop.getProperty("PAYMENT_NOT_CONFIGURED_MESSAGE"); 
	
	public static final String PAYMENT_FAILED = "Failed";

	public static final String STATUS_SUC = "C";
	
	public static final String STATUS_PENDING = "P";

	
	public static final long DAYS_IN_MILLI_SECOND = (24 * 60 * 60 * 1000);
	
	public static final String STRIPES_API_SEC_KEY =  prop.getProperty("STRIPES_API_SEC_KEY"); 

	public static final String STRIPES_API_PUBLIC_KEY =  prop.getProperty("STRIPES_API_PUBLIC_KEY"); 
	
	public static CommonDAO commonDao =  null; 
	
	public static final String ADMIN_USER =  prop.getProperty("ADMIN_USER"); 
	
	public static final String ADMIN_PASSWORD =  prop.getProperty("ADMIN_PASSWORD"); 

	public static final String CARRIER_ID = prop.getProperty("CARRIERID"); 
	
	public static final long CARRIER_ID_LAST_DOWNLOAD_DATE = Long.parseLong(prop.getProperty("CARRIER_ID_LAST_DOWNLOAD_DATE"));

	public static final String CLIENT ="client";
	
	public static final String ADMIN ="admin";
	
	public static final String RESULT = "Result";
	
	public static final String RECORDS = "Records" ;
	
	public static final String TOTAL_RECORD_COUNT = "TotalRecordCount";
	
	public static final String START_INDEX = "jtStartIndex";
	
	public static final String PAGE_SIZE = "jtPageSize" ;
	
	public static final String OK = "OK" ;
	
	public static final String CURRENT_AGENT = "currentAgent" ;
	
	
}
