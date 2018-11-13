package com.juja.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;

public class testNIO {

    @Test
    public void test_newNIO() {
        // given
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("src/main/java/com/juja/nio"))) {
            for (Path filePath : stream) {
                String fileName = filePath.getFileName().toString();
                String line = String.format("Processed file: name=%s size=%d bytes\n", fileName, Files.size(filePath));
                System.out.println(line);
                Files.copy(filePath, Paths.get("src/main/java/com/juja/nio/income", fileName), StandardCopyOption.REPLACE_EXISTING);
                Files.write(Paths.get("log.txt"), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
