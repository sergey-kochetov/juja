package com.juja.parsing;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserCSV {
    //bl2612-26993.csv
    //bl2612-26994.csv
    private static final String FILE_NAME = "D:\\test\\bl2612-26993.csv";

    private static List<String> data = new ArrayList<>();

    private static List<String> newData = new ArrayList<>();

    private static String day = "";
    private static String programName = "";
    private static String time = "";

    public static void main(String[] args) {
        readFile(FILE_NAME);

        newData = newLine(data);

        String newFileName = FILE_NAME.replace(".csv", "_new.csv");
        writeFile(newFileName);

        newData.stream().forEach(System.out::println);
    }

    public static List<String> newLine(List<String> data) {
        List<String> result = new ArrayList<>();
        try {
            for (String x : data) {

                String line = getNewLine(x);
                result.add(line);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(result.size());

        return result;
    }

    private static String getNewLine(String x) {
        String[] strs = x.split(",");
        if (strs.length < 30) {
            return x;
        }
        List<String> result = new ArrayList<>();

        boolean flag = false;

        if (!strs[1].equals("Program Name")) {
            if (!programName.isEmpty() && programName.equals(strs[1])) {
                flag = true;
            }
            programName = strs[1];
        }
        result.add(strs[1]);

        if (strs[2].contains("Comment")) {
            result.add(strs[2]);
        } else {
            String comment = "";
            if (!flag && !time.isEmpty()) {
                comment = deltaDate(strs[3].split(" ")[1], time);
            } else {
                if (!strs[3].split(" ")[1].equals(time)) {
                    comment =  deltaDate(strs[3].split(" ")[1], time);
                }

            }
            result.add(comment);
        }
        if (strs[3].contains("Time")) {
            result.add(strs[3]);
        } else {
            result.add(strs[3].split(" ")[1]);
            String currentDay = strs[3].split(" ")[0];
            if (!day.equals(currentDay) && !currentDay.equals("1900/01/01")  || day.trim().isEmpty()) {
                day = currentDay;
                String removeElement = result.remove(1);
                result.add(1,  day);
            }
        }
        if (strs[4].contains("Time")) {
            result.add(strs[4]);
        } else {
            result.add(strs[4].split(" ")[1]);
            time = strs[4].split(" ")[1];
        }
        result.add(strs[6]);
        result.add(strs[8]);
        result.add(strs[12]);
        result.add(strs[13]);
        result.add(strs[14]);
        return result.stream().collect(Collectors.joining(","));
    }

    public static String deltaDate(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            long millis = format.parse(start).getTime() -  format.parse(end).getTime();
            return String.format("+%02d:%02d:%02d",
                    //Hours
                    TimeUnit.MILLISECONDS.toHours(millis) -
                            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)),
                    //Minutes
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    //Seconds
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        } catch (Exception e) {
            return "";
        }



    }

    private static void writeFile(String newFileName) {
        try (FileWriter writer = new FileWriter(newFileName)){
            for(String str: newData) {
                writer.write(str + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            data = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.remove(0);
    }
}
