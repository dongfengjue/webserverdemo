package com.chenbing.Collection;

import java.util.Hashtable;

public class MyHashTable {
    public static void main(String args[]){
        Hashtable<String,String> hashtable = new Hashtable<String, String>();
        hashtable.put("123","789");
        System.out.println(hashtable.get("123"));
    }

}
