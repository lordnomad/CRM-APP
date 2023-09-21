package com.heidary.aop;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//pointcuts
	@Pointcut("execution(* com.heidary.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.heidary.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.heidary.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	
	//before
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		//display the method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @Before: calling method" + theMethod);
		
		//display the arguments to the method
		
		//get the arguments
		Object[] args = theJoinPoint.getArgs();
		//loop thru and display arguments
		for(Object tempArg : args) {
			myLogger.info("====>> arguments: " +tempArg);
		}
	}
	
	//after
	@AfterReturning(pointcut="forAppFlow()" , returning="theResult")
	public void afterReturning(JoinPoint theJoinPoint,Object theResult) {
		
		//display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>> in @AfterReturning: from method" + theMethod);
		//display data returned
		myLogger.info("====>> result: " + theResult);
	}





}
