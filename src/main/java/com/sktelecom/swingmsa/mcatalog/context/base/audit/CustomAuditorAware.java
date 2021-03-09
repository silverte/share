package com.sktelecom.swingmsa.mcatalog.context.base.audit;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.sktelecom.swing.io.vo.HeaderVO;
@Component
public class CustomAuditorAware implements AuditorAware<String> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public Optional<String> getCurrentAuditor() {
		HeaderVO header = (HeaderVO) request.getSession().getAttribute("header");
		return Optional.of(header.getUserId());
	}

}
