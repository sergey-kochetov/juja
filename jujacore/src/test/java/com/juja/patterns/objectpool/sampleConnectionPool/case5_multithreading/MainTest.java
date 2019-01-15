package com.juja.patterns.objectpool.sampleConnectionPool.case5_multithreading;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest extends AbstractTest {

    @Test
    public void test() throws Exception {
        // when
        Main.main(new String[0]);

        Thread.sleep(5000);

        // then
        // тут сложно что либо конкретное потестить потому как много потоков
        // и логика переключения между ними недетерминированная (неопределенная)
        // даже этот тест будет иногда слетать
        String out = console.getOut();
        assertTrue(out.contains("init pool...\n" +
                "put Resource@1 to pool\n" +
                "put Resource@2 to pool\n" +
                "put Resource@3 to pool\n" +
                "...init done!"));
        // все три ресурса были извлечены из пула
        assertTrue(out.contains("get Resource@1 from pool"));
        assertTrue(out.contains("get Resource@2 from pool"));
        assertTrue(out.contains("get Resource@3 from pool"));
        // пул был пустым некоторое время - нехватка ресурсов
        assertTrue(out.contains("pool is empty - waiting for resource..."));
        // все ресурсы обрабатывали какие-либо данные
        assertTrue(out.contains("Resource@1 processed: data"));
        assertTrue(out.contains("Resource@2 processed: data"));
        assertTrue(out.contains("Resource@3 processed: data"));
        // все данные были обработаны
        assertTrue(out.contains("processed: data0"));
        assertTrue(out.contains("processed: data1"));
        assertTrue(out.contains("processed: data2"));
        assertTrue(out.contains("processed: data3"));
        assertTrue(out.contains("processed: data4"));
        assertTrue(out.contains("processed: data5"));
        // все ресурсы вобвращались обратно на пул
        assertTrue(out.contains("put Resource@1 to pool"));
        assertTrue(out.contains("put Resource@2 to pool"));
        assertTrue(out.contains("put Resource@3 to pool"));
        // но порядок не соблюден, а потому мы не знаем
        // были ли все ресурсы после окончания работы возвращены на место
        // равномерно ли была распределена нагрузка между ресурсами
        // и т.д.
    }
}
