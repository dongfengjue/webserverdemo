package com.chenbing.SpringIoc;

public class BImpl implements  B{
    A a ;
    public void setA(A a){
        this.a = a;
    }

    public void print(){
        System.out.println("hello B");
        a.print();
    }
}
