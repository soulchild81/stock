package com.pay.stock.common.aop;

import com.pay.stock.common.Exception.CommonException;
import com.pay.stock.common.ExceptionMsg;
import com.pay.stock.common.annotation.StockApi;
import com.pay.stock.common.result.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Slf4j
@Aspect
@Component
public class ControllerAop {
    @Around(value="@annotation(stockApi)")
    public ServiceResult<Object> process(ProceedingJoinPoint joinPoint, StockApi stockApi) throws Throwable {
        ServiceResult<Object> searchResult = new ServiceResult<>();
        try{
            Object result = joinPoint.proceed();
            searchResult = (ServiceResult<Object>)result;

            this.setResult(searchResult , ExceptionMsg.RESULT_CODE.SUCCESS.getMsg(), ExceptionMsg.RESULT_CODE.SUCCESS.name() , Integer.toString(ExceptionMsg.RESULT_CODE.SUCCESS.getCode()));
            searchResult.setData(searchResult.getResult());
            return searchResult;
        }catch(Exception e){
            e.printStackTrace();
            if(e instanceof CommonException) {
                CommonException be = (CommonException) e;
                this.setResult(searchResult , be.getResult_code().getMsg(), be.getResult_code().name() , Integer.toString(be.getResult_code().getCode()));
            } else {
                this.setResult(searchResult , ExceptionMsg.RESULT_CODE.UNKNOWN_ERROR.getMsg(), ExceptionMsg.RESULT_CODE.UNKNOWN_ERROR.name(), Integer.toString(ExceptionMsg.RESULT_CODE.UNKNOWN_ERROR.getCode()));
            }

            return searchResult;
        }
    }

    public void setResult(ServiceResult<Object> searchResult , String msg , String type , String code){
        searchResult.setMessage(msg);
        searchResult.setMessage_type(type);
        searchResult.setReturn_code(code);
    }
}