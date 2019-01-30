package com.example.mail_project;

import com.example.mail_project.entity.CustomerLog;
import com.example.mail_project.entity.LineDataImg;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RepositoryConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(CustomerLog.class);
        config.exposeIdsFor(LineDataImg.class);
    }
}
