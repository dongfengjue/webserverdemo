package com.chenbing.Collection;

/*
* 简单的树
* 通过递归往树里增加节点
* 没有平衡
* */
public class MyTree {

    public static void main(String[] args) {
        MyTree myTree = new MyTree();
        Node myNode = myTree.joinNode(null,100);
        myNode = myTree.joinNode(myNode,2);
        myNode = myTree.joinNode(myNode,5);
        myNode = myTree.joinNode(myNode,80);
        myNode = myTree.joinNode(myNode,180);
        myNode = myTree.joinNode(myNode,6);
        myNode = myTree.joinNode(myNode,1);
        myNode = myTree.joinNode(myNode,8);
        myTree.printf(myNode);
    }

    /***
     *  打印树里的内容
     * @param node
     */
    public void printf(Node node) {
        if (node == null) {
            return;
        }
        node.print();
        printf(node.getLeft());
        printf(node.getRight());

    }

    /***
     * 获取一个树或者向树里增加节点
     * @param node
     * @param id
     * @return
     */
    public Node joinNode(Node node, Integer id) {
        if (null == id) {
            return null;
        }
        if (null == node) {
            node = new Node(id,"根节点");
        } else {
            node = pushNode(node, id);
        }
        return node;
    }


    /***
     * 增加节点的实现
     * @param node
     * @param id
     * @return
     */
    public Node pushNode(Node node, Integer id) {
        Node left = node.getLeft();
        Node right = node.getRight();
        Integer rootId = node.getId();
        if (rootId > id) {
            if (null == left) {
                left = new Node(id,rootId+"的左边");
                node.setLeft(left);
                return node;
            } else {
                pushNode(left, id);
            }
        } else {
            if (null == right) {
                right= new Node(id,rootId+"的右边");
                node.setRight(right);
                return node;
            } else {
                pushNode(right, id);
            }
        }
        return node;
    }

    public class Node {

        private Integer id;
        private String name;
        private Node left = null;
        private Node right = null;


        public Node(Integer id) {
            this.id = id;
        }

        public Node(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void print() {
            System.out.println(name + id);
        }
    }


}
