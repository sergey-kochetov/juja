package com.juja.core.collection.guava;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class GuavaTest {
    public static void print(Object ... args) {
        Arrays.stream(args).forEach(System.out::println);
    }

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        List<String> list1 = new ArrayList<String>() {{
            add("first");
            add("second");
            add("third");
        }};

        List<String> list2 = ImmutableList.of("first", "second", "third");

        list.addAll(list2);


        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (int i = 0; i < 10; i++) {
            builder.add("=" + i);
        }
        ImmutableList<String> list3 = builder.build();

        ImmutableMap<String, Integer> map = ImmutableMap.of("first", 1, "second",2, "third", 3);


        print(list, list2, map);
    }

    @Test
    public void test2() {
        print(Strings.commonSuffix("Mike", "Coke")); // общий префикс -> ke
        print(Strings.padEnd("some long string", 20, '_')); //some long string____
        print(Strings.repeat("_", 20)); // ____________________
        ImmutableList.of("John", "Jeremy", "Mike", "Sudharsan")
                .stream()
                .map(name -> Strings.padStart(name, 15, '.'))
                .forEach(GuavaTest::print);
        /**
         * ...........John
         * .........Jeremy
         * ...........Mike
         * ......Sudharsan
         */

    }
    @Test
    public void test3() {
        String str = "long text, just long text, nothing but text";
        Iterable<String> strings = Splitter.on("text").trimResults().split(str);

        String result = Joiner.on("video").join(strings);

        print(result); // longvideo, just longvideo, nothing butvideo
    }

    @Test
    public void test4() {
        ArrayList<String> list = Lists.newArrayList("FirstGreet", "second", "third");
        HashSet<String> set = Sets.newHashSet("fourth", "fifth", "sixth");

        Iterable<String> concat = Iterables.concat(list, set);

        assertEquals("[FirstGreet, second, third, sixth, fifth, fourth]", concat.toString());

        Iterable<String> skip = Iterables.skip(concat, 2);
        Iterable<String> limit = Iterables.limit(skip, 2);

        assertEquals("[third, sixth]", limit.toString());
    }

    @Test
    public void sets() {
        // given
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3, 4);
        HashSet<Integer> set2 = Sets.newHashSet(3, 4, 5, 6, 7);

        // when
        Sets.SetView<Integer> diff1 = Sets.difference(set1, set2);

        // then
        assertEquals("[1, 2]", diff1.toString());

        // when
        Sets.SetView<Integer> diff2 = Sets.difference(set2, set1);

        // then
        assertEquals("[5, 6, 7]", diff2.toString());

        // when
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);

        // then
        assertEquals("[3, 4]", intersection.toString());
    }


    @Test
    public void test5() {
        // given
        HashMultiset<Object> multiset = HashMultiset.create();

        // when
        multiset.add("first");
        multiset.add("second", 3);
        multiset.add("third");
        multiset.add("third");

        // then
        assertEquals("[third x 2, first, second x 3]", multiset.toString());

    }

    @Test
    public void test6() {
        // given
        HashMultimap<String, String> multimap = HashMultimap.create();

        // when
        multimap.put("first", "1");
        multimap.put("second", "2");
        multimap.put("second", "0010");
        multimap.putAll("third", Lists.newArrayList("3", "0011"));

        // then
        assertEquals("{second=[0010, 2], third=[0011, 3], first=[1]}", multimap.toString());
    }
    @Test
    public void test7() {
        // given
        HashBiMap<Object, Object> biMap = HashBiMap.create();

        biMap.put("first", 1);
        biMap.put("second", 2);
        biMap.put("third", 3);

        // when then
        assertEquals("{first=1, second=2, third=3}", biMap.toString());
        assertEquals(1, biMap.get("first"));
        assertEquals("first", biMap.inverse().get(1));
    }
    @Test
    public void test8() {
        // given
        HashBasedTable<String, String, Double> table = HashBasedTable.create();
        table.put("first", "1", 1000.0);
        table.put("first", "2", 1100.0);
        table.put("second", "1", 2000.0);
        table.put("second", "2", 2100.0);
        table.put("third", "1", 3000.0);
        table.put("third", "2", 3100.0);

        assertEquals("{" +
                "first={1=1000.0, 2=1100.0}, " +
                "second={1=2000.0, 2=2100.0}, " +
                "third={1=3000.0, 2=3100.0}}", table.toString());
        assertEquals("1000.0", table.get("first", "1").toString());
        assertEquals("{1=2000.0, 2=2100.0}", table.row("second").toString());
        assertEquals("{first=1100.0, second=2100.0, third=3100.0}", table.column("2").toString());

    }

    }
