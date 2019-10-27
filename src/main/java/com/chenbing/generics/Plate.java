package com.chenbing.generics;

public class Plate<T>{
    private T item;
    public Plate(T t){item=t;}
    public void set(T t){item=t;}
    public T get(){return item;}

    public static void main(String[] args){
        // 上界通配符
        Plate<? extends Fruit> p=new Plate<Apple>(new Apple());

//不能存入任何元素
//        p.set(new Fruit());    //Error
//        p.set(new Apple());    //Error

//读取出来的东西只能存放在Fruit或它的基类里。
        Fruit newFruit1=p.get();
        Object newFruit2=p.get();
//        Apple newFruit3=p.get();    //Error


        // 下界通配符
        Plate<? super Apple> pp=new Plate<Fruit>(new Fruit());

//        pp.set(new Fruit());    //Error
        pp.set(new Apple());

//        Fruit newFruit4=pp.get();  //Error
        Object newFruit5=pp.get();
        Fruit f = (Fruit)newFruit5;
//        Apple newFruit6=pp.get();    //Error
    }
}