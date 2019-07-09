package com.juja.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        System.out.println();

        long millis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millis);
        System.out.println("minutes: " + calendar.get(Calendar.MINUTE));
        System.out.println("hours: " + calendar.get(Calendar.HOUR_OF_DAY));

        System.out.println("day of week: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("day of month: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("day of year: " + calendar.get(Calendar.DAY_OF_YEAR));

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println("yesterday: " + format.format(calendar.getTime()));

        System.out.println("is leap year 2019: " + isLeapYear(2019));
        System.out.println("is leap year 2020: " + isLeapYear(2020));

        System.out.println("start of week day: " + calendar.getFirstDayOfWeek());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        System.out.println("start of week date: " + format.format(calendar.getTime()));

        System.out.println("is dates of the same day: " + dateOfSameDay(Calendar.getInstance(), Calendar.getInstance()));
    }

    private static boolean dateOfSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    private static String isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.FEBRUARY, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 29 ? "yes" : "no";
    }

    private static Calendar calendarStartOfDay(Calendar dateTime) {
        Calendar calendar = (Calendar) dateTime.clone();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private static Calendar calendarStartOfMonth(Calendar dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
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
