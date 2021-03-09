package com.sktelecom.swingmsa.mcatalog.context.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sktelecom.swing.io.vo.HeaderVO;

@Component
public class LoggerUtil {
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("${spring.application.name}")
	private String appName;
	
	private String defaultMsg() {
		HeaderVO header = (HeaderVO) request.getSession().getAttribute("header");
		String url = header.getTrx_code();
		
		return String.join("|", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis())), appName, url);
	}

	public String entryLog() {
		return defaultMsg();
	}
	
	public String errorLog(Exception e) {
		return String.join("|", defaultMsg(), e.getLocalizedMessage());
	}
}
