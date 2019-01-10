package com.juja.patterns.immutable.hashMapError;

// POJO Bean
public final class ImmutableKey {

    private final String data;

    public ImmutableKey(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    //public void setData(String data) { this.data = data; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableKey key = (ImmutableKey) o;

        return data != null ? data.equals(key.data) : key.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    @Override
    public String toString() {
        return data;
    }
}
