package com.caihong.algorithm.tree;

import com.sun.source.tree.NewArrayTree;

public class AvlTree<AnyType extends Comparable<? super AnyType>> {

    private static final int ALLOWED_IMBALANCE = 1;

    // inner class
    private static class AvlNode<AnyType> {

        AnyType element;
        AvlNode<AnyType> left;
        AvlNode<AnyType> right;
        int height;

        // inner class
        AvlNode(){};

        AvlNode(AnyType element) {
            this(element, null, null);
        }

        AvlNode(AnyType element, AvlNode<AnyType> left, AvlNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    private int height(AvlNode<AnyType> node) {
        return node == null ? -1 : node.height;
    }

    //insert
    public AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            return new AvlNode<>(x, null, null);
        }

        int result = x.compareTo(t.element);
        if (result < 0) {
            t.left = insert(x, t.left);
        } else if (result > 0) {
            t.right = insert(x, t.right);
        } else {
            //duplicate, do nothing
        }
        return balance(t);
    }

    public AvlNode<AnyType> remove(AnyType x,AvlNode<AnyType> t){
        if (t==null) {
            return t;
        }
        int result = x.compareTo(t.element);
        if(result<0){
            t.left=remove(x,t.left);
        }else if(result>0){
            t.right=remove(x,t.right);
        }else if(t.left!=null&&t.right!=null){
            t.element=findMin(t.right).element;
            t.right=remove(t.element,t.right);
        }else {
            t=(t.left!=null)?t.left:t.right;
        }
        return balance(t);
    }

    private AvlNode<AnyType> findMin(AvlNode<AnyType> t) {
        if(t==null || t.left==null){
            return t;
        }
        return findMin(t.left);
    }

    //The method of balance
    private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
        if (t == null) {
            return t;
        }
        //t.left?????????
        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            //??????
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                //??????
                t = doubleWithLeftChild(t);
            }
        }

        //t.right?????????
        if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            //??????
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                //??????
                t = doubleWithRightChild(t);
            }
        }
        //??????t.height
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    //???????????????
    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
        //???????????????
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }

    //???????????????
    private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    //???????????????
    private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> k3) {
        k3.right=rotateWithRightChild(k3.right);
        return rotateWithLeftChild(k3);
    }

    // ????????????
    public void printTree(){
        if(isEmpty()){
            System.out.println();
        }

        printTree(new AvlNode<AnyType>());
    }

    private void printTree(AvlNode<AnyType> root) {
        if (root !=null) {
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }

    private boolean isEmpty() {
        return false;
    }

    // ????????????
    public int calculateHeight(AvlNode root){
        if (root==null) {
            return -1;
        } else {
            return 1+Math.max(height(root.left), height(root.right));
        }
    }
}
