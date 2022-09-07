package com.tecnara.manoamiga.aaa.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
@Component
@Aspect
public class LogAspect {

    private int pos=0;
    
    @Pointcut("execution(* com.tecnara.manoamiga.aaa.services.IGeneral.*(..))")
    public void controllerMethods() {
        System.out.println("Se ejecuta");
    }

    @Before("controllerMethods()")
    public void before(JoinPoint joinPoint) {
        
        System.out.println(" ".repeat(pos++) + "-->" + joinPoint.toShortString());
    }

    @AfterReturning(value = "controllerMethods()", returning = "data")
    public void logMethodCall(JoinPoint jp, Object data) throws Throwable {
        System.out.println(" ".repeat(pos++) + "<--" + jp.toShortString() + "->" + data);
    }

}
*/