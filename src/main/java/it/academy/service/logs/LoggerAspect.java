package it.academy.service.logs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static it.academy.service.utils.Constants.*;

@Slf4j
@Component
@Aspect
public class LoggerAspect {

    @Pointcut("execution(* it.academy.service.services.impl..*(..))")
    public void serviceMethodExec() { }

    @Before("serviceMethodExec()")
    public void beforeCallAtMethod1(JoinPoint jp) {
        String args = getMethodData(jp);
        log.info(METHOD_STARTED + jp.toString() + ", args=[" + args + "]");
    }

    @After("serviceMethodExec()")
    public void afterCallAt(JoinPoint jp) {
        log.info(METHOD_FINISHED + jp.toString());
    }

    @AfterThrowing(pointcut = "serviceMethodExec()", throwing = "exception")
    public void afterThrowing(JoinPoint jp, Throwable exception) {
        String args = getMethodData(jp);
        log.error(EXCEPTION_IN_METHOD + jp.toString() + ", args=[" + args + "] " + exception.getClass() + " " + exception.getMessage());
    }

    private String getMethodData(JoinPoint jp) {
        return Arrays.stream(jp.getArgs())
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

}
