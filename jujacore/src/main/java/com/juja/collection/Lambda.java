package com.juja.collection;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class Lambda {
    public static void main(String[] args) {
        //methodFile();
        //methodList();

    }

    private static void methodList() {
        List<String> list = new ArrayList<>();
        for (int i = 10; i > 0 ; i--) {
            list.add(String.format("%02d", i));

        }
        list.sort((s1, s2) -> s1.compareTo(s2));
        for (String s : list) {
            System.out.println(s);
        }
    }

    private static void methodFile() {
        File src = new File(".");
        File[] files2 = src.listFiles(new MyFilter());
        File[] files1 = src.listFiles(File::isDirectory);
        for (File file : files1) {
            System.out.println(file);
        }
    }
}
class MyFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}
