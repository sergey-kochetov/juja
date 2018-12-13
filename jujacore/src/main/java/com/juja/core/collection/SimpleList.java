package com.juja.core.collection;

import java.util.Iterator;

interface SimpleList<E> {
    public boolean add(E newElement);
    public E get(int index);
    public Iterator<E> iterator();
    public int size();
    public boolean isEmpty();
    public E remove(int index);
}