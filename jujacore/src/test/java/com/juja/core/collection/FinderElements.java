package com.juja.core.collection;

import java.util.Iterator;

public class FinderElements {
    public static Integer findElement(SinglyLinkedList<Integer> singlyLinkedList, int k) {
        int count = 0;
        for (Iterator it = singlyLinkedList.iterator(); it.hasNext(); ) {
            it.next();
            count++;
        }
        Integer[] result = new Integer[count];
        int index = 0;
        for (Iterator it = singlyLinkedList.iterator(); it.hasNext();) {
            result[index++] = (Integer) it.next();
        }
        return result[count - k - 1];
    }


}
