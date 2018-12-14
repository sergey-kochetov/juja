package com.juja.patterns.command.list;

public class LinkedListReceiver {
    private Node first;
    private Node last;

    public static class Node {
        public Node next;
        public Node prev;
        public String value;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getLast() {
        return last;
    }

    public void setLast(Node last) {
        this.last = last;
    }
}
