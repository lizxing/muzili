package com.lizxing.muzili.common.aspect;

import com.google.gson.Gson;
import com.lizxing.muzili.common.util.HttpContextUtils;
import com.lizxing.muzili.common.util.IPUtils;
import com.lizxing.muzili.module.sys.entity.SysLog;
import com.lizxing.muzili.module.sys.entity.SysUser;
import com.lizxing.muzili.module.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lizxing
 * @date 2021/8/18
 */
@Aspect
@Component
public class SysLogAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.lizxing.muzili.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        // 保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        com.lizxing.muzili.common.annotation.SysLog syslogAnnotation = method.getAnnotation(com.lizxing.muzili.common.annotation.SysLog.class);
        if(syslogAnnotation != null){
            //注解上的描述
            sysLog.setOperation(syslogAnnotation.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = new Gson().toJson(args);
            sysLog.setParams(params);
        }catch (Exception e){
            logger.info("获取请求参数失败");
        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = ((SysUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
        sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreateDate(LocalDateTime.now());
        //保存系统日志
        sysLogService.save(sysLog);
    }
}
