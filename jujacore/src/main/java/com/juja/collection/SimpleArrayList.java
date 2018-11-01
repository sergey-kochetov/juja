package com.juja.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class SimpleArrayList <E> implements SimpleList<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 36;
    private E[] data;
    private int size = 0;

    public SimpleArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public SimpleArrayList(int initialCapacity) {
        this.data = (E[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(E newElement) {
        ensureCapacity(size + 1);
        data[size] = newElement;
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return data[index];
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
        rangeCheck(index);
        E oldValue = data[index];
        int numMoved = size - index - 1;
        System.arraycopy(data, index + 1, data, index, numMoved);
        data[--size] = null;
        return oldValue;
    }

    private void rangeCheck(int index) {
        if (index < 0 || size < index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, data.length + (data.length >> 1));
            E[] newData = (E[]) new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, data.length);
            this.data = newData;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size ; i++){
            result.append(data[i].toString());
            if (i != size -1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleArrayList<?> that = (SimpleArrayList<?>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int position = 0;
            private boolean status = false;
            @Override
            public boolean hasNext() {
                if (!status) {
                    status = true;
                }
                if (data == null || data.length == 0) {
                    return false;
                }
                return position != size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[position++];
            }

            @Override
            public void remove() {
                if (!status) {
                    throw new IllegalStateException();
                }
                status = false;
                position--;
                SimpleArrayList.this.remove(position);

            }
        };
    }


}