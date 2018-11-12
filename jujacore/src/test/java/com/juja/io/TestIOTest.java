package com.juja.io;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TestIOTest {

    @Test
    public void shouldMD5_whenPrintEncode () {
        // given
        TestIO test = new TestIO();
        String text = "Hello World!";

        // when
        String expected = test.md5(text);

        // then
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", expected);
    }

    @Test
    public void shouldMD5ForFile_whenPrintEncodeForFile () {
        // given
        TestIO test = new TestIO();
        String fileName = "src/main/resources/file2.txt";
        Path pathName = Paths.get(fileName);

        // when
        String expected = test.md5ForFile(fileName);

        // then
        assertEquals("ae75b588da5d1ecdf72a39c0c82faec9", expected);
    }

}