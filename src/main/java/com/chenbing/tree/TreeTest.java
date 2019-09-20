package com.chenbing.tree;

import com.alibaba.fastjson.JSON;
import com.chenbing.tree.NormalTree.NormalTree;
import com.chenbing.tree.RBTree.RBTree;
import com.chenbing.tree.TreePrint.TreeNode;
import com.chenbing.tree.TreePrint.TreePrintUtil;

public class TreeTest {

    public static void main(String[] args){
        String[] strings = {"1","2","3","22","43","32","212","312","12","33","13","21"};
//        NormalTree rbTree = new NormalTree(strings);
        RBTree rbTree = new RBTree(strings);
//        System.out.println(JSON.toJSONString(rbTree));
        TreePrintUtil.pirnt( rbTree.root());
    }
}
