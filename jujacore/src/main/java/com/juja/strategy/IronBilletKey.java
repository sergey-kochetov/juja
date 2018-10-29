package com.juja.strategy;

import java.util.Arrays;

public abstract class IronBilletKey implements Key {

    @Override
    public String getCode() {
        return Arrays.toString(getNotch());
    }

    abstract int[] getNotch();
}
