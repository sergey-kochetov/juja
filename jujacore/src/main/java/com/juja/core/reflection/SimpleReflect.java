package com.juja.core.reflection;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleReflect {

    public static void main(String[] args) {
        SimpleReflect sr = new SimpleReflect();
        sr.runTest();
    }

    private void runTest() {
        try {
            Class cl = Class.forName("com.juja.core.reflection.SimpleReflectTest");

            Constructor cst = cl.getConstructor();
            Object entity = cst.newInstance();

            Method[] methods = cl.getMethods();
            for (Method m : methods) {
                Test annotation = m.getAnnotation(Test.class);
                if (annotation != null) {
                m.invoke(entity);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
