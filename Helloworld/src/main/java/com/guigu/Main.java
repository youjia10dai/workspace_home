package com.guigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @SpringBootApplication 标注一个主程序类,说明这是个springboot应用
 *
 */
@SpringBootApplication
public class Main {

	public static void main(String[] args){
		//启动 Spring boot 应用
		SpringApplication.run(Main.class, args);
	}
	
}
