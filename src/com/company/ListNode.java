package com.company;

public class ListNode<T>{
    public ListNode <T> next;
    public T data;

    // Creates an empty node.
    public ListNode(){
        next = null;
        data = null;
    }

    // Creates a node storing the specified data.
    public ListNode (T data){
        next = null;
        this.data = data;
    }

    // Returns the node that follows this one.
    public ListNode getNext(){
        return next;
    }

    // Sets the node that follows this one.
    public void setNext (ListNode node){
        next = node;
    }

    // Returns the data stored in this node.
    public T getData(){
        return data;
    }

    // Sets the data stored in this node.
    public void setdata (T elem){
        data = elem;
    }


}