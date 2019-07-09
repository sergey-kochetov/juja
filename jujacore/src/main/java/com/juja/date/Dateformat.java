package com.juja.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dateformat {
    public static void main(String[] args) {
        Date date = new Date();

        System.out.println(getReadableDateWeekDay(date));
        System.out.println(getReadableDateTime(date));
        System.out.println(getReadableDateTimeShort(date));
        System.out.println(getReadableDateWithYear(date));
        System.out.println(getReadableTime(date));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS X");
        String formated = format.format(date);
        System.out.println("\nformatted: " + formated);
        try {
            Date parseDate = format.parse(formated);
            System.out.println(date.equals(parseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static String getReadableDateWeekDay(Date date) {
        SimpleDateFormat readableFormat = new SimpleDateFormat("dd MMMM, EEEE");
        return readableFormat.format(date);
    }

    private static String getReadableDateTime(Date date) {
        SimpleDateFormat readableFormat = new SimpleDateFormat("d MMMM H:mm", Locale.US);
        return readableFormat.format(date);
    }

    private static String getReadableDateTimeShort(Date date) {
        SimpleDateFormat readableFormat = new SimpleDateFormat("dd MMM 'at' H:mm", Locale.US);
        return readableFormat.format(date);
    }

    private static String getReadableDateWithYear(Date date) {
        SimpleDateFormat readableFormat = new SimpleDateFormat("dd-MM-yyyy");
        return readableFormat.format(date);
    }

    private static String getReadableTime(Date date) {
        SimpleDateFormat readableFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss X");
        return readableFormat.format(date);
    }
}
