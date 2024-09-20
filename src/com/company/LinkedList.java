package com.company;

public class LinkedList<T>{
    // This class has a default constructor: 
    public LinkedList() {
        length = 0;
    }

    // This is the only field of the class.  It holds the head of the list 
    ListNode<T> head;

    // Length of the linked list
    private int length = 0;

    // Return the first node in the list 
    public ListNode<T> getHead() {
        return head;
    }

    // Insert a node at the beginning of the list 
    public void insertAtBegin(ListNode<T> node) {
        node.setNext(head);
        head = node;
        length ++;
    }

    // Insert a node at the end of the list 
    public void insertAtEnd(ListNode<T> node) {
        if (head == null)
            head = node;
        else {
            ListNode<T> p, q;
            for(p = head; (q = p.getNext()) != null; p = q);
            p.setNext(node);
        }
        length ++;
    }

    // Add a new value to the list at a given position.
    // All values at that position to the end move over to make room.
    public void insert(T data, int position) {
        // fix the position
        if (position < 0) {
            position = 0;
        }
        if (position > length) {
            position = length;
        }

        // if the list is empty, make it be the only element
        if (head == null) {
            head = new ListNode(data);
        }
        // if adding at the front of the list...
        else if (position == 0) {
            ListNode<T> temp = new ListNode(data);
            temp.next = head;
            head = temp;
        }
        // else find the correct position and insert
        else {
            ListNode<T> temp = head;
            for (int i=1; i<position; i+=1) {
                temp = temp.getNext();
            }
            ListNode<T> newNode = new ListNode(data);
            newNode.next = temp.next;
            temp.setNext(newNode);
        }
        // the list is now one value longer
        length += 1;
    }

    // Remove and return the node at the head of the list 
    public ListNode<T> removeFromBegin() {
        ListNode<T> node = head;
        if (node != null) {
            head = node.getNext();
            node.setNext(null);
        }
        return node;
    }

    // Remove and return the node at the end of the list 
    public ListNode<T> getLast() {
        if (head == null)
            return null;
        if (head.getNext() == null) {
            return head;
        }
        ListNode<T> p = head.getNext();
        while(p.getNext() != null) {
            p = p.getNext();
        }
        return p;
    }

    // Remove and return the node at the end of the list 
    public ListNode<T> removeFromEnd() {
        if (head == null)
            return null;
        ListNode<T> p = head, q = null, next = head.getNext();
        if (next == null) {
            head = null;
            // reduce the length of the list
            length-=1;
            return p;
        }
        while((next = p.getNext()) != null) {
            q = p;
            p = next;
        }
        q.setNext(null);
        // reduce the length of the list
        length-=1;
        return p;
    }

    // Remove a node matching the specified node from the list.  
    // Use equals() instead of == to test for a matched node.
    public void removeMatched(ListNode node) {
        if (head == null)
            return;
        if (node.equals(head)) {
            head = head.getNext();
            // reduce the length of the list
            length-=1;
            return;
        }
        ListNode<T> p = head, q = null;
        while((q = p.getNext()) != null) {
            if (node.equals(q)) {
                p.setNext(q.getNext());
                // reduce the length of the list
                length-=1;
                return;
            }
            p = q;
        }
    }

    // Remove the value at a given position.
    // If the position is less than 0, remove the value at position 0.
    // If the position is greater than 0, remove the value at the last position.
    public void remove(int position) {
        // fix position
        if (position < 0) {
            position = 0;
        }

        if (position >= length) {
            position = length-1;
        }

        // if nothing in the list, do nothing
        if (head == null)
            return;

        // if removing the head element...
        if (position == 0) {
            head = head.getNext();
        }
        // else advance to the correct position and remove
        else {
            ListNode<T> temp = head;
            for (int i=1; i<position; i+=1) {
                temp = temp.getNext();
            }
            temp.setNext(temp.getNext().getNext());
        }
        // reduce the length of the list
        length -= 1;
    }

    // Return a string representation of this collection, in the form ["str1","str2",...].
    public String toString() {
        String result = "[";
        if (head == null) {
            return result+"]";
        }
        result = result + head.getData();
        ListNode<T> temp = head.getNext();
        while (temp != null) {
            result = result + "," + temp.getData();
            temp = temp.getNext();
        }
        return result + "]";
    }

    // Return the current length of the list.
    public int length() {
        return length;
    }

    // Find the position of the first value that is equal to a given value.
    // The equals method us used to determine equality.
    public int getPosition(T data) {
        // go looking for the data
        ListNode<T> temp = head;
        int pos = 0;
        while (temp != null) {
            if (temp.getData().equals(data)) {
                // return the position if found
                return pos;
            }
            pos += 1;
            temp = temp.getNext();
        }
        // else return -1
        return Integer.MIN_VALUE;
    }

    // Size of the list.
    public boolean isEmpty(){
        return length==0;
    }

    // Remove everything from the list.
    public void clearList(){
        head = null;
        length = 0;
    }
}