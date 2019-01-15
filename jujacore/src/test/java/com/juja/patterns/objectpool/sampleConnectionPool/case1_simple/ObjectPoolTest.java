package com.juja.patterns.objectpool.sampleConnectionPool.case1_simple;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectPoolTest {
    // тест на извлечение первого ресурса
    @Test
    public void testGetFirstResource() {
        // given
        ObjectPool pool = new ObjectPool(2);

        // when
        Resource resource = pool.get();

        // then
        assertEquals("Resource@1", resource.toString());
    }

    // тест на извлечение второго ресурса
    @Test
    public void testGetSecondResource() {
        // given
        ObjectPool pool = new ObjectPool(2);

        // when
        Resource resource1 = pool.get();
        Resource resource2 = pool.get();

        // then
        assertEquals("Resource@1", resource1.toString());
        assertEquals("Resource@2", resource2.toString());
    }

    // а вот третий уже получить не можем, потому как размер пула = 2
    @Test
    public void testNotEnoughResources() {
        // given
        ObjectPool pool = new ObjectPool(2);

        // when
        Resource resource1 = pool.get();
        Resource resource2 = pool.get();

        try {
            Resource resource3 = pool.get();
            fail("Expected exception");
        } catch (Exception e) {
            assertEquals("pool is empty!", e.getMessage());
        }
    }

    // тест на возвращение ресурса в пул
    @Test
    public void testPutResourceToPool() {
        // given
        ObjectPool pool = new ObjectPool(2);

        // when
        // взяли
        Resource resource1 = pool.get();
        Resource resource2 = pool.get();
        // вернули
        pool.put(resource1);
        // снова взяли
        Resource resource3 = pool.get();

        // then
        assertEquals("Resource@1", resource1.toString());
        assertEquals("Resource@2", resource2.toString());
        // а третим то у нас снова первый
        assertEquals("Resource@1", resource3.toString());
    }

}