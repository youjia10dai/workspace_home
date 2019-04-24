package com.atguigu.gmall.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.atguigu.gmall.service.UserService;

@Configuration
public class MyDubboConfig {

	@Bean
	public ApplicationConfig configApplicationConfig() {
		ApplicationConfig config = new ApplicationConfig();
		config.setName("boot-user-service-provider");
		return config;
	}
	
	//<dubbo:registry protocol="zookeeper" address="127.0.0.1:2181"></dubbo:registry>
	@Bean
	public RegistryConfig congifRegistryConfig() {
		RegistryConfig config = new RegistryConfig();
		config.setProtocol("zookeeper");
		config.setAddress("127.0.0.1:2181");
		return config;
	}
	
	//<dubbo:protocol name="dubbo" port="20886"></dubbo:protocol>
	@Bean
	public ProtocolConfig configProtocolConfig() {
		ProtocolConfig config = new ProtocolConfig();
		config.setName("dubbo");
		config.setPort(20886);
		return config;
	}
	
	/**
	 * 	<dubbo:service interface="com.atguigu.gmall.service.UserService" 
			ref="userServiceImpl">
		</dubbo:service>
	 */
	@Bean
	public ServiceConfig<UserService> userServiceConfig(UserService userService) {
		//userService 这个是实现类,配置到spring容器中,自动的导入
		ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
		serviceConfig.setInterface(UserService.class);
		serviceConfig.setRef(userService);
		
		//配置每一个method的信息
		MethodConfig methodConfig = new MethodConfig();
		methodConfig.setName("getUserAddressList");
		methodConfig.setTimeout(1000);
		
		//将method的设置关联到service配置中
		List<MethodConfig> list = new ArrayList<MethodConfig>();
		list.add(methodConfig);
		
		serviceConfig.setMethods(list);
		
		return serviceConfig;
	}
	
}
