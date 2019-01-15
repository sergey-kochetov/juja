package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class MainTest extends AbstractTest {

    @Test
    public void test() throws Exception {
        // when
        Main.main(new String[0]);

        // then
        assertEquals("create Resource@1 from factory-method\n" +
                "get Resource@1 from pool\n" +
                "create Resource@2 from factory-method\n" +
                "get Resource@2 from pool\n" +
                "Resource@1 processed: data1\n" +
                "put Resource@1 to pool\n" +
                "get Resource@1 from pool\n" +
                "Resource@1 processed: data3\n" +
                "put Resource@1 to pool\n" +
                "Resource@2 processed: data2\n" +
                "put Resource@2 to pool\n", console.getOut());
    }
}
