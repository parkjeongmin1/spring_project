package com.shopmax.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration //설정파일을 만들기 위한 애노테이션
@EnableJpaAuditing //JPA의 auditing 기능을 활성화
public class AuditConfig {
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
