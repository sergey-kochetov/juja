package com.juja.patterns.cor.atm;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MoneyStackTest {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private MoneyStack moneyStack;
    private int debt = 0;

    @Before
    public void setup() {
        System.setOut(new PrintStream(out));

        // given
        // тестируемы класс
        moneyStack = new MoneyStack(100);
        moneyStack.setNextStack(new MoneyStack(0) {
            @Override
            public void withdraw(int amount) {
                debt = amount;
            }
        });
    }
    @Test
    public void testWithDebt() {
        // when
        moneyStack.withdraw(250);
        // then
        assertEquals("2 x $100\r\n", out.toString());

        assertEquals(50, debt);
    }

    @Test
    public void testWithoutDebt() {
        // when
        moneyStack.withdraw(300);

        //then
        assertEquals("3 x $100\r\n", out.toString());
        assertEquals(0, debt);
    }

}