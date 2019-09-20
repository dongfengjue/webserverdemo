package com.chenbing.tree.RBTree;

import java.util.*;

public class RBTree {

        private HashSet<Node> tree;
//        private Node root;

        public RBTree(String[] strings){
            initTree(strings);
        }
        public Set<Node> getTree() {
            return tree;
        }

        public void setTree(HashSet<Node> tree) {
            this.tree = tree;
        }

//        public Node getRoot() {
//            return root;
//        }
//
//        public void setRoot(Node root) {
//            this.root = root;
//        }

        private  void initTree(String[] strings){
//            strings = (new HashSet<>(Arrays.asList(strings)) ).toArray(new String[0]);
            for (int i = 0; i < strings.length; i++) {
                Node node =  new Node();
                node.data = strings[i];
                node.red = true;

                if(getTree() == null){
                    firstTreeNode(this,node);
                }else{
                    insertNode(this.root(),node);
                }
            }

        }

        // 插入后平衡
        private Node balanceInsertion(Node root,Node x) {
            x.red = true;
            for (Node xp, xpp, xppl, xppr;;) {
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                if (xp == (xppl = xpp.left)) {
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        private Node rotateLeft(Node root,Node p) {
            Node r, pp, rl;
            if (p != null && (r = p.right) != null) {
                if ((rl = p.right = r.left) != null)
                    rl.parent = p;
                if ((pp = r.parent = p.parent) == null)
                    (root = r).red = false;
                else if (pp.left == p)
                    pp.left = r;
                else
                    pp.right = r;
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        private Node rotateRight(Node root,Node p) {
            Node l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;
                if ((pp = l.parent = p.parent) == null)
                    (root = l).red = false;
                else if (pp.right == p)
                    pp.right = l;
                else
                    pp.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }

        private  void addTreeNode(RBTree tree, Node node){
            Node rootNode = tree.root();
            insertNode(rootNode,node);
        }

        private  Node insertNode(Node fatherNode,Node node){
            int compareRes = compareNode(fatherNode,node);
            if(compareRes < 0){
                if(fatherNode.right == null){
                    fatherNode.right = node;
                    node.parent = fatherNode;
                    balanceInsertion(fatherNode,node);
                    return node;
                }
                return insertNode(fatherNode.right,node);
            }else{
                if(fatherNode.left == null){
                    fatherNode.left = node;
                    node.parent = fatherNode;
                    balanceInsertion(fatherNode,node);
                    return node;
                }
                return insertNode(fatherNode.left,node);
            }
        }
        private  int compareNode(Node firstNode,Node secondNode){
            int firstStr = firstNode.data == null ? 0: Integer.valueOf(firstNode.data.toString());
            int secondStr = secondNode.data == null ? 0: Integer.valueOf(secondNode.data.toString());
            return firstStr - secondStr;
        }

        private  void firstTreeNode(RBTree tree, Node node){
            HashSet<Node> nodeSet = new HashSet<>();
            nodeSet.add(node);
//            tree.setRoot(node);
            tree.setTree(nodeSet);
        }

        public Node root() {
            Node node = new Node();
            if(this.getTree() != null){
                if(this.getTree().iterator().hasNext()){
                    node = this.getTree().iterator().next();
                }
            }
            for (Node r = node, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

}
