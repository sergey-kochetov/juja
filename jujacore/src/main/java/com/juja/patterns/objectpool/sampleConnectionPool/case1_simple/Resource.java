package com.juja.patterns.objectpool.sampleConnectionPool.case1_simple;

import com.juja.patterns.objectpool.IdGenerator;

public class Resource {
    public Resource() {
        setup();
    }

    private void setup() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // этот метож вызывает клиент готового ресурса
    public void doit(String data) {
        System.out.println(this + " processed: " + data);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + id;
    }
    public String id = IdGenerator.put(this);
}
