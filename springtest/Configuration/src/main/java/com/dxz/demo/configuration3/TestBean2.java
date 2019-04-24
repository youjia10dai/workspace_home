package com.dxz.demo.configuration3;
public class TestBean2 {
    private String username;
    private String url;
    private String password;

    public void sayHello() {
        System.out.println("TestBean2 sayHello...");
    }

    public String toString() {
        return "TestBean2 username:" + this.username + ",url:" + this.url + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBean2 ³õÊ¼»¯¡£¡£¡£");
    }

    public void cleanUp() {
        System.out.println("TestBean2 Ïú»Ù¡£¡£¡£");
    }
}