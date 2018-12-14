package com.juja.core.collection;

import org.junit.Test;

public class SimpleLinkedListTest {

    @Test
    public void test_01() {
        Integer[] testElement  = {1,2,3,4,5,6,7,8};
        //first array list
        SimpleLinkedList<Integer> firstSimpleLinkedList = new SimpleLinkedList<>();
        for (int i = 0; i <testElement.length ; i++) {
            firstSimpleLinkedList.add(testElement[i]);
        }

        //second array list
        SimpleLinkedList<Integer> secondSimpleLinkedList = new SimpleLinkedList<>();

        for (int i = 0; i <testElement.length ; i++) {
            secondSimpleLinkedList.add(testElement[i]);
        }


        //check
        if (!firstSimpleLinkedList.equals(secondSimpleLinkedList)) {
            System.out.println(firstSimpleLinkedList.equals(secondSimpleLinkedList));
            System.out.println(firstSimpleLinkedList);
            System.out.println(secondSimpleLinkedList);
            throw new AssertionError("Two simpleLinkedList with identical type and with identical elements should be equals");
        }

        //System.out.print("OK");
    }
    @Test
    public void test_02() {
        //first array list
        SimpleLinkedList<Integer> firstSimpleLinkedList = new SimpleLinkedList<>();
        firstSimpleLinkedList.add(1);
        firstSimpleLinkedList.add(2);

        //check
//        if (firstSimpleLinkedList.equals(null)){
//            System.out.println(firstSimpleLinkedList);
//            throw new AssertionError("simpleLinkedList should be not equals null");
//        }

        //System.out.print("OK");
    }
    @Test
    public void test_03() {

        //first array list
        SimpleLinkedList<Integer> firstSimpleLinkedList = new SimpleLinkedList<>();

        //second array list
        SimpleLinkedList<Integer> secondSimpleLinkedList = new SimpleLinkedList<>();


        //check
//        if (!(firstSimpleLinkedList.hashCode()==secondSimpleLinkedList.hashCode()))
//            throw new AssertionError("Two empty simpleLinkedList with identical type should be equals");


        //System.out.print("OK");

    }
}

