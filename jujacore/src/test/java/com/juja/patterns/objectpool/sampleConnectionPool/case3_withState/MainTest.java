package com.juja.patterns.objectpool.sampleConnectionPool.case3_withState;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest extends AbstractTest {

    @Test
    public void test() throws Exception {
        // when
        Main.main(new String[0]);

        // then
        assertEquals("init pool...\n" +
                "Resource[null]@1 state = null\n" +
                "put Resource[null]@1 to pool\n" +
                "Resource[null]@2 state = null\n" +
                "put Resource[null]@2 to pool\n" +
                "...init done!\n" +
                "get Resource[null]@1 from pool\n" +
                "Resource[null]@1 state = state1\n" +
                "get Resource[null]@2 from pool\n" +
                "Resource[state1]@1 state = state2\n" +
                "Resource[state2]@1 processed: data1\n" +
                "Resource[state2]@1 state = null\n" +
                "put Resource[null]@1 to pool\n" +
                "get Resource[null]@1 from pool\n" +
                "Resource[null]@1 state = state3\n" +
                "Resource[state3]@1 processed: data3\n" +
                "Resource[state3]@1 state = null\n" +
                "put Resource[null]@1 to pool\n" +
                "Resource[null]@2 processed: data2\n" +
                "Resource[null]@2 state = null\n" +
                "put Resource[null]@2 to pool\n", console.getOut());
    }
}
