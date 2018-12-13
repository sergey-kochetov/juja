package com.juja.core.nio;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class Watching {

    public static void main(String[] args) throws IOException {
        Path dirPath = Paths.get("income");
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        WatchService watchService = FileSystems.getDefault().newWatchService();
        dirPath.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        while (true) {
            WatchKey key;

            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            for ( WatchEvent event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path filePath = ((WatchEvent<Path>) event).context();
                    System.out.println("New file =" + filePath + " was discovered");
                    System.out.println("Processing...");
                    long time2 = System.currentTimeMillis();

                    Stream<String> stream = Files.lines(filePath);
                    stream.forEach(System.out::println);
                    stream.close();

                    System.out.println("File =" + filePath + " was successfully processed in " +
                            (System.currentTimeMillis() - time2) + "ms");
                }
                if (event.kind() ==  StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("File =" + ((WatchEvent<Path>) event).context() + " was modified");
                }
                if (event.kind() ==  StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("File =" + ((WatchEvent<Path>) event).context() + " was deleted");
                }
            }
            if (!key.reset()) {
                break;
            }
        }
    }
}
