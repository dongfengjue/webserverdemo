package com.chenbing.base;

public class DoubleFormat {

    public static void doubleFormat(){
        Double d = 54564564564.32132132d;
        System.out.println(d);
        System.out.println(d.toString());
        System.out.println(String.valueOf(d));
        System.out.println(Double.valueOf(d.toString()));
        System.out.println(String.format("%.2f",d));
    }

    public static void main(String args[]){
        doubleFormat();
    }
}
