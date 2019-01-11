package com.juja.patterns.caching.case1_simple;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CacheTest {

    @Test
    // тестируем что кеш сперва берет данные из постовщика ресурсов
    public void testGetResourseFromProvider() {
        // given
        Resource resource = new Resource(13);
        Provider provider = mock(Provider.class);
        when(provider.get(13)).thenReturn(resource);

        Cache cache = new Cache(provider);
        // when
        Resource actual = cache.get(13);

        // then
        assertSame(resource, actual);
        verify(provider, only()).get(13);
    }
    @Test
    // во втором тесте на томже ключе кеш берет сохраненые данные
    public void testGetResourseFromCache() {
        // given
        Resource resource = new Resource(13);
        Provider provider = mock(Provider.class);
        when(provider.get(13)).thenReturn(resource);

        Cache cache = new Cache(provider);
        cache.get(13);
        // when
        Resource actual = cache.get(13);

        // then
        assertSame(resource, actual);
        verify(provider, only()).get(13);
    }

}