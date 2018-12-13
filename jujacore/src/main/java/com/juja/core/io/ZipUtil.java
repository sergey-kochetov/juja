package com.juja.core.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {
    public static void main(String[] args) throws IOException {
        readZip();
    }

    private static void readZip() throws IOException {
        ZipFile zf = new ZipFile("../ziptest.zip");
        zf.stream().forEach(new MyZip(zf));

}
}

class MyZip implements Consumer<ZipEntry> {
    private ZipFile zipFile;

    public MyZip(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    @Override
    public void accept(ZipEntry zipEntry) {
        try {
            System.out.println("Entry: " + zipEntry.getName());
            if (!zipEntry.isDirectory()) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(zipFile.getInputStream(zipEntry)));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("------------");

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}