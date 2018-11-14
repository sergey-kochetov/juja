package com.juja.date;

import org.junit.Test;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SampleDateTest {
    private static final String DATE_FORMAT = "yyMMddHHmm";
    @Test
    public void testDateFormat() {
        String dateString = "1806101315";
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            assertEquals("Sun Jun 10 13:15:00 MSK 2018", dateFormat.parse(dateString).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDateFormatCurrent() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        // assertEquals("1811141813", dateFormat.format(System.currentTimeMillis()));
    }

    @Test
    public void testDateFormatCurrent2() {
        List<String> data = Arrays.asList(
                "1804010101 line1",
                "1803010101 line2",
                "1802010101 line3",
                "1801010101 line4");
        List<String> result = new ArrayList<>();
        data.forEach(line -> {
            String[] lineParts = line.split(" ");
            if (isDateAfterMark(lineParts[0])) {
                result.add(lineParts[1]);
            }
        });
            assertEquals("[line1, line2]", result.toString());
    }
    // какая дата больше
    public boolean isDateAfterMark(String dateString) {
        return LocalDateTime
                .parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT))
                .isAfter(LocalDateTime.of(2018, 3, 1, 0, 0));
    }

    @Test
    public void testTimeUnit() {
        // given
        String dateString = "1710110000";
        long diff = 0L;
        try {
            diff = TimeUnit.MILLISECONDS
                            .toDays(System.currentTimeMillis()) -
                    TimeUnit.MILLISECONDS
                            .toDays(new SimpleDateFormat(DATE_FORMAT).parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 400 дней 14-11-2018
        //assertEquals(400L, diff);
    }
}
