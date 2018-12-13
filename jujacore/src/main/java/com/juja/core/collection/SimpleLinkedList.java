package com.juja.core.collection;


import java.util.Iterator;
import java.util.NoSuchElementException;


public class SimpleLinkedList <E> implements SimpleList<E> {
    private Node<E> first = null; // head
    private Node<E> last = null; // tail
    private int size = 0;

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || size < index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private Node<E> node(int index) {
        if (index < size / 2) {
            Node<E> f = first;
            for (int i = 0; i < index; i++) {
                f = f.next;
            }
            return f;
        } else {
            Node<E> l = last;
            for (int i = size - 1; i > index; i--) {
                l = l.prev;
            }
            return l;
        }
    }

    private E unlink(Node<E> x) { //todo:
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public boolean add(E newElement) {
        final Node<E> tmp = last;
        final Node<E> newNode = new Node<>(tmp, newElement, null);
        last = newNode;
        if (tmp == null) {
            first = newNode;
        } else {
            tmp.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).item;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    /*BODY*/

    @Override
    public boolean equals(Object o) {
        return toString().equals(o.toString());
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            result.append(node.item.toString());
            if (i != size - 1) {
                result.append(", ");
            }
            node = node.next;
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int position = 0;
            private boolean status = false;
            private Node<E> root = first;

            @Override
            public boolean hasNext() {
                if (!status) {
                    status = true;
                }
                if (first == null || size == 0) {
                    return false;
                }
                return position != size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E result = root.item;
                root = root.next;
                position++;
                return result;
            }

            @Override
            public void remove() {
                if (!status) {
                    throw new IllegalStateException();
                }
                status = false;
                position--;
                SimpleLinkedList.this.remove(position);
            }
        };
    }
}

