package com.juja.io;

import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class IOSample extends AbstractSample {
    // -------------- InputStream ---------------
    @Test
    public void testInputStreamAbstraction_readAll_skip() throws IOException {
        //given
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // анонимная реализация InputStream
        InputStream input = new InputStream() {
            // курсор, позиция в данных с которой мы прочитаем
            private int index = 0;
            @Override
            public int read() throws IOException {
                if (index == data.length) {
                    // если достигли конца вернем -1
                    return -1;
                }
                return data[index++];
            }
        };
        // when
        byte[] out = new byte[3];
        // прочитаем
        int count = input.read(out);
        // then
        // что прочитали
        assertEquals("[1, 2, 3]", Arrays.toString(out));
        // сколько байт прочитали
        assertEquals(3, count);

        // when
        // пропустить 2 байта
        long skipCount = input.skip(2);
        assertEquals(2, skipCount);

        // возьмем приемник поболее и тоже прочитаем
        out = new byte[10];
        count = input.read(out);
        // then
        // хотя мы хотели прочитать 10, но фактически прочитали только 5
        assertEquals("[6, 7, 8, 9, 10, 0, 0, 0, 0, 0]", Arrays.toString(out));
        assertEquals(5, count);
    }

    @Test
    public void testInputStreamAbstraction_readPart() throws IOException {
        // given
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        InputStream input = new InputStream() {
            private int index = 0;
            @Override
            public int read() throws IOException {
                if (index == data.length) {
                    return -1;
                }
                return data[index++];
            }
        };
        // when
        // результат
        byte[] out = new byte[10];
        Arrays.fill(out, (byte) 33);
        // прочитаем только часть, в определенную позицию out и заданной длинной
        int count = input.read(out, 4, 2);
        // then
        assertEquals("[33, 33, 33, 33, 1, 2, 33, 33, 33, 33]", Arrays.toString(out));
        assertEquals(2, count);
    }

    @Test
    public void testInputStreamAbstract_mark_reset() throws IOException {
        // given
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // реализация с поддержкой меток
        InputStream input = new InputStream() {
            private int index = 0;
            private int readlimit = 0;
            private int countRead = 0;
            private int mark = 0;
            private boolean marked = false;
            // мы должны переопределить метод
            // и сообщить, что мы поддерживаем этц фичу
            @Override
            public boolean markSupported() {
                return true;
            }
            // перемещает метку в текущую точку входного потока,
            // которая остается корректной до тех пор,
            // пока не будет прочитано readlimit байт

            @Override
            public synchronized void mark(int readlimit) {
                this.readlimit = readlimit;
                countRead = 0;
                marked = true;
                mark = index;
            }

            @Override
            public synchronized void reset() throws IOException {
                // если мы не оставляли маркер
                // или с момента маркировки прочитали байт больше,
                // чем было указано в readlimit
                if (!marked || countRead > readlimit) {
                    throw new IOException("reset");
                }
                // иначе возвращаем маркер в ту позицию,
                // которая была в момент вызова mark
                index = mark;
            }
            // если бы мы эту фичу не поддерживали - markSupported() return false
            // то default реализация reset прокинула бы исключение

            @Override
            public int read() throws IOException {
                if (index == data.length) {
                    return -1;
                }
                countRead++; // запоминаем сколько было фактически прочитано
                return data[index++];
            }
        };
        // when
        byte[] out = new byte[5];
        int count = input.read(out);
        // then
        assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(out));
        assertEquals(5, count);
        // when
        // посчитаем маркер без ограничений
        input.mark(Integer.MAX_VALUE);
        // прочитаем еще 5 байт
        count = input.read(out);
        // then
        assertEquals("[6, 7, 8, 9, 10]", Arrays.toString(out));
        assertEquals(5, count);
        // when
        // вернемя назад
        input.reset(); // откатывает предыдущее чтение
        // и снова прочитаем 5 байт
        count = input.read(out);

        // then
        assertEquals("[6, 7, 8, 9, 10]", Arrays.toString(out));
        assertEquals(5, count);
    }
    // реализация с available
    @Test
    public void testInputStreamAbstraction_available() throws IOException {
        // given
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // базавая реализация с поддержкой available
        InputStream input = new InputStream() {
            private int index = 0;

            @Override
            public int available() throws IOException {
                return data.length - index;
            }

            @Override
            public int read() throws IOException {
                if (index == data.length) {
                    return -1;
                }
                return data[index++];
            }
        };
        // when
        byte[] out = new byte[5];
        int count = input.read(out);
        // then
        assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(out));
        assertEquals(5, count);
        // when
        // посмотрим сколько нам осталось
        int available = input.available();
        assertEquals(10, available);
        // прочитаем еще 5
        count = input.read(out);
        // then
        assertEquals("[6, 7, 8, 9, 10]", Arrays.toString(out));
        assertEquals(5, count);
        // when
        // и сновапосмотрим сколько осталось
        available = input.available();
        assertEquals(5, available);
        // последние 5 байт
        count = input.read(out);
        // then
        assertEquals("[11, 12, 13, 14, 15]", Arrays.toString(out));
        assertEquals(5, count);
        // и ничего не осталось
        available = input.available();
        assertEquals(0, available);
    }
    @Test
    public void testInputStreamIntegerToByteCrunch() throws IOException {
        // given
        int[] data = new int[]{258, 257, 256, 255, 254, 253, 252,
                6, 5, 4, 3, 2, 1, 0, -1, -2, -3};
        InputStream input = new InputStream() {
            private int index = 0;
            @Override
            // метод возвращает int хотя мы работаем с byte
            // хочешь вернуть данные - верни 0...255 (int)
            // если надо вернуть признак конца файла -  верни -1
            public int read() throws IOException {
                if (index == data.length) {
                    return -1;
                }
                return data[index++];
            }
        };
        // when
        // возьмем вместительный контейнер и заполним его маркерами пустого места
        byte[] out = new byte[data.length];
        Arrays.fill(out, (byte) 33);
        input.read(out);
        // then
        assertEquals(
                "[2, 1, 0, -1, -2, -3, -4, " +
                        "6, 5, 4, 3, 2, 1, 0, 33, 33, 33]", Arrays.toString(out));
        // костыль сработал и -1 закончил запись
    }
    // ---------------------ByteArrayInputStream---------------------
    @Test
    public void testByteArrayInputStream() throws IOException {
        // given
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // реализация с простой поддержкой меток
        InputStream input = new ByteArrayInputStream(data);
        // when
        byte[] out = new byte[5];
        int count = input.read(out);
        // then
        assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(out));
        assertEquals(5, count);
        // when
        // посчитаем маркер без ограничений
        input.mark(Integer.MAX_VALUE);
        out = new byte[10];
        // прочитаем еще 10 байт
        count = input.read(out);
        // then
        assertEquals("[6, 7, 8, 9, 10, 11, 12, 13, 14, 15]", Arrays.toString(out));
        assertEquals(10, count);
        // when
        // вернемя назад
        input.reset(); // откатывает предыдущее чтение
        out = new byte[3];
        count = input.read(out);
        // then
        assertEquals("[6, 7, 8]", Arrays.toString(out));
        assertEquals(3, count);
        // when
        input.skip(2); // пропустили два
        count = input.read(out);
        // then
        assertEquals("[11, 12, 13]", Arrays.toString(out));
        assertEquals(3, count);
    }
    // --------------------OutputStream---------------------
    @Test
    public void testOutputStreamAbstraction_writeAll() throws IOException {
        // given
        // буффер куда будет производится запись
        byte[] data = new byte[15];
        Arrays.fill(data, (byte) 33);

        OutputStream output = new OutputStream() {
            // курсор, позиция
            private int index = 0;
            @Override
            public void write(int b) throws IOException {
                // data[index++] = (byte) (b & 0x000000ff);
                data[index++] = (byte) b;
            }
        };
        // when
        byte[] out = new byte[]{-128, -127, -3, -2, -1, 0, 1, 2, 3, 126, 127};
        // и пишем в стрим
        output.write(out);
        // then
        assertEquals("[-128, -127, -3, -2, -1, " +
                "0, 1, 2, 3, 126, 127, 33, 33, 33, 33]", Arrays.toString(data));
    }
    // --------------------ByteArrayOutputStream---------------------
    @Test
    public void testByteArrayOutputStream_write() throws IOException {
        // given
        int size = 100;
        ByteArrayOutputStream output = new ByteArrayOutputStream(size);

        // when
        byte[] out = new byte[]{-128, -127, -3, -2, -1, 0, 1, 2, 3, 126, 127};
        // и пишем в стрим
        output.write(out);
        // then
        assertEquals("[-128, -127, -3, -2, -1, 0, 1, 2, 3, 126, 127]",
                Arrays.toString(output.toByteArray()));
    }

    @Test
    public void testByteArrayOutputStream_reset() throws IOException {
        // given
        // по умолчанию 32
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        // when
        byte[] out = new byte[]{1, 2, 3, 4, 5};
        output.write(out);

        // then
        assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(output.toByteArray()));
        assertEquals(5, output.size());

        // when
        // а потом ресетим
        output.reset();
        out = new byte[]{6, 7, 8};
        output.write(out);

        // then
        assertEquals("[6, 7, 8]", Arrays.toString(output.toByteArray()));
        assertEquals(3, output.size());
    }

    // --------------------Reader--------------------> char
    @Test
    public void testReader_read() throws IOException {
        // given
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        // анонимная реализация абстракции Reader
        Reader input = new Reader() {
            // курсор с которого мы начинаем читать
            private int index = 0;
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                // проверки --> которых нет
                System.arraycopy(data, index, cbuf, off, len);
                index += len;
                return len;
            }

            @Override
            public void close() throws IOException {}
        };
        // when
        char[] out = new char[3];
        int count = input.read(out);

        // then
        assertEquals("[a, b, c]", Arrays.toString(out));
        assertEquals(3, count);

        // when
        // пропустим 2 байта
        long skipCount = input.skip(2); // =2
        assertEquals(2, skipCount);
        count = input.read(out);

        // then
        assertEquals("[f, g, h]", Arrays.toString(out));
        assertEquals(3, count);
    }
    //----------------CharArrayReader--------------
    @Test
    public void testCharArrayReader() throws IOException {
        // given
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        CharArrayReader reader = new CharArrayReader(data, 0, data.length);

        // when
        char[] out = new char[3];
        int count = reader.read(out);

        // then
        assertEquals("[a, b, c]", Arrays.toString(out));
        assertEquals(3, count);

        // when
        // пропустим 2 байта
        long skipCount = reader.skip(2); // =2
        assertEquals(2, skipCount);
        count = reader.read(out);

        // then
        assertEquals("[f, g, h]", Arrays.toString(out));
        assertEquals(3, count);
    }
    @Test
    public void testCharArrayReader_close() throws IOException {
        // given
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        CharArrayReader reader = new CharArrayReader(data, 0, data.length);
        // закрываем стрим
        reader.close();
        // считывать данные из закрытого стрима нельзя
        try {
            // when
            char[] out = new char[3];
            reader.read(out);
            fail();
        } catch (IOException e) {
            // then
            assertEquals("Stream closed", e.getMessage());
        }
    }
    @Test
    public void testByteArrayInputStream_close() throws IOException {
        // given
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        //закрываем стрим
        input.close();

        // when
        // пишем в якобы закрытый стрим
        byte[] out = new byte[3];
        int count = input.read(out);

        // then
        // все норм
        assertEquals("[1, 2, 3]", Arrays.toString(out));
        assertEquals(3, count);
    }
    //-----------------Writer----------------
    @Test
    public void testWriter_writeAll() throws IOException {
        // given
        char[] data = new char[12];
        Arrays.fill(data, '-');
        //анонимная реализация Writer
        Writer writer = new Writer() {
            // курсор
            private int index = 0;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                // д.б. проверки которых нет))
                System.arraycopy(cbuf, off, data, index, len);
                index +=len;
            }
            // метод промывки всех потоков в том числе и этот
            @Override
            public void flush() throws IOException {
                // пока игнорим
            }

            @Override
            public void close() throws IOException {
                // пока игнорим
            }
        };
        // when
        char[] out = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        // пишем в стрим
        writer.write(out);  // == writer.write(out, 0, out.length);
        // then
        assertEquals("[a, b, c, d, e, f, g, h, i, -, -, -]", Arrays.toString(data));
    }
    @Test
    public void testWriter_otherMetods() throws IOException {
        // given
        char[] data = new char[40];
        Arrays.fill(data, '-');
        Writer writer = new Writer() {
            // курсор
            private int index = 0;
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                // д.б. проверки которых нет))
                System.arraycopy(cbuf, off, data, index, len);
                index +=len;
            }
            // метод промывки всех потоков в том числе и этот
            @Override
            public void flush() throws IOException {
                // пока игнорим
            }

            @Override
            public void close() throws IOException {
                // пока игнорим
            }
        };

        // when
        // пишем в стрим
        // интежер
        writer.write(456);
        // char
        writer.append('w');
        // строки not-null
        writer.write("Hello Wrold!");
        writer.write("Мама мыла раму!", 5, 4); //offset-length
        // CharSequence реализации
        writer.append("Hello Wrold!");
        writer.append("Мама мыла раму!", 5, 9); // start-end
        // and null
        writer.append(null);
        assertEquals("[ǈ, " +
                "w, " +
                "H, e, l, l, o,  , W, r, o, l, d, !, " +
                "м, ы, л, а, " +
                "H, e, l, l, o,  , W, r, o, l, d, !, " +
                "м, ы, л, а, " +
                "n, u, l, l, " +
                "-, -]", Arrays.toString(data));
    }
    //------------InputStreamReader------------
    // это мост между InputStream и Reader
    // позволяет прочитать любой byte поток(который принимает в констукор)
    // User <- Reader <- InputStream <- DataSource
    @Test
    public void testInputStreamReader() throws IOException {
        // given
        byte[] data = new byte[]{-48, -97, -47, -128, -48, -72, -48, -78, -48, -75, -47, -126, 32, -48,
                -100, -48, -72, -47, -128, 33};
        InputStream input = new ByteArrayInputStream(data);
        Reader reader = new InputStreamReader(input, "UTF-8");
        //Reader reader1 = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        // when
        char[] buffer = new char[11];
        reader.read(buffer);
        // then
        assertEquals("[П, р, и, в, е, т,  , М, и, р, !]", Arrays.toString(buffer));
    }
    // --------------OutputStreamWriter--------------
    @Test
    public void testOutputStreamWriter() throws IOException {
        // given
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(output, "UTF-8");
        //Writer writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        // when
        char[] buffer = new char[] {'П', 'р', 'и', 'в', 'е', 'т', ' ', 'М', 'и', 'р', '!'};
        writer.write(buffer);
        // нужно дернуть flush для обнавления
        writer.flush();

        // then
        assertEquals("[-48, -97, -47, -128, -48, -72, -48, -78, -48, -75, -47, -126, " +
                "32, -48, -100, -48, -72, -47, -128, 33]", Arrays.toString(output.toByteArray()));
    }
    // -------------File---------------
    // File - ссылка на реальный или несуществующий файл или директорию
    @Test
    public void testFile() throws IOException {
        // given
        File file = new File("src/main/resources/file.txt");
        // when then
        //существует ли файл
        if (file.exists()) {
            // файл можно переименовать
            boolean rename = file.renameTo(new File("src/main/resources/file2.txt"));
            // удалить файл
            boolean delete = file.delete();
        }
        // можно удалить когда потушится JVM
        file.deleteOnExit();
        // можно создать
        file.createNewFile();
        assertTrue(file.exists());
        // может ли быть прочитан
        assertTrue(file.canRead());
        // а записать
        assertTrue(file.canWrite());

        file.setReadOnly();
        assertFalse(file.canWrite());
        // это директория?
        assertFalse(file.isDirectory());
        // это файл?
        assertTrue(file.isFile());
        // скрытый ли файл
        assertFalse(file.isHidden());
        // можно узнать дату модификации
        assertTrue(file.lastModified() > 0);
        // а можно ее установить в лонг
        file.setLastModified(Calendar.getInstance().getTimeInMillis());
        // можно узнать размер файла
        assertEquals(0, file.length());
        // имя файла
        assertEquals("file.txt", file.getName());
        // папка в которой файл лежит
        assertEquals("src\\main\\resources", file.getParent());
        // получение родительской папки под видом File
        // File parentFile = file.getParentFile();
        assertEquals("src\\main\\resources", file.getParentFile().toString());
        // получение пути файла
        assertEquals("src\\main\\resources\\file.txt", file.getPath());
        // получить абсолютный путь
        assertTrue(file.getAbsoluteFile().isAbsolute());
        // получение пути на файл
        Path path = file.toPath();
        assertEquals("src\\main\\resources\\file.txt", path.toString());
        // получение ссылки под видом URI
        URI uri = file.toURI();
        // получение ссылки под видом URL(deprecated)
        URL url = file.toURL();

    }
    // -------FileInputStream / FileOutputStream-----------
    @Test
    public void testFileInputStream_FileOutputStream() throws IOException {
        // given
        // осздаем пустой файл для работы в этом тексте
        File file = createNewFile("src\\main\\resources\\data.dat");
        // открываем поток для записи
        FileOutputStream out = new FileOutputStream(file);
        // осторожно пробуем записать
        try {
            out.write(new byte[]{-48, -97, -47, -128, -48, -72, -48, -78, -48, -75, -47, -126,
                    32, -48, -100, -48, -72, -47, -128, 33});
            // есть и другие возможности
            // получить SeekablebyteChannel для работы с файлом
            FileChannel channel = out.getChannel();
            // получить FileDescriptor открытого в OS файла
            FileDescriptor fileDescriptor = out.getFD();

        } finally {
            out.close();
        }
        // после чего мы можем прочитать содержимое файла с помощью
        FileInputStream in = new FileInputStream(file);
        // пробуем прочитать
        try {
            byte[] bytes = new byte[in.available()];
            // читаем
            int read = in.read(bytes);

            assertEquals(read, bytes.length);
            assertEquals("Привет Мир!", new String(bytes, "UTF-8"));
        } finally {
            in.close();
        }
    }

    private File createNewFile(String pathName) throws IOException {
        File file = new File(pathName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        file.deleteOnExit();
        return file;
    }

    @Test
    public void testFileReader_FileWriter() throws IOException {
        // given
        // создаем пустой файл для работы в этом тесте
        File file = createNewFile("src\\main\\resources\\data.dat");
        // when
        // открываем поток для записи
        FileWriter out = new FileWriter(file);
        try {
            out.write("Привет Мир!".toCharArray());
        } finally {
            out.close();
        }
        // then
        FileReader in = new FileReader(file);
        try {
            char[] chars = new  char[1000];
            int read = in.read(chars);

            assertEquals(11, read);
            assertEquals("Привет Мир!", new String(chars, 0, read));
        } finally {
            in.close();
        }
    }
    // --------------RandomAccesFile-------------
    @Test
    public void testRandomAccesFile() throws IOException {
        // given
        // создаем пустой файл для работы в этом тесте
        File file = createNewFile("src\\main\\resources\\data.dat");
        // открываем его
        String mode = "rws";
        // "r" - read only - только чтение файла любая комманда записи вызовет исключение
        // "rw" - read write, чтение-запись, если файл не существует его создадут
        // "rws" - "rw" + каждая запись в file или metadata будет сразу уходить на диск
        // "rwd" - "rw" + каждая запись в file будет сразу уходить на диск
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, mode);
        // when
        try {
            // пишем
            byte[] bytes = "Привет Мир!".getBytes("Cp866");
            randomAccessFile.write(bytes);
            // курсор в конце файла
            long cursor = randomAccessFile.getFilePointer();
            assertEquals(11, cursor);
            // смещаем его в нужную нам позицию
            randomAccessFile.seek(3);
            // читаем 6 байт с этого места
            byte[] buffer = new byte[3];
            int read = randomAccessFile.read(buffer);

            assertEquals(3, read);
            assertEquals("вет", new String(buffer, "Cp866"));
        } finally {
            randomAccessFile.close();
        }

    }
    // ----------StreamTokenizer---------
    @Test
    public void testStreamTokenizer() throws IOException {
        // given
        Reader reader = new StringReader("Привет 42 мир!");
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        // when
        List<String> tokens = new LinkedList<>();
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                tokens.add("Number: " + tokenizer.nval);
            } else if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                tokens.add("Word: " + tokenizer.sval);
            } else if (tokenizer.ttype == '!') {
                tokens.add("End of Line");
            }
        }
        // then
        assertEquals("[Word: Привет, Number: 42.0, Word: мир, End of Line]", tokens.toString());
    }

    // -------ObjectInputStream / ObjectOutputStreaem---------
    @Test
    public void testObjectInputStream_ObjectOutputStreaem() throws IOException, ClassNotFoundException {
        // given
        // есть какие-то данные...
        Object[] data = new Object[]{
                Math.E,
                "Привет Мир!",
                42,
                true,
                new Point(23, 45)
        };
        // when
        // создаем объекты для сериализации
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        // ObjectOutputStream работает на каком-то внешнем OutputStream
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutput);
        // пишем объект
        out.writeObject(data);
        // получаем массив байтов
        byte[] bytes = byteArrayOutput.toByteArray();

        // then
        assertEquals("[-84, -19, 0, 5, 117, 114, 0, 19, 91, 76, 106, 97, 118," +
                        " 97, 46, 108, 97, 110, 103, 46, 79, 98, 106, 101, 99, 116, 59, " +
                        "-112, -50, 88, -97, 16, 115, 41, 108, 2, 0, 0, 120, 112, 0, 0, 0, " +
                        "5, 115, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 68," +
                        " 111, 117, 98, 108, 101, -128, -77, -62, 74, 41, 107, -5, 4, 2, 0, 1, " +
                        "68, 0, 5, 118, 97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, " +
                        "46, 108, 97, 110, 103, 46, 78, 117, 109, 98, 101, 114, -122, -84, -107, " +
                        "29, 11, -108, -32, -117, 2, 0, 0, 120, 112, 64, 5, -65, 10, -117, 20, " +
                        "87, 105, 116, 0, 20, -48, -97, -47, -128, -48, -72, -48, -78, -48, -75, " +
                        "-47, -126, 32, -48, -100, -48, -72, -47, -128, 33, 115, 114, 0, 17, 106, " +
                        "97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 110, 116, 101, 103, 101, 114, " +
                        "18, -30, -96, -92, -9, -127, -121, 56, 2, 0, 1, 73, 0, 5, 118, 97, 108, 117, " +
                        "101, 120, 113, 0, 126, 0, 3, 0, 0, 0, 42, 115, 114, 0, 17, 106, 97, 118, 97, " +
                        "46, 108, 97, 110, 103, 46, 66, 111, 111, 108, 101, 97, 110, -51, 32, 114, -128, " +
                        "-43, -100, -6, -18, 2, 0, 1, 90, 0, 5, 118, 97, 108, 117, 101, 120, 112, 1, 115, " +
                        "114, 0, 14, 106, 97, 118, 97, 46, 97, 119, 116, 46, 80, 111, 105, 110, 116, -74, " +
                        "-60, -118, 114, 52, 126, -56, 38, 2, 0, 2, 73, 0, 1, 120, 73, 0, 1, 121, 120, " +
                        "112, 0, 0, 0, 23, 0, 0, 0, 45]",
                Arrays.toString(bytes));
        // when
        // создаем объекты для десериализации
        InputStream byteArrayInput = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(byteArrayInput);
        // читаем объекты
        Object object = in.readObject();
        // then
        // вернули исходник
        assertEquals("[2.718281828459045, " +
                        "Привет Мир!, " +
                        "42, " +
                        "true, " +
                        "java.awt.Point[x=23,y=45]]",
                Arrays.toString((Object[]) object));

    }
    // ----------------DataInputStream / DataOutputStream----------
    @Test
    public void testDataInputStream_DataOutputStream() throws IOException {
        // given
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(arrayOut);
        // when
        out.writeInt(42);
        out.writeBoolean(true);
        out.writeUTF("Привет Мир!");
        out.writeDouble(Math.PI);
        // a это нельзя out.writeObject(new Point(23, 45));
        out.flush();

        byte[] bytes = arrayOut.toByteArray();
        InputStream arrayIn = new ByteArrayInputStream(bytes);
        DataInputStream in = new DataInputStream(arrayIn);

        // then
        assertEquals(42, in.readInt());
        assertEquals(true, in.readBoolean());
        assertEquals("Привет Мир!", in.readUTF());
        assertEquals(Math.PI, in.readDouble(), 0.0);
        // это нельзя asserEquals(new Point(23, 45), in.readObject());
    }

    // -------------FilterInputStream / FilterOutputStream-----------
    @Test
    public void testFilterInputStream_FilterOutputStream() throws IOException {
        // given
        // наши пароль и то что мы собрались запрятать
        String pass = "password";
        String toEncrypt = "Привет Мир!";
        // настраиваем наш ByteArrayInputStream -
        // он будет читать массив байтов незашифрованой строки
        byte[] bytes = toEncrypt.getBytes("UTF-8");
        InputStream arrayIn = new ByteArrayInputStream(bytes);
        // декорируем его в EncodeInputeStream - это наследник фильтра
        // а значит он может принимтаь любой другой InputStream
        // и делать часть работ прежде/после/вместо декорируемого InputStream
        // в данном случае он будет быстро подменять байт за байтом
        InputStream encIn = new EncoderInputStream(arrayIn, pass);
        // настраиваем наш ByteArrayInputStream -
        // он будет сохранять результат в массив
        OutputStream arrayOut = new ByteArrayOutputStream();
        // декорируем его в EncodeOutputStream - это наследник фильтра
        // но уже другого FilterOutputStream,
        // а значит он может принимать любой другой OutputStream
        // и делать часть работ прежде/после/вмесо декорируемого OutputStream
        // в данном случае он будет быстро подменять байт за байтом
        OutputStream encOut = new EncoderOutputStream(arrayOut, pass);

        // when
        // читаем buffer
        byte[] buffer = new byte[100];
        int read = encIn.read(buffer);
        assertEquals(20, read);
        String encrypted = new String(buffer, 0, read, "UTF-8");
        assertEquals("K\u0004J\u001BK#K)K.J\u0019�K\u0007K#J\u001B�", encrypted);

        // передаем данные в связку
        // EncryptData -> EncodeOutputStream -> ByteArrayOutputStream -> toString
        encOut.write(buffer, 0, read);

        // then
        String actual = new String(((ByteArrayOutputStream) arrayOut).toByteArray(), "UTF-8");
        assertEquals("Привет Мир!", actual);
    }
    static class XOREncoder {
        private long seed;
        public XOREncoder(long seed) {
            this.seed = seed;
        }
        public int encode(int b) {
            return (int) (b ^ seed);
        }
    }
    static class EncoderOutputStream extends FilterOutputStream {
        private XOREncoder rnd;

        public EncoderOutputStream(OutputStream out, String pass) {
            super(out);
            this.rnd = new XOREncoder(pass.hashCode());
        }
        private int encode(int b) {
            return rnd.encode(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            for (int index = 0; index < b.length; index++) {
                b[index] = (byte) encode(b[index]);
            }
            out.write(b, off, len);
        }

        @Override
        public void write(int b) throws IOException {
            int encode = encode(b);
            out.write(encode);
        }
    }
    static class EncoderInputStream extends FilterInputStream {
        private XOREncoder rnd;

        public EncoderInputStream(InputStream in, String pass) {
            super(in);
            this.rnd = new XOREncoder(pass.hashCode());
        }

        private int encode(int b) {
            return rnd.encode(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int read = in.read(b, off, len);
            for (int index = 0; index < b.length; index++) {
                b[index] = (byte) encode(b[index]);
            }
            return read;
        }

        @Override
        public int read() throws IOException {
            int read = in.read();
            return encode(read);
        }
    }
    //--------------FilterReader / FilterWriter---------------
    @Test
    public void testFilterReader_FilterWriter() throws IOException {
        // given
        String string = "Привет Мир!";
        char[] chars = string.toCharArray();
        Reader arrayReader = new CharArrayReader(chars);
        // это декоратор, анонимно реализуем его тут на основе фильтра
        Reader reader = new FilterReader(arrayReader) {
            @Override
            public int read() throws IOException {
                // тут мы вначале читаем данные бызовым Reader
                char read = (char) super.read();
                // а потом меняем их
                return changeCase(read);
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                // тут мы вначале читаем данные бызовым Reader
                int read = super.read(cbuf, off, len);
                // а потом меняем их
                for (int i = off; i < read; i++) {
                    cbuf[i] = changeCase(cbuf[i]);
                }
                return read;
            }
        };
        // теперь тоже в обратном порядке
        Writer arrayWriter = new CharArrayWriter();
        // это наш декаратор, анонимно реализуем его тут на основе фильтра
        Writer writer = new FilterWriter(arrayWriter) {
            @Override
            public void write(int c) throws IOException {
                // а тут мы кодируем перед отправкой в базовый Writer
                char ch = changeCase((char) c);
                // после отправляем
                super.write(c);
            }

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                // тут мы кодируем перед отправкой в базовый Writer
                for (int i = off; i < len; i++) {
                    cbuf[i] = changeCase(cbuf[i]);
                }
                // после отправляем
                super.write(cbuf, off, len);
            }

            @Override
            public void write(String str, int off, int len) throws IOException {
                this.write(str.toCharArray(), off, len);
            }
        };
        // "Привет Мир!" -> CharArrayReader -> FilterReader -> "пРИВЕТ мИР!"
        char[] buffer = new char[100];
        int read = reader.read(buffer);
        // then
        assertEquals(11, read);
        String encrypted = new String(buffer, 0, read);
        assertEquals("пРИВЕТ мИР!", encrypted);
        // передаем данные в связку
        // "пРИВЕТ мИР!" -> FilterWriter -> CharArrayWriter -> "Привет Мир!"
        writer.write(buffer, 0, read);
        // then
        String actual = arrayWriter.toString();
        assertEquals("Привет Мир!", actual);
    }
    private char changeCase(char ch) {
        if (Character.isUpperCase(ch)) {
            return Character.toLowerCase(ch);
        }
        return Character.toUpperCase(ch);
    }
    // ------------PrintStream-------------
    // PrintStream - это фильтр, который добовляет к OutputStream
    // методы для печати реальных данных в строковом формате
    // кстати, System.out есть обэект этого класса
    @Test
    public void testPrintStream() throws UnsupportedEncodingException {
        // подготовили приемник
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // задекорировали PrintStream исходный OutputStream
        PrintStream out = new PrintStream(bytes, true, "UTF-8");
        // напечатали
        out.println("Привет Мир!");
        // получили в приемнике то что пришло
        byte[] array = bytes.toByteArray();
        assertEquals(String.format("%s%s", "Привет Мир!", System.lineSeparator()),
                new String(array, "UTF-8"));
    }
    //------------PrintWriter---------------
    // Writer - то же самое что PrintStream, только для Writer
    @Test
    public void testPrintWriter() throws UnsupportedEncodingException {
        // подготовили приемник
        CharArrayWriter chars = new CharArrayWriter();
        // задекорировали PrintWriter исходный Writer
        PrintWriter out = new PrintWriter(chars, true);
        // Напечатали
        out.println("Привет Мир!");
        // получили в приемнике то что пришло
        char[] array = chars.toCharArray();
        assertEquals(String.format("%s%s", "Привет Мир!", System.lineSeparator()),
                String.valueOf(array));
    }
    // декораторы
    // TODO ----------BufferedInputStream / BufferedOutputStream-------
    // TODO ----------BufferedReader / BufferedWriter------------------

    // -------ObjectInputStream / ObjectOutputStreaem---------
    @Test
    public void testMultipleSerialization() throws IOException, ClassNotFoundException {
        // given
        // есть какие-то данные...
        List<Object> list = new ArrayList<>();

        list.add(Math.E);
        list.add("Привет Мир!");
        list.add(42);
        list.add(true);
        list.add(new Point(23, 45));

        // when
        // создаем объекты для сериализации
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        // ObjectOutputStream работает на каком-то внешнем OutputStream
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutput);
        // пишем объект 2шт.
        out.writeObject(list);
        out.writeObject(list);
        // получаем массив байтов
        byte[] bytes = byteArrayOutput.toByteArray();

        // when
        // создаем объекты для десериализации
        InputStream byteArrayInput = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(byteArrayInput);
        // читаем объекты
        Object object1 = in.readObject();
        Object object2 = in.readObject();
        // then
        // вернули исходник
        assertEquals("[2.718281828459045, Привет Мир!, 42, true, java.awt.Point[x=23,y=45]]", object1.toString());
        assertEquals("[2.718281828459045, Привет Мир!, 42, true, java.awt.Point[x=23,y=45]]", object2.toString());


    }
}
