package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;


public interface Resource {
    // этот метод будет вызывать клиент у готового ресурса
    void doit(String data);

    // сам ресурс ничего не делает в этом методе
    void close();
}
