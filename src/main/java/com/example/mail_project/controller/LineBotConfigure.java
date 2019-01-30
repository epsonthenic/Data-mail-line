package com.example.mail_project.controller;

import com.example.mail_project.MailProjectApplication;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class LineBotConfigure implements WebMvcConfigurer {

    @Override // ดาวโหลด รับส่งรูป
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String downloadedContentUri = MailProjectApplication.downloadedContentDir.toUri().toASCIIString();
        log.info("downloaded Uri: {}", downloadedContentUri);
        registry.addResourceHandler("/downloaded/**")
                .addResourceLocations(downloadedContentUri);
    }
}