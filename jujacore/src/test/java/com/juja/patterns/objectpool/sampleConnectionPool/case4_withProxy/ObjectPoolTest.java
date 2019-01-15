package com.juja.patterns.objectpool.sampleConnectionPool.case4_withProxy;

import com.juja.patterns.objectpool.sampleConnectionPool.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ObjectPoolTest extends AbstractTest {

    // тест на извлечение первого ресурса
    @Test
    public void testGetFirstResource() {
        // given
        ObjectPool pool = new ObjectPool(2);

        // when
        Resource resource = pool.get();

        // then
        assertEquals("ResourceImpl@1", resource.toString());
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
        assertEquals("ResourceImpl@1", resource1.toString());
        assertEquals("ResourceImpl@2", resource2.toString());
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
        resource1.close();
        // снова взяли
        Resource resource3 = pool.get();

        // then
        assertEquals("ResourceImpl@1", resource1.toString());
        assertEquals("ResourceImpl@2", resource2.toString());
        assertEquals("ResourceImpl@1", resource3.toString());
    }
}