package com.chenbing.tree.RBTree;

import com.chenbing.tree.TreePrint.TreeNode;

import java.util.Comparator;

public class Node implements TreeNode {

        public Node parent;

        public Node left;

        public Node right;

        public Object data;

        public Boolean red;

        public Node root() {
            for (Node r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }


//    @Override
//    public int compareTo(Object o) {
//        Node oo = (Node)o;
//        return Integer.valueOf(this.data.toString()) - Integer.valueOf(oo.data.toString());
//    }

    @Override
    public String getPrintInfo() {
        return String.valueOf(data);
    }

    @Override
    public TreeNode getLeftChild() {
        return left;
    }

    @Override
    public TreeNode getRightChild() {
        return right;
    }

    @Override
    public String toString() {
        return "[" +data + "]";
    }
}
