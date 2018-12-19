package com.chenbing.Collection;

import java.util.HashSet;
import java.util.TreeMap;

public class Set {
    public static void main(String args[]){


        HashSet hashSet = new HashSet();
        for(int i = 0 ; i < 10;i++){
            hashSet.add("index"+i);
        }

        TreeMap<String,String> treeMap = new TreeMap<String,String>();
        treeMap.put("123","789");

        treeMap.get("123");

    }
}
