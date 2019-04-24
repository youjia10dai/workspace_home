package com.dxz.demo.configuration5;
import org.springframework.stereotype.Component;

@Component
public class TestBean {

    private String username;
    private String url;
    private String password;

    public void sayHello() {
        System.out.println("TestBean sayHello...");
    }

    public String toString() {
        return "username:" + this.username + ",url:" + this.url + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBean start");
    }

    public void cleanUp() {
        System.out.println("TestBean destory");
    }
}