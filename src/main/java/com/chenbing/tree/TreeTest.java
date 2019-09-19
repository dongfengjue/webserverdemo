package com.chenbing.tree;

import com.alibaba.fastjson.JSON;
import com.chenbing.tree.NormalTree.NormalTree;

public class TreeTest {

    public static void main(String[] args){
        String[] strings = {"1","2","3","22","43","32","43","212","312","12","33","212","13","21","312","212","13"};
        NormalTree rbTree = new NormalTree(strings);
        System.out.println(JSON.toJSONString(rbTree));
    }
}
