package com.chenbing.SpringIoc;

public class start {
    public static void main(String args[]) throws Exception {
        ClassPathXmlApplicationContext cpxa = new ClassPathXmlApplicationContext();
        B b = (B) cpxa.getBean("b");
        b.print();
    }
}
