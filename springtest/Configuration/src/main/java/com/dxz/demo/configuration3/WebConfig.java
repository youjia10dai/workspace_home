package com.dxz.demo.configuration3;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:config/applicationContext-configuration.xml")
public class WebConfig {
}