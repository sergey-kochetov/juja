package com.juja.patterns.objectpool.sampleConnectionPool.case3_withState;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResourceTest extends AbstractTest {

    @Test
    public void testWithoutState() {
        // given
        Resource resource = new Resource();

        // when
        resource.doit("data");

        // then
        assertEquals("Resource[null]@1 processed: data\n", console.getOut());
        assertEquals("Resource[null]@1", resource.toString());
    }

    @Test
    public void testWithState() {
        // given
        Resource resource = new Resource();

        // when
        resource.setState("state");
        resource.doit("data");


        // then
        assertEquals("Resource[null]@1 state = state\n" +
                "Resource[state]@1 processed: data\n", console.getOut());
        assertEquals("Resource[state]@1", resource.toString());
    }
}
