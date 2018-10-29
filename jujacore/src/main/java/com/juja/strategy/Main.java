package com.juja.strategy;

public class Main {

    public static void main(String[] args) {
        //Lock lock = new LockImpl("[1, 2, 3, 4, 5, 6, 7]");

        //Key oneKey = new ClassicIronBilletKey(1, 2, 3, 4, 5, 6, 7);
        Key anotherKey = new ClassicIronBilletKey(2, 4, 5, 2, 7, 3, 1);

        //testLock(lock, oneKey, anotherKey);

        Lock lock2 = new LockImpl("1, 2, 3, 36, 4, 5, 6");
        int[] i1 = new int[]{1, 2, 3};
        int[] i2 = new int[]{4, 5, 6};
        Key oneKey2 = new SecureIronBilletKey(i1, i2);
        testLock(lock2, oneKey2, anotherKey);
    }

    private static void testLock(Lock lock, Key oneKey, Key anotherKey) {
        System.out.println(lock.isOpened());
        lock.unlock(oneKey);
        System.out.println(lock.isOpened());

        lock.unlock(anotherKey);
        System.out.println(lock.isOpened());
        lock.unlock(oneKey);
        System.out.println(lock.isOpened());
    }
}
