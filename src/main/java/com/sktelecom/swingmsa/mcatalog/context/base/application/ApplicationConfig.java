package com.sktelecom.swingmsa.mcatalog.context.base.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ApplicationConfig {

	@Value("${spring.application.name}")
	private String appName;
	
	//swagger 사용
	@Value("${info.app.title}")
	private String title;

	@Value("${info.app.desciprion}")
	private String desciprion;
}
