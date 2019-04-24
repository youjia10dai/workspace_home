package com.dxz.demo.configuration5;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dxz.demo.configuration5")
public class TestConfiguration {
    public TestConfiguration() {
        System.out.println("TestConfigurationÈÝÆ÷Æô¶¯³õÊ¼»¯¡£¡£¡£");
    }
    
    @Configuration
    static class DatabaseConfig {
        @Bean
        DataSource dataSource() {
            return new DataSource();
        }
    }
}