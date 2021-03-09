package com.sktelecom.swingmsa.mcatalog.context.base.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "customAuditorAware")
@Configuration
public class JpaAuditingConfig {
}
