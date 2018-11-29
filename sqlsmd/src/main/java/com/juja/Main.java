package com.juja;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String COMMAND_SAMPLE = "connect|sqlcmd|postgres|postgres";
    private static final int SPLIT = 4;

    public static void main(String[] args) throws Exception {
//
//        ProcessBuilder pb = new ProcessBuilder("c:\\Windows\\system32\\cmd.exe");
//        pb.redirectErrorStream(true);
//
//        Process p = pb.start();
//        try {
//            InputStream is = p.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is, "CP866");
//            BufferedReader br = new BufferedReader(isr);
//            OutputStream os = p.getOutputStream();
//            Writer writer = new PrintWriter(os);
//            writer.write("hellpppp...\n");
//            writer.flush();
//
//            String line;
//            while (true) {
//                Thread.sleep(100);
//
//                if (is.available() > 0) {
//
//                    char[] buff = new char[1024];
//                    int read = br.read(buff);
//                } else {
//                    String line2 = new Scanner(System.in).nextLine();
//                    writer.write(line2 + "\n");
//                    writer.flush();
//                }
//
//            }
//
//        } catch (IOException e) {
//
//        }
    }

}
