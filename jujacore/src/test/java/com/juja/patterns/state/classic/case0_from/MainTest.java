package com.juja.patterns.state.classic.case0_from;

import com.juja.patterns.command.classic.ConsoleMock;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class MainTest {
    private ConsoleMock console = new ConsoleMock();

    @Test
    public void test() {
        // when
        Main.main(new String[0]);

        // then
        assertEquals("Handled by A: data1\n" +
                "Handled by B: data2\n" +
                "Handled by C: data3\n" +
                "Handled by A: data4\n" +
                "Handled by B: data5\n", console.getOut());
    }
}
