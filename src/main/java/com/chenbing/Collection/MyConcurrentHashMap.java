package com.chenbing.Collection;

import java.util.concurrent.ConcurrentHashMap;

public class MyConcurrentHashMap {
    public static void main(String args[]){
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("123","789");
        System.out.println("----"+concurrentHashMap.get("123"));
    }
}
