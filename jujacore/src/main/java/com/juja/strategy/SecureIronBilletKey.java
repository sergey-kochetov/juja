package com.juja.strategy;

import java.util.Arrays;

public class SecureIronBilletKey extends IronBilletKey {

    private int[] notch1;
    private int[] notch2;

    public SecureIronBilletKey(final int[] n1, final int[] n2) {
        this.notch1 = n1;
        this.notch2 = n2;
    }

    @Override
    int[] getNotch() {
        int[] result = new int[notch1.length + notch2.length + 1];
        System.arraycopy(result, 0, notch1, 0, notch1.length);
        result[notch1.length] = 36;
        System.arraycopy(result, notch1.length + 1, notch2, 0, notch2.length);
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(notch1) +
                Arrays.toString(notch2);
    }
}
