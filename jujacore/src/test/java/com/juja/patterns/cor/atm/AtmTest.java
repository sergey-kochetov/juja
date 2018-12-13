package com.juja.patterns.cor.atm;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AtmTest {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(out));
    }

    private Atm atm;

    @Test
    public void testDefaultConstructor_case1() {
        given.defaultAtm();

        given.when(186)
                .then("186 =\r\n" +
                "1 x $100\r\n" +
                "1 x $50\r\n" +
                "1 x $20\r\n" +
                "1 x $10\r\n" +
                "1 x $5\r\n" +
                "1 x $1\r\n" +
                "++++++++++++++\r\n");
    }

    private Given given = new Given();

    public class Given {
        public void defaultAtm() {
            atm = new Atm();
        }
        public void primeAtm() {
            atm = new Atm(1, 2, 5, 10, 15, 20, 25, 50, 100, 200, 300, 500, 1000, 2000, 5000);
        }

        public When when(int amount) {
            return new When(amount);
        }
    }

    public class When {
        private int amount;
        // это конструктор который мы за\фектор\метод\или
        private When(int amount) {
            this.amount = amount;
        }
        // это часть2 вызова when(часть1).then(часть2)
        // тут делается самое важное
        public void then(String... lines) {
            // when
            // делаем тестовый вызов
            atm.withdraw(amount);

            // then
            // собираем строчку
            String expected = getString(lines);

            assertEquals(expected, out.toString());
            // чистим буфер
            out.reset();
        }

        private String getString(String[] lines) {
            return Arrays.stream(lines).collect(Collectors.joining(","));
        }
        private String getString2(String[] lines) {
            String result = "";
            for (String line : lines) {
                result += line + "\r\n";
            }
            return result;
        }
    }

    @Test
    public void testDefaultConstructor_case3() {
        // given
        given.defaultAtm();

        given.when(1231)
                .then("1231 =\r\n" +
                "12 x $100\r\n" +
                "1 x $20\r\n" +
                "1 x $10\r\n" +
                "1 x $1\r\n" +
                "++++++++++++++\r\n");
    }

    @Test
    public void testConstructorWithParameters_case3() {
        // given
        given.primeAtm();

        // when then
        given.when(12322).then("12322 =\r\n" +
                "2 x $5000\r\n" +
                "1 x $2000\r\n" +
                "1 x $300\r\n" +
                "1 x $20\r\n" +
                "1 x $2\r\n" +
                "++++++++++++++\r\n");
    }

}