package com.chenbing.tree.TreePrint;

public interface TreeNode {
        /**
         * 需要打印的信息
         * @return
         */
        String getPrintInfo();

        TreeNode getLeftChild();

        TreeNode getRightChild();
    }