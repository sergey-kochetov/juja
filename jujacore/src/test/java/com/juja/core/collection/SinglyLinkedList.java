package com.juja.core.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList <T> {
    private Node<T> first = null;

    public void add(T element) {
        if (first == null) {
            first = new Node<T>(element);

        } else {
            Node<T> current = getLast();
            current.next = new Node<T>(element);
        }
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) throw new IndexOutOfBoundsException();
            if (current.element == null) throw new NoSuchElementException();
            T element = current.element;
            current = current.next;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node<T> getLast() {
        Node<T> last = first;
        while (last.next != null) {
            last = last.next;
        }
        return last;
    }

    private class Node<T> {
        T element;
        Node<T> next;

        Node(T element) {
            this.element = element;
            this.next = null;
        }
    }
}
