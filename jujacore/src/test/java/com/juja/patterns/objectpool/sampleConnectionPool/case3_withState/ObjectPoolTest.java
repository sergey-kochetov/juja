package com.juja.patterns.objectpool.sampleConnectionPool.case3_withState;

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
        assertEquals("Resource[null]@1", resource.toString());
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
        assertEquals("Resource[null]@1", resource1.toString());
        assertEquals("Resource[null]@2", resource2.toString());
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
        assertEquals("Resource[null]@1", resource1.toString());
        assertEquals("Resource[null]@2", resource2.toString());
        assertEquals("Resource[null]@1", resource3.toString());
    }

    // тест на возвращение ресурса в пул, но с проверкой,
    // что состояние ресурса обнулилось
    @Test
    public void testPutResourceToPool_clearState() {
        // given
        ObjectPool pool = new ObjectPool(1);

        // when then
        Resource resource1 = pool.get();
        assertEquals("Resource[null]@1", resource1.toString());

        // when then
        resource1.setState("state");
        assertEquals("Resource[state]@1", resource1.toString());

        // when
        pool.put(resource1);

        // then
        Resource resource2 = pool.get();
        assertEquals("Resource[null]@1", resource2.toString());
    }
}