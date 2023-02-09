package com.naumovets.spring_secutiry.aop.aspects.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Aspect
@Slf4j
@Component
public class TimerAspect {

    @Pointcut("@within(com.naumovets.spring_secutiry.aop.aspects.annotations.Timer)")
    public void classesAnnotated() {

    }

    @Pointcut("@annotation(Timer)")
    public void methodAnnotated() {

    }

    @Pointcut("classesAnnotated() || methodAnnotated()")
    public void classesAndMethods() {

    }

    @Around("classesAndMethods()")
    public Object takeTheTime(ProceedingJoinPoint pjp) {
        try {
            //ProceedJoinPont
            String className = pjp.getTarget().getClass().getName();
            String method = pjp.getSignature().getName();

            Long start = System.currentTimeMillis();
            Object obj = pjp.proceed();
            Long finish = System.currentTimeMillis();
            Long duration = finish - start;

            log.info("Время работы метода: {}#{}, завершилось со временем (мс): {}", className, method, duration);

            return obj;
        } catch (Throwable e) {
            log.error("Метод bar() отработал с ошибкой: {}", e.getMessage());

            return null;
        }
    }

}
