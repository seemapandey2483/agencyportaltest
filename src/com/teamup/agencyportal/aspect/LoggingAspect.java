package com.teamup.agencyportal.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {

	static Logger log = Logger.getLogger(LoggingAspect.class);

	@AfterReturning(pointcut = "within(com.teamup.agencyportal..*)", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("Method Name : " + joinPoint.getSignature().getName() + " result : " + result);
	}

	@AfterThrowing(pointcut = "within(com.teamup.agencyportal..*))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		log.error(joinPoint.getSignature().getName() + " Exception : " + error);
	}

}