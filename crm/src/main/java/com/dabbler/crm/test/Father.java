package com.dabbler.crm.test;

public class Father {
    public String name = "father";

    public void m1() {
        System.out.println("我是父类的方法");
    }
}

class Son extends Father {

    public String name = "son";

    @Override
    public void m1() {
        System.out.println("我是子类的方法");
    }
    public void m2() {
        System.out.println("adsasdadada");
    }
}

class Test {
    public static void main(String[] args) {
        Father son = new Son();
        System.out.println("son.name = " + son.name);
        son.m1();
    }
}