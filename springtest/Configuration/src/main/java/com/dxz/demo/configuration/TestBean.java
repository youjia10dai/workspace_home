package com.dxz.demo.configuration;

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
        System.out.println("TestBean ��ʼ��������");
    }

    public void cleanUp() {
        System.out.println("TestBean ���١�����");
    }
}