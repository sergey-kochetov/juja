package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResourceTest extends AbstractTest {

    @Test
    public void test() {
        // given
        Resource resource = new Resource();

        // when
        resource.doit("data");

        // then
        assertEquals("Resource@1 processed: data\n", console.getOut());
        assertEquals("Resource@1", resource.toString());
    }
}
