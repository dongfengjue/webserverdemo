package com.chenbing.tree.NormalTree;

import java.util.HashSet;
import java.util.Set;

public class NormalTree {

        private Set<Node> tree;
        private Node root;

        public NormalTree(String[] strings){
            initNormalTree(strings);
        }
        public Set<Node> getTree() {
            return tree;
        }

        public void setTree(Set<Node> tree) {
            this.tree = tree;
        }

        public Node getRoot() {
            return root;
        }

        public void setRoot(Node root) {
            this.root = root;
        }

        private  void initNormalTree(String[] strings){
            for (int i = 0; i < strings.length; i++) {
                Node node =  new Node();
                node.setData(strings[i]);

                if(getTree() == null){
                    firstTreeNode(this,node);
                }else{
                    addTreeNode(this,node);
                }
            }

        }

        private  void addTreeNode(NormalTree tree, Node node){
            Node rootNode = tree.getRoot();
            insertNode(rootNode,node);
        }

        private  Node insertNode(Node fatherNode,Node node){
            int compareRes = compareNode(fatherNode,node);
            if(compareRes > 0){
                if(fatherNode.getRight() == null){
                    fatherNode.setRight(node);
                    return node;
                }
                return insertNode(fatherNode.getRight(),node);
            }else{
                if(fatherNode.getLeft() == null){
                    fatherNode.setLeft(node);
                    return node;
                }
                return insertNode(fatherNode.getLeft(),node);
            }
        }
        private  int compareNode(Node firstNode,Node secondNode){
            int firstStr = firstNode.getData() == null ? 0: Integer.valueOf(firstNode.getData().toString());
            int secondStr = secondNode.getData() == null ? 0: Integer.valueOf(secondNode.getData().toString());
            return firstStr - secondStr;
        }

        private  void firstTreeNode(NormalTree tree, Node node){
            Set<Node> nodeSet = new HashSet<>();
            nodeSet.add(node);
            tree.setRoot(node);
            tree.setTree(nodeSet);
        }
}
