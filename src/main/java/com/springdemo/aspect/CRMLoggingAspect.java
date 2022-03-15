package com.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    private Logger logger = Logger.getLogger(CRMLoggingAspect.class.getName());

    @Pointcut("execution(* com.springdemo.controller.*.*(..))")
    private void forControllerPackage(){};

    @Pointcut("execution(* com.springdemo.service.*.*(..))")
    private void forServicePackage(){};

    @Pointcut("execution(* com.springdemo.dao.*.*(..))")
    private void forDaoPackage(){};

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){};

    @Before("forAppFlow()")
    public void  before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().toShortString();
        logger.info("\n\n======>>> Executing @Before on method: " + method);

        Object[] args = joinPoint.getArgs();

        for(Object arg: args){
            logger.info("\n===> argument: "+ arg);
        }
    }

    @AfterReturning(pointcut="forAppFlow()", returning="result")
    public void  afterReturning(JoinPoint joinPoint, Object result){
        String method = joinPoint.getSignature().toShortString();
        logger.info("\n\n======>>> Executing @AfterReturning on method: " + method);
        logger.info("\n====> result: "+ result);
    }


}
