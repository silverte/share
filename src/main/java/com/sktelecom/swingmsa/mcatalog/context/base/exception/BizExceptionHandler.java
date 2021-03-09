package com.sktelecom.swingmsa.mcatalog.context.base.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sktelecom.swingmsa.mcatalog.context.util.LoggerUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations=Controller.class)
public class BizExceptionHandler {
	
	@Autowired
	private LoggerUtil loggerUtil;

	@ExceptionHandler(BizException.class)	
	public Map<String, Object> bizExceptionErrorHandler(BizException e) throws Exception {
		
	    HashMap<String, Object> result = new HashMap<>();
	    HashMap<String, String> message = new HashMap<>();
	    
		log.error(loggerUtil.errorLog(e.getException()), e.getException());
		
		message.put("code", "99");
		message.put("message", "오류가 발생하였습니다.");
		 
        result.put("error", message);
        return result;

	}

	@ExceptionHandler(value = Exception.class)
	public Map<String, Object> defaultErrorHandler(Exception e) throws Exception {
		
		HashMap<String, Object> result = new HashMap<>();
		HashMap<String, String> message = new HashMap<>();
		
		log.error(loggerUtil.errorLog(e), e);
		
		message.put("code", "99");
		message.put("message", "오류가 발생하였습니다.");
		 
        result.put("error", message);        
        return result;
	}
}
