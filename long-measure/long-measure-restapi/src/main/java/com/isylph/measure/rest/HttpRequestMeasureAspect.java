package com.isylph.measure.rest;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class HttpRequestMeasureAspect {

    //捕获controller层参数列表不为空的接口方法
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void httpRequestBase(){}

    //@Before("httpRequestBase()")
    public void deBefore(JoinPoint joinPoint) {
        /*Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg == null) {
                throw new ReturnException(BaseErrorConsts.RET_BAD_PARAM);
            }
        }*/
    }


    //环绕通知，捕获dubbo rpc接口中的所有返回类型为RetData的方法
    //@Around("execution(com.isylph.lb.basis.vo.RetData com.isylph.lb.console.api.service.*ServiceI.*(..))")
    @Around("httpRequestBase()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Object target = pjp.getTarget();//目标对象
        Signature methodSignature = pjp.getSignature();//目标对象方法签名

        //获取该方法接口上的参数注解数组
        String methodName = methodSignature.getName();


        //参数校验通过，执行方法
        Object result;
        try {
            Long start = System.currentTimeMillis();
            result = pjp.proceed();
            Long end = System.currentTimeMillis();
            log.warn("++++interface time consuming {} - {}", methodName, end - start);
            return result;
        } catch (Exception ex) {

            throw ex;
        }
    }

}
