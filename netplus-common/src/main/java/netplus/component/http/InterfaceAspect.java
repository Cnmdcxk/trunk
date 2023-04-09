package netplus.component.http;

import com.google.gson.Gson;
import netplus.component.entity.aspect.InterfaceAnnotation;
import netplus.component.entity.aspect.Tbmqq460Mapper;
import netplus.component.entity.aspect.Tbmqq460WithBLOBs;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class InterfaceAspect {

    private static Log logger = LogFactory.getLog(InterfaceAspect.class);


    @Autowired(required = false)
    Tbmqq460Mapper tbmqq460Mapper;


    @Pointcut("@annotation(netplus.component.entity.aspect.InterfaceAnnotation)")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){

        Object arg = joinPoint.getArgs()[0];

        InterfaceAnnotation annotation = getAnnotation(joinPoint);
        BaseRequest baseRequest = (BaseRequest) arg;
        if (!"Y".equals(baseRequest.getIsRestart())) {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


            Tbmqq460WithBLOBs tbmqq460WithBLOBs = new Tbmqq460WithBLOBs();
            tbmqq460WithBLOBs.setReqid(baseRequest.getReqId());
            tbmqq460WithBLOBs.setCallee(annotation.callee());
            tbmqq460WithBLOBs.setCaller(annotation.caller());
            tbmqq460WithBLOBs.setRequrl(String.valueOf(request.getRequestURL()));

            tbmqq460WithBLOBs.setReqtime(baseRequest.getReqTime());
            tbmqq460WithBLOBs.setReqname(joinPoint.getSignature().getName());
            tbmqq460WithBLOBs.setReqdata(new Gson().toJson(arg));

            tbmqq460WithBLOBs.setStatus("INIT");
            tbmqq460WithBLOBs.setTimes(Short.parseShort("0"));
            tbmqq460Mapper.insertSelective(tbmqq460WithBLOBs);
        }
    };

    @AfterReturning(pointcut = "pointcut()", returning = "rvt")
    public Object afterReturning(JoinPoint joinPoint, Object rvt){

        Object arg = joinPoint.getArgs()[0];
        InterfaceAnnotation annotation = getAnnotation(joinPoint);
        BaseRequest baseRequest = (BaseRequest) arg;


        if (!"Y".equals(baseRequest.getIsRestart())) {
            Tbmqq460WithBLOBs tbmqq460WithBLOBs = new Tbmqq460WithBLOBs();
            tbmqq460WithBLOBs.setReqid(baseRequest.getReqId());
            tbmqq460WithBLOBs.setCallee(annotation.callee());
            tbmqq460WithBLOBs.setCaller(annotation.caller());

            tbmqq460WithBLOBs.setResptime(String.valueOf(new Date().getTime()));
            tbmqq460WithBLOBs.setRespdata(new Gson().toJson(rvt));

            BaseResponse baseResponse = (BaseResponse) rvt;

            if (baseResponse.getStatus().equals("1")) {
                tbmqq460WithBLOBs.setStatus("OK");

            }else{
                tbmqq460WithBLOBs.setStatus("FAIL");

            }

            tbmqq460Mapper.updateByPrimaryKeySelective(tbmqq460WithBLOBs);
        }

        return rvt;
    };

    @AfterThrowing(pointcut = "pointcut()", throwing = "err")
    public void afterThrowing(JoinPoint joinPoint, Throwable err){
        Object arg = joinPoint.getArgs()[0];
        InterfaceAnnotation annotation = getAnnotation(joinPoint);
        BaseRequest baseRequest = (BaseRequest) arg;


        if (!"Y".equals(baseRequest.getIsRestart())) {

            Tbmqq460WithBLOBs tbmqq460WithBLOBs = new Tbmqq460WithBLOBs();
            tbmqq460WithBLOBs.setReqid(baseRequest.getReqId());
            tbmqq460WithBLOBs.setCallee(annotation.callee());
            tbmqq460WithBLOBs.setCaller(annotation.caller());

            tbmqq460WithBLOBs.setResptime(String.valueOf(new Date().getTime()));
            tbmqq460WithBLOBs.setRespdata(err.getMessage());
            tbmqq460WithBLOBs.setStatus("FAIL");

            tbmqq460Mapper.updateByPrimaryKeySelective(tbmqq460WithBLOBs);
        }
    };

    @After("pointcut()")
    public void after(JoinPoint joinPoint){
        //什么也不做
    };

    private InterfaceAnnotation getAnnotation (JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        InterfaceAnnotation annotation = method.getAnnotation(InterfaceAnnotation.class);
        return annotation;
    }
}
