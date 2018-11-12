package com.juja.io;

import jdk.internal.util.xml.impl.Input;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class TestIO {

    public void main() {
        String fileName = "src/main/resources/file2.txt";
       // methodDIS(fileName);
       // methodNIO(fileName);
       // methodScanner(fileName);
       // methodNIO2(fileName);
       // System.out.println(md5("Privet Vasya!"));
        System.out.println(md5ForFile(fileName));
    }

    private void methodScanner(String fileName) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(fileName));
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void methodDIS(String fileName) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            byte[] buffer = new byte[512];
            while (dis.available() != 0) {
                int count = dis.read(buffer);
                if (count > 0) {
                    //String line = buffer.toString();
                    System.out.println(String.valueOf(buffer));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void methodNIO(String filename) {
        try {
            List<String> list = Files.readAllLines(Paths.get(filename));
            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void methodNIO2(String filename) {
        try {
            Files.lines(Paths.get(filename)).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    protected String md5(String plainText) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(plainText.getBytes(Charset.forName("UTF-8")));
            messageDigest.digest();

             return Hex.encodeHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    protected String md5ForFile(String fileName) {
        try (InputStream input = new FileInputStream(fileName)){
            return DigestUtils.md5Hex(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
