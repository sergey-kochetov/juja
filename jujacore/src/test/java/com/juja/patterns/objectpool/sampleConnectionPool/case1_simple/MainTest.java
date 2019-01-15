package com.juja.patterns.objectpool.sampleConnectionPool.case1_simple;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest extends AbstractTest {
    @Test
    public void test() throws Exception {
        // when
        Main.main(new String[0]);

        // then
        assertEquals("init pool...\n" +
                "put Resource@1 to pool\n" +
                "put Resource@2 to pool\n" +
                "put Resource@3 to pool\n" +
                "...init done!\n" +
                "get Resource@1 from pool\n" +
                "get Resource@2 from pool\n" +
                "Resource@1 processed: data1\n" +
                "put Resource@1 to pool\n" +
                "get Resource@3 from pool\n" +
                "Resource@3 processed: data3\n" +
                "put Resource@3 to pool\n" +
                "Resource@2 processed: data2\n" +
                "put Resource@2 to pool\n" +
                "-------------------------------\n" +
                "init pool...\n" +
                "put Resource@4 to pool\n" +
                "put Resource@5 to pool\n" +
                "...init done!\n" +
                "get Resource@4 from pool\n" +
                "get Resource@5 from pool\n" +
                "Resource@4 processed: data1\n" +
                "put Resource@4 to pool\n" +
                "get Resource@4 from pool\n" +
                "Resource@4 processed: data3\n" +
                "put Resource@4 to pool\n" +
                "Resource@5 processed: data2\n" +
                "put Resource@5 to pool\n", console.getOut());
    }

}