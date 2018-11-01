package com.juja.collection;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SimpleArrayListTest {

    @Test
    public void test_01() {

        // list is one element
        Integer testOneElement = 1;
        SimpleArrayList<Integer> simpleArrayList = new SimpleArrayList<>();

        simpleArrayList.add(testOneElement);

        //call and check
        Iterator<Integer> iterator = null;
        try {
            iterator = simpleArrayList.iterator();
        } catch (UnsupportedOperationException u) {
            throw new AssertionError("Iterator not implemented");
        }

        if (iterator == null)
            throw new AssertionError("Iterator must be no equals null");

        // call before get element
        if (!iterator.hasNext())
            throw new AssertionError("hasNext() must be returned true if list is not empty");

        // get element
        boolean isExceptionMethodNext = false;
        Integer actualResultNext = null;
        try {
            actualResultNext = iterator.next();
        } catch (NoSuchElementException e) {
            isExceptionMethodNext = true;
        }

        //call after get one element
        if (iterator.hasNext())
            throw new AssertionError("hasNext() must be returned false if no element");


        if (isExceptionMethodNext)
            throw new AssertionError("next() must be throw NoSuchElementException if no element");

        if (actualResultNext.compareTo(testOneElement) != 0)
            throw new AssertionError("OneElement must be equals " + testOneElement.toString() + " but found " + actualResultNext.toString());


        //System.out.print("OK");
    }
    @Test
    public void test_02() {

        Integer[] listElements = {1, 2, 3, 4, 5, 6, 7};
        Integer[] expectedElements = {2, 3, 4, 5, 6, 7};

        SimpleArrayList<Integer> arrayList = new SimpleArrayList<>();

        for (int i = 0; i < listElements.length; i++) {
            arrayList.add(listElements[i]);
        }

        SimpleArrayList<Integer> expectedArrayList = new SimpleArrayList<>();

        for (int i = 0; i < expectedElements.length; i++) {
            expectedArrayList.add(expectedElements[i]);
        }

        //call and check
        Iterator<Integer> iterator = null;
        try {
            iterator = arrayList.iterator();
        } catch (UnsupportedOperationException u) {
            throw new AssertionError("Iterator not implemented");
        }

        if (iterator == null)
            throw new AssertionError("Iterator must be no equals null");

        try {
            iterator.next();
            iterator.remove();
        } catch (IllegalStateException e) {
            throw new AssertionError("Non-expected throw IllegalStateException form iterator.remove()");
        }

        if (!arrayList.equals(expectedArrayList)) {
            //System.out.println(arrayList.equals(expectedArrayList));
            //System.out.println(arrayList.toString());
            //System.out.println(expectedArrayList.toString());
            throw new AssertionError("actual data is not equal to expected data");}

        //System.out.print("OK");
    }

    @Test
    public void test_03() {

        Integer[] expectedElements = {1, 2, 3, 4, 5, 6, 7};

        SimpleArrayList<Integer> simpleArrayList = new SimpleArrayList<>();

        for (int i = 0; i < expectedElements.length; i++) {
            simpleArrayList.add(expectedElements[i]);
        }

        //call and check
        Iterator<Integer> iterator = null;
        try {
            iterator = simpleArrayList.iterator();
        } catch (UnsupportedOperationException u) {
            throw new AssertionError("Iterator not implemented");
        }

        if (iterator == null)
            throw new AssertionError("Iterator must be no equals null");

        boolean isException = false;
        try {
            iterator.remove();
        } catch (IllegalStateException e) {
            isException = true;
        }

        if (!isException)
            throw new AssertionError("Expected throw IllegalStateException form iterator.remove()");

        //System.out.print("OK");
    }
    @Test
    public void test_04() {
        Integer[] expectedElements = {1, 2, 3, 4, 5, 6, 7};
        String expectedPrintString = "[1, 2, 3, 4, 5, 6, 7]";

        SimpleArrayList<Integer> simpleArrayList = new SimpleArrayList<>();

        for (int i = 0; i < expectedElements.length; i++) {
            simpleArrayList.add(expectedElements[i]);
        }

        //call

        String actualPrintString = simpleArrayList.toString();

        //check
        if (!expectedPrintString.equals(actualPrintString))
            throw new AssertionError("Should be printed " + expectedPrintString + " but printed " + actualPrintString);

        //System.out.print("OK");
    }

}