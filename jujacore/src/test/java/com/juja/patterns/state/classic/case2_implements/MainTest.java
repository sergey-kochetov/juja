package com.juja.patterns.state.classic.case2_implements;

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
        assertEquals("Set state: A\n" +
                "Handled by A: data1\n" +
                "Set state: B\n" +
                "Handled by B: data2\n" +
                "Set state: C\n" +
                "Handled by C: data3\n" +
                "Set state: A\n" +
                "Handled by A: data4\n" +
                "Set state: B\n" +
                "Handled by B: data5\n" +
                "Set state: C\n" +

                "Set state: B\n" +
                "Handled by C\n" +
                "Set state: A\n" +
                "Handled by B\n" +
                "Set state: C\n" +
                "Handled by A\n" +
                "Set state: B\n" +
                "Handled by C\n" +
                "Set state: A\n" +
                "Handled by B\n", console.getOut());
    }
}
