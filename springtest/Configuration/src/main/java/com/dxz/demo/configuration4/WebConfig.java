package com.dxz.demo.configuration4;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.dxz.demo.configuration.TestConfiguration;


@Configuration
@ImportResource("classpath:config/applicationContext-configuration.xml")
@Import(TestConfiguration.class)
public class WebConfig {
}