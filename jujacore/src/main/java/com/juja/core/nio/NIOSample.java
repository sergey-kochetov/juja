package com.juja.core.nio;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class NIOSample extends NIOAbstract {
    @Before
    public void setup2() throws IOException {
        resetFile("src/main/resources/test.txt", "line1\r\nline2\r\nline3\r\nline4");
        removeFile("src/main/resources/text2.txt");
    }

    private void removeFile(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));

    }

    private void resetFile(String path, String data) throws IOException {
        if (Files.notExists(Paths.get(path))) {
            Files.createFile(Paths.get(path));
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path));) {
            writer.write(data);
        }

    }
    //TODO ----------- File vs Path / Files -----------
    // робота с путями относительно root
    @Test
    public void testFilePath_relativeFromDrive() throws IOException {
        // give
        // слеш спереди = путь относительный
        // под винду и линукс "/"
        String stringPath = "/src/main/resources/test.txt";
        // when
        // создаем два конкурента
        Path path = Paths.get(stringPath);
        File file = new File(stringPath);
        // then
        // хоть api у них разные но с сылками они работаю одинаково
        assertEquals("D:\\src\\main\\resources\\test.txt", path.toAbsolutePath().toString());
        assertEquals(false, path.isAbsolute());

        assertEquals("D:\\src\\main\\resources\\test.txt", file.getAbsolutePath());
        assertEquals(false, file.isAbsolute());
    }
    // робота с путями относительно другой папки
    @Test
    public void testFilePath_relativeFromFolder() throws IOException {
        // give
        // нет слеша спереди
        // под винду и линукс "/"
        String stringPath = "src/main/resources/test.txt";
        // when
        // создаем два конкурента
        Path path = Paths.get(stringPath);
        File file = new File(stringPath);
        // then
        // хоть api у них разные но с сылками они работаю одинаково
        assertEquals("D:\\projects\\juja\\jujacore\\src\\main\\resources\\test.txt", path.toAbsolutePath().toString());
        assertEquals(false, path.isAbsolute());

        assertEquals("D:\\projects\\juja\\jujacore\\src\\main\\resources\\test.txt", file.getAbsolutePath());
        assertEquals(false, file.isAbsolute());
    }
    // робота с абсолютными путями
    @Test
    public void testFilePath_absolute() throws IOException {
        // give
        // абсолютный путь указывают явно
        String stringPath = "D:/projects/juja/jujacore/src/main/resources/test.txt";
        assertEquals("\\", File.separator);
        // when
        // создаем два конкурента
        Path path = Paths.get(stringPath);
        File file = new File(stringPath);
        // then
        // хоть api у них разные но с сылками они работаю одинаково
        assertEquals("D:\\projects\\juja\\jujacore\\src\\main\\resources\\test.txt", path.toAbsolutePath().toString());
        assertEquals(true, path.isAbsolute());

        assertEquals("D:\\projects\\juja\\jujacore\\src\\main\\resources\\test.txt", file.getAbsolutePath());
        assertEquals(true, file.isAbsolute());
    }
    /* Попробуем скопировать файл, проверить существует ли он или удалить
       Files -> Path copy(Path source, Path targe, CopyOption... options)
       Files -> boolean exists(Path path, LinkOption... options)
       Files -> boolean noExsist(Path path, LinkOption... options)
       Files -> void delete(Path path)
       Files -> boolean deleteIfExists(Path path)
    */
    @Test
    public void testFilePath_copyExistRemove() throws IOException {
        // given
        // готовимся к копированию
        String stringPath = "src/main/resources/test.txt";
        Path sourcePath = Paths.get(stringPath);
        File sourceFile = new File(stringPath);

        /*
        удалить файл sourceFile.delete() -> not SRP (Single responsibility principe)
        копирование делается так
         */
        Path targetPath = Paths.get(stringPath + "2");
        Files.copy(sourcePath, targetPath,
                /* У него есть опций - это vararg параметр
                    перезаписать существующий файл
                    но если мы копируем символическую линку, только она будет заменена */
                StandardCopyOption.REPLACE_EXISTING,
                // скопировать кроме всего прочего так же и аттрибуты в новый файл
                StandardCopyOption.COPY_ATTRIBUTES
                // переписать файл как атамарную операцию файловой системы
                // если такой поддержки нет - получить исключение
                // если опция есть, в процессе выполнения любой поток увидит файл
                // в папке назначения цеостным, когда копирование закончится
                // StandardCopyOption.ATOMIC_MOVE
        );
        // then
        // файл скапировался File
        File targetFile = new File(stringPath + "2");
        assertEquals(true, targetFile.exists());
        // файл скапировался Path
        assertEquals(true, Files.exists(targetPath));
        assertEquals(false, Files.notExists(targetPath));

        // подчистим за собой
        if (Files.exists(targetPath)) {
            Files.delete(targetPath);
        }
        // или еще проще
        Files.deleteIfExists(targetPath);
        //  а так бы мы делали раньше
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }
    //-----------------------------------------------
    /*  Files -> long copy(InputStream in, Path target, CopyOption... options)
        Files -> long copy(Path source, OutputStream out) */
    @Test
    public void testFilePath_copyTo() throws IOException {
        // given
        // получили ссылку на объект
        String stringPath = "src/main/resources/test.txt";
        Path path = Paths.get(stringPath);
        // мы создадим демо строчку и два InputStream/OutputStream
        // для превращение потоков в/из массив/в
        String string = new String("Hello java.nio!").intern();
        ByteArrayInputStream bis = new ByteArrayInputStream(string.getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // when
        Files.deleteIfExists(path);
        // копируем из лююбого InputStream в Path
        Files.copy((InputStream) bis, path);
        // then
        // файл существует и в нем то, что нам надо
        Files.exists(path);
        assertEquals("[Hello java.nio!]", getFileContent(path));
        // when
        // копируем обратно из Path в любой OutputStream
        Files.copy(path, (OutputStream) bos);
        // then
        // получили в ByteArrayOutputStream результат
        assertEquals("Hello java.nio!", new String(bos.toByteArray()));
        // чистим  за собой
        Files.deleteIfExists(path);

    }

    private String getFileContent(Path path) {
        List<String> list = new ArrayList<>();
        try( BufferedReader  reader = new  BufferedReader(new FileReader(path.toFile()))) {
            while (reader.ready()) {
                list.add(reader.readLine());
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return Arrays.toString(list.toArray());
    }
    //----------------------------------------------------
    @Test
    public void testFiles_newIOStream() throws IOException {
        // given
        // получили ссылку на файл
        String stringPath ="src/main/resources/test.txt";;
        Path path = Paths.get(stringPath);
        // when
        // берем InputStream для чтения
        InputStream is = Files.newInputStream(path,
                // открытие файла для чтения
                StandardOpenOption.READ
                // открытие файла для записи
                // StandardOpenOption.WRITE,
                // если файл открыт для записи,
                // то байты будут добавляться
                // в конец файла, а не в начало
                // StandardOpenOption.APPEND,
                // если файл уже существует и открывается для записи,
                // то его длина устанавливается в 0.
                // StandardOpenOption.TRUNCATE_EXISTING
                // если файла нет создать новый
                // StandardOpenOption.CREATE,
                // а если есть - сгенирировать ошибку
                // StandardOpenOption.CREATE_NEW,
                // Удалять при закрытии фала либа когда JVM закончит работу
                // StandardOpenOption.DELETE_ON_CLOSE,
                // создание сжатого файла средствами файловой системы (NTFS)
                // StandardOpenOption.SPARSE,
                // каждое обнавление файла синхронно пишется на диск
                // StandardOpenOption.DSYNC,
                // каждое обновление файла и метаданых синхронно пишется на диск
                // StandardOpenOption.SYNC,
                // не переходить по символическим ссылкам
                //LinkOption.NOFOLLOW_LINKS
                );
        // then
        // теперь прочитаем все линии из файла
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.mark(10000); // запомнили позицию
        List<String> list = reader.lines().collect(Collectors.toList());
        assertEquals("[line1, line2, line3, line4]", list.toString());

        // when
        // возмем теперь OutputStream из Path
        OutputStream os = Files.newOutputStream(path,
                StandardOpenOption.WRITE,
                // а попробуем заремарить эту строчку
                // и сделать так, чтобы line5 добавилась в начало файла
                StandardOpenOption.APPEND
                );
        // then
        // и допишем туда чтот интересное
        OutputStreamWriter writer = new OutputStreamWriter(os);
        writer.write("\r\nline5");
        // и снова все прочитаем
        reader.reset();
        list = reader.lines().collect(Collectors.toList());
        assertEquals("[line1, line2, line3, line4]", list.toString());
        //запишем данные
        writer.flush();
        reader.reset();
        list = reader.lines().collect(Collectors.toList());
        assertEquals("[line1, line2, line3, line4, line5]", list.toString());

        // закрываем все потоки
        reader.close();
        writer.close();
    }
    //---------------------------------------------------
    @Test
    public void testFilePath_parts() throws IOException {
        // given
        // получили ссылку на файл
        String stringPath = "src/main/resources/test.txt";
        Path path = Paths.get(stringPath);
        // when then
        // получаем имя файла
        Path fileName = path.getFileName();
        assertEquals("test.txt", fileName.toString());
        // when then
        // получаем родительскую папку в которой файл
        Path parent = path.getParent();
        assertEquals("src\\main\\resources", parent.toString());
        // when then
        // получаем прородительскую папку в которой файл
        Path parent2 = parent.getParent();
        assertEquals("src\\main", parent2.toString());
        // when then
        // Path - то интервейс и он наследует Iterable<Part>
        List<String> parts = new ArrayList<>();
        for (Path part : path) {
            parts.add(part.toString());
        }
        assertEquals("[src, main, resources, test.txt]", parts.toString());
        // when then
        // можно получить количество частей
        //для абсолютно и относительной
        assertEquals(4, path.getNameCount());
        assertEquals(7, path.toAbsolutePath().getNameCount());
        // when then
        // можно получить любцю часть по индексу
        assertEquals("src", path.getName(0).toString());
        assertEquals("main", path.getName(1).toString());
        assertEquals("resources", path.getName(2).toString());
        assertEquals("test.txt", path.getName(3).toString());
        // when then
        // можно получить промежуточные части по индексу
        assertEquals("main\\resources", path.subpath(1, 3).toString());
    }
    //---------------------------------------------------------
    // метод resolve служит объединению двух относительных путей
    // Path -> Path resolve(Path other)
    @Test
    public void testFilePath_resolve() throws IOException {
        // given
        Path path1 = Paths.get("src/main");
        Path path2 = Paths.get("resources/text.txt");
        // when then
        // объединим два пути
        Path path = path1.resolve(path2);
        assertEquals("src\\main\\resources\\text.txt", path.toString());
        // порядок важен
        path = path2.resolve(path1);
        assertEquals("resources\\text.txt\\src\\main", path.toString());
    }
    //метод resolveSibling служит замене части исходно пути частью из другого
    // полезно при переименовании файлов или папок
    //      Path -> Path resolveSibling(Path other)
    //      Path -> Path resolveSibling(String other)
    @Test
    public void testFilePath_resolveSibling() throws IOException {
        // given
        Path path = Paths.get("src/main/resources/text.txt");
        // when then
        Path replace1 = Paths.get("test2.txt");
        Path result1 = path.resolveSibling(replace1);
        assertEquals("src\\main\\resources\\test2.txt", result1.toString());
    }
    //--------------------------------------------------
    // есть различные способы на проверку созпадения и равенство
    //  Path -> boolean startWith(Path other)
    //  Path -> boolean startWith(String other)
    //  Path -> boolean endWith(Path other)
    //  Path -> boolean endWith(String other)
    //  Path -> boolean equals(Object other)
    //  Path -> int compareTo(Path other)
    @Test
    public void testFilePath_comparing() throws IOException {
        // given
        Path path = Paths.get("src/main/resources/text.txt");

        // when then
        // можно проверить начало пути
        assertEquals(true, path.startsWith(Paths.get("src/main")));
        assertEquals(true, path.startsWith("src\\main\\resources"));
        assertEquals(false, path.startsWith(Paths.get("other/main")));
        // when then
        // можно проверить конец пути
        assertEquals(true, path.endsWith(Paths.get("resources/text.txt")));
        assertEquals(true, path.endsWith("main\\resources\\text.txt"));
        // when then
        // можно проверить equals
        assertEquals(true, path.equals(Paths.get("src/main/resources/text.txt")));
        assertEquals(false, path.equals("src/main/resources/text.txt"));
        // when then
        // можно проверить Comparable
        assertEquals(0, path.compareTo(Paths.get("src/main/resources/text.txt")));
        assertEquals(true, path.compareTo(Paths.get("src/main/resources/textzz.txt")) < 0);

    }

}
