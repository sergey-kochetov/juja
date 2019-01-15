package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResourceTest extends AbstractTest {

    @Test
    public void test() {
        // given
        Resource resource = new ResourceImpl();

        // when
        resource.doit("data");

        // then
        assertEquals("ResourceImpl@1 processed: data\n", console.getOut());
        assertEquals("ResourceImpl@1", resource.toString());
    }

    @Test
    public void testClose() {
        // given
        Resource resource = new ResourceImpl();

        // when
        resource.close();

        // then
        assertEquals("", console.getOut());
    }
}
