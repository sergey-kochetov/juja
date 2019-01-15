package com.juja.patterns.objectpool.sampleConnectionPool.case2_withFactory;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;


import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ObjectPoolTest extends AbstractTest {

    // тест на извлечение первого ресурса
    @Test
    public void testGetFirstResource() {
        // given
        // фектори будет работать как мы того хотим!
        ObjectFactory factory = mock(ObjectFactory.class);
        Resource resource = new Resource();
        when(factory.createResource()).thenReturn(resource);

        ObjectPool pool = new ObjectPool(factory);

        // when
        Resource resourceFromPool = pool.get();

        // then
        // объект тот же
        assertSame(resource, resourceFromPool);
    }

    // тест на извлечение второго ресурса
    @Test
    public void testGetSecondResource() {
        // given
        ObjectFactory factory = mock(ObjectFactory.class);
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();
        when(factory.createResource()).thenReturn(resource1, resource2);

        ObjectPool pool = new ObjectPool(factory);

        // when
        Resource resourceFromPool1 = pool.get();
        Resource resourceFromPool2 = pool.get();

        // then
        assertSame(resource1, resourceFromPool1);
        assertSame(resource2, resourceFromPool2);
    }

    // тест на извлечение второго ресурса, ограничений то нет
    // (это опасно утечками, но для примера можно)
    @Test
    public void testThirdResources() {
        // given
        ObjectFactory factory = mock(ObjectFactory.class);
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();
        Resource resource3 = new Resource();
        when(factory.createResource()).thenReturn(resource1, resource2, resource3);
        ObjectPool pool = new ObjectPool(factory);

        // when
        Resource resourceFromPool1 = pool.get();
        Resource resourceFromPool2 = pool.get();
        Resource resourceFromPool3 = pool.get();

        // then
        assertSame(resource1, resourceFromPool1);
        assertSame(resource2, resourceFromPool2);
        assertSame(resource3, resourceFromPool3);
    }

    // тест на возвращение ресурса в пул
    @Test
    public void testPutResourceToPool() {
        // given
        ObjectFactory factory = mock(ObjectFactory.class);
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();
        Resource resource3 = new Resource();
        when(factory.createResource()).thenReturn(resource1, resource2, resource3);
        ObjectPool pool = new ObjectPool(factory);

        // when
        Resource resourceFromPool1 = pool.get();
        Resource resourceFromPool2 = pool.get();
        pool.put(resourceFromPool1);
        Resource resourceFromPool3 = pool.get();

        // then
        assertSame(resource1, resourceFromPool1);
        assertSame(resource2, resourceFromPool2);
        assertSame(resource1, resourceFromPool3);
    }
}