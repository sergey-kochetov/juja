package com.juja.patterns.objectpool.sampleConnectionPool.case5_multithreading;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class ObjectPoolTest {

    private ObjectPool pool;
    private Resource resource1;
    private Resource resource2;

    // подготовка (фикстура) для всех тестов одинаковая
    // один пул, два ресурса
    @Before
    public void setup() {
        // мочим все :)
        // ресурса в пуле у нас два
        resource1 = mock(Resource.class);
        resource2 = mock(Resource.class);
        final List<Resource> resources = new LinkedList<>();
        resources.add(resource1);
        resources.add(resource2);

        pool = new ObjectPool(2) {
            // они будут возвращаться в заданном порядке
            @Override
            protected Resource createResource() {
                return resources.remove(0);
            }
        };
    }

    // тест на извлечение первого ресурса
    @Test
    public void testGetFirstResource() {
        // when
        Resource resource = pool.get();

        // then
        assertSame(this.resource1, resource);
    }

    // тест на извлечение второго ресурса
    @Test
    public void testGetSecondResource() {
        // when
        Resource resource1 = pool.get();
        Resource resource2 = pool.get();

        // then
        assertSame(this.resource1, resource1);
        assertSame(this.resource2, resource2);
    }

    // самый интересный тест - проверяет, что ресурсы возвращаются назад в пул
    // тут замешана магия многопоточности, держись!
    @Test
    public void testNoMoreMaxCountResources() throws Exception {
        // given
        // берем оба ресурса
        Resource resource1 = pool.get();
        Resource resource2 = pool.get();

        // when
        // запускаем поток, который попробует взять третий ресурс
        // и на этом заблочится, потому что пул длинной в 2
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Resource> future = executor.submit(new Callable<Resource>() {
            @Override
            public Resource call() throws Exception {
                return pool.get();
            }
        });
        // заставим поток понервничать
        Thread.sleep(500);

        // then
        // мы проверим, что в future ничего пока нет
        // т.е. поток все еще ожидает своей очереди на pool.get()
        assertFalse(future.isDone());

        // when
        pool.put(resource1);

        // then
        // намекаем, что потоку пора завершаться
        // но он нас не послушает пока не разблочится на pool.get()
        executor.shutdown();
        // вот это мы и подождем сколько надобно
        // (эти две строчки работают в тандеме)
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        // ура! внешний поток получил ресурс и мы его получаем из future
        assertTrue(future.isDone());
        Resource resource3 = future.get();

        // when
        // ану-ка вызовем метод на этом ресурсе?
        resource3.doit("data");

        // then
        // так как первым вернулся назад в пул прокси представляющий resource1,
        // то его мы и вернули под видом третьего
        verify(this.resource1).doit("data");
        // а второй ничего об этом и не узнал
        verifyNoMoreInteractions(this.resource2);
    }
}
