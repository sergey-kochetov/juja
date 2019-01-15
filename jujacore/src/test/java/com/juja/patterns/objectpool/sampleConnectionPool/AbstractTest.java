package com.juja.patterns.objectpool.sampleConnectionPool;

import com.juja.patterns.objectpool.IdGenerator;
import org.junit.Before;

public class AbstractTest {
    protected ConsoleMock console = new ConsoleMock();

    @Before
    public void setup() {
        IdGenerator.reset();
    }
}
