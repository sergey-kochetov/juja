package com.juja.collection;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static org.junit.Assert.*;

public class StreamSampleTest {

    @Test
    public void testSimpleStream_01() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "name1"),
                new User(123, Role.GUEST, "name123"),
                new User(11, Role.USER, "super1"),
                new User(12, Role.USER, "super2"),
                new User(13, Role.USER, "super3")
        );
        //filtering
        List<User> filtered = new LinkedList<>();
        for (User user : users) {
            if (user.getRole() == Role.USER) {
                filtered.add(user);
            }
        }
        //sorting
        Collections.sort(filtered, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });

        //map -filtring
        List<String> names = new LinkedList<>();
        for (User user : filtered) {
            names.add(user.getName());
        }
        //then
        assertEquals("[super3, super2, super1]", names.toString());
    }

    @Test
    public void testStreamJava8_02() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "name1"),
                new User(123, Role.GUEST, "name123"),
                new User(11, Role.USER, "super1"),
                new User(12, Role.USER, "super2"),
                new User(13, Role.USER, "super3")
        );
        //Stream
        List<String> names1 = users.stream()
                //lazy -
                .filter(user -> user.getRole() == Role.USER)
                .sorted((o1, o2) -> Long.compare(o2.getId(), o1.getId()))
                .map(user -> user.getName())
                // =
                .collect(Collectors.toList());
        //easy
        List<String> names2 = users.stream()
                .filter(user -> user.getRole() == Role.USER)
                .sorted(Comparator.comparing(User::getId).reversed())
                .map(User::getName)
                .collect(Collectors.toList());
        //then
        assertEquals("[super3, super2, super1]", names1.toString());
        assertEquals("[super3, super2, super1]", names2.toString());
    }

    //Filtering to predictor for Stream
    @Test
    public void testFiltring_03() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );
        List<User> result = users.stream()
                .filter(user -> user.getId() % 2 == 0)
                .collect(Collectors.toList());
        // then
        assertEquals("[[2=User 2], [4=User 4]]", result.toString());
    }
    //Stream<T> distinct()
    //S unordered()
    @Test
    public void testDistinct_04() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                //update User
                new User(2, Role.USER, "User 2 update"),
                new User(3, Role.USER, "User 3 update")
        );

        List<User> result = users.stream()
                //если не нужен порядок то по производительности быстрее
                .unordered()
                //удаляем дубликаты
                .distinct()
                .collect(Collectors.toList());
        //then
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4]]", result.toString());
    }
    // Limit
    //Отсекает лишнее в хвосте
    //Limit - "short-circuiting stateful intermediate operation"
    @Test
    public void testLimit_05() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5"),
                new User(6, Role.USER, "User 6")
        );

        List<User> result = users.stream()
                //берем первые 2
                .limit(2)
                .collect(Collectors.toList());

        assertEquals("[[1=User 1], [2=User 2]]", result.toString());
    }
    //Skip
    //Отсекает лишнее в начале
    //Skip - "stateful intermidiate operation"
    @Test
    public void testSkip_06() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5"),
                new User(6, Role.USER, "User 6")
        );

        List<User> result = users.stream()
                //берем все после 4-х
                .skip(4)
                .collect(Collectors.toList());
        //Limit - "short-circuiting statful intermediate operation"
        assertEquals("[[5=User 5], [6=User 6]]", result.toString());
    }
    //Sorted
    @Test
    public void testSorted_07() {
        //given
        Collection<User> users = Arrays.asList(
                new User(4, Role.USER, "User 4"),
                new User(3, Role.USER, "User 3"),
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2")
        );

        List<User> result = users.stream()
                // sorted whith Comparator
                // (o1, o2) -> o1.getName().compareTo(o2.getName())
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
        //Limit - "short-circuiting statful intermediate operation"
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4]]", result.toString());
    }
    // ComparableUser - for next test
    static class ComparableUser extends User implements Comparable<User> {
        public ComparableUser(long id, Role role, String name) {
            super(id, role, name);
        }

        @Override
        public int compareTo(User o) {
            return getName().compareTo(o.getName());
        }
    }

    @Test
    public void testIntermediateAndTerminalOperation_08() {
        //given
        List<String> phases = new LinkedList<>();
        Collection<Integer> users = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        List<Integer> names = users.stream()
                //промежуточные операции ничего не делают
                .filter(n -> {
                    phases.add("f-" + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    phases.add("m-" + n);
                    return n * n;
                })
                .collect(Collectors.toList());
        //then
        assertEquals("[4, 16, 36, 64]", names.toString());
        assertEquals("[f-1, f-2, m-2, f-3, f-4, m-4, f-5, f-6, m-6, f-7, f-8, m-8]", phases.toString());
    }
    @Test
    public void testIntermediateAndTerminalOperationSorted_09() {
        //given
        List<String> phases = new LinkedList<>();
        Collection<Integer> users = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        List<Integer> names = users.stream()
                //промежуточные операции ничего не делают
                .filter(n -> {
                    phases.add("f-" + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    phases.add("m-" + n);
                    return n * n;
                })
                .sorted((n1, n2) -> {
                    phases.add("s-" + n1 + "-" + n2);
                    return Integer.compare(n1, n2);
                })
                //некоторые функции влияют на выполнение других
                .limit(2)
                .collect(Collectors.toList());
        //then
        assertEquals("[4, 16]", names.toString());
        assertEquals("[f-1, f-2, m-2, f-3, f-4, m-4, f-5, f-6, m-6, " +
                "f-7, f-8, m-8, s-16-4, s-36-16, s-64-36]", phases.toString());
    }
    // перебор элементов в Stream
    // Stream<T> peek(Consumer<? super T> action)
    // for debug
    @Test
    public void testPeek_10() {
        //given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );
        List<User> sorted = new LinkedList<>();

        // when
        List<String> names = users.stream()
                //посортировали в обратном порядке по id
                .sorted(Comparator.comparing(User::getId).reversed())
                //перегоняем юзеров паралельно в другой список
                // с помощью Consumer
                .peek(user -> sorted.add(user))
                // дальше берем только имена
                .map(User::getName)
                .collect(Collectors.toList());
        //then
        assertEquals("[User 5, User 4, User 3, User 2, User 1]", names.toString());
        assertEquals("[[5=User 5], [4=User 4], [3=User 3], [2=User 2], [1=User 1]]", sorted.toString());
    }

    // foreach
    @Test
    public void testForeach_11() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );
        List<String> names = new LinkedList<>();

        // when
        users.stream()
                // берем только именна
                .map(User::getName)
                // отпровляем все данные на Consumer -> terminal
                .forEach(name -> names.add(name));
        // then
        assertEquals("[User 1, User 2, User 3, User 4, User 5]", names.toString());
    }

    // foreachOrdered
    @Test
    public void testForeachOrdered_12() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );
        List<String> names = new LinkedList<>();

        // when
        users.stream()
                .map(User::getName)
                .forEachOrdered(name -> names.add(name));
        // then
        assertEquals("[User 1, User 2, User 3, User 4, User 5]", names.toString());
    }
    // map
    @Test
    public void testMap_13() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        List<String> names = new LinkedList<>();
        names = users.stream()
                // берем только имена
                // user -> user.getName()
                .map(User::getName)
                .collect(Collectors.toList());
        // then
        assertEquals("[User 1, User 2, User 3, User 4, User 5]", names.toString());

        // when
        List<Long> ids = users.stream()
                // user -> user.getId()
                .map(User::getId)
                .collect(Collectors.toList());
        // then
        assertEquals("[1, 2, 3, 4, 5]", ids.toString());
    }
    // Map to Long
    @Test
    public void testMapToLong_14() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        long[] data = users.stream()
                .sorted(Comparator.comparing(User::getRole))
                .mapToLong(user -> (long) (user.getId() + user.getRole().name().length()))
                .toArray();
        // then
        assertEquals("[6, 8, 6, 8, 10]", Arrays.toString(data));

        // when then
        assertEquals(15, users.stream()
                .mapToInt(user -> (int) user.getId())
                .sum());

        // when then
        assertEquals("OptionalDouble[3.0]", users.stream()
                .mapToInt(user -> (int) user.getId())
                // среднее арифметическое
                .average().toString());
        // when then
        assertEquals("IntSummaryStatistics{count=5, sum=15, min=1, average=3,000000, max=5}",
                users.stream()
                        .mapToInt(user -> (int) user.getId())
                        .summaryStatistics().toString());
    }
    //
    @Test
    public void testMapToInt_mapToObject_15() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        List<User> result = users.stream()
                .mapToInt(user -> (int) user.getId())
                // получим InitStream
                // и можем сделать с ним обратное
                .mapToObj(id -> new User(id, users.get(id - 1).getRole(), "User " + id))
                .collect(Collectors.toList());
        // then
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4], [5=User 5]]", result.toString());
    }

    //
    @Test
    public void testFlatMap_16() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        List<String> data = users.stream()
                .sorted(Comparator.comparing(User::getRole))
                .flatMap(user -> Stream.of(
                        "id:" + user.getId(),
                        "role:" + user.getRole(),
                        "name:" + user.getName()))
                .collect(Collectors.toList());
        // then
        assertEquals("[id:1, role:ADMIN, name:User 1, " +
                "id:3, role:ADMIN, name:User 3, " +
                "id:2, role:USER, name:User 2, " +
                "id:4, role:USER, name:User 4, " +
                "id:5, role:GUEST, name:User 5]", data.toString());
    }
    //
    @Test
    public void testFlatMapToInt_17() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        List<Integer> data = users.stream()
                .sorted(Comparator.comparing(User::getRole))
                .flatMapToInt(user -> IntStream.of(
                        (int) user.getId(),
                        user.getRole().name().length(),
                        user.getName().length()
                )).collect(() -> new LinkedList<Integer>(),
                        (list, value) -> list.add(value),
                        (list, list2) -> list.addAll(list2));
        // then
        assertEquals("[" +
                "1, 5, 6, " +
                "3, 5, 6, " +
                "2, 4, 6, " +
                "4, 4, 6, " +
                "5, 5, 6]", data.toString());
    }
    // stream to array
    @Test
    public void testToArray_18() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        Object[] sorted = users.stream()
                .sorted(Comparator.comparing(User::getRole))
                .toArray();
        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]", Arrays.toString(sorted));
    }
    //
    @Test
    public void testToArrayWithIntFunction_19() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        String[] sorted = users.stream()
                .sorted(Comparator.comparing(User::getRole))
                .map(User::toString)
                // length -> new String[length]
                .toArray(String[]::new);
        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]", Arrays.toString(sorted));
    }
    // collect
    @Test
    public void testCollect_20() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        List<String> result = users.stream()
                .sorted(Comparator.comparing(User::getRole))
                // преобразуем в String
                .map(User::toString)
                .collect(
                        () -> new LinkedList<>(),
                        (container, name) -> container.add(name),
                        (container, sub) -> container.addAll(sub)
                );

    }
    // reduce
    @Test
    public void testReduce_21() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        Optional<Long> result = users.stream()
                .map(u -> u.getId())
                .reduce((a, b) -> a + b);
        // then
        assertTrue(result.isPresent());
        assertEquals(15L, result.get().longValue());
    }
    // reduce
    @Test
    public void testReduceString_22() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        StringBuilder result = users.stream()
                .reduce(new StringBuilder(),
                        (builder, user) -> builder.append(user.toString()),
                        (builder, anotherBuilder) -> builder.append(anotherBuilder.toString()));
        // then
        assertEquals("[1=User 1][2=User 2][3=User 3][4=User 4][5=User 5]", result.toString());
    }
    // MIN
    @Test
    public void testMin_23() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        Optional<User> result = users.stream()
                .min(Comparator
                        .comparing(User::getRole)
                        .thenComparing(Comparator
                                .comparing(User::getName)
                                .reversed()));
        // then
        assertTrue(result.isPresent());
        assertEquals("Optional[[3=User 3]]", result.toString());
    }
    // MAN
    @Test
    public void testMax_24() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        Optional<User> result = users.stream()
                .max(Comparator
                        .comparing(User::getRole)
                        .thenComparing(Comparator
                                .comparing(User::getName)
                                .reversed()));
        // then
        assertTrue(result.isPresent());
        assertEquals("Optional[[5=User 5]]", result.toString());
    }
    // long count
    @Test
    public void testcount_25() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        long result = users.stream()
                .filter(user -> user.getRole() == Role.USER)
                .count();
        // then
        assertEquals(2, result);
    }
    // Any match
    @Test
    public void testAnyMatch_26() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when then
        assertTrue(users.stream()
                .anyMatch(user -> user.getRole() == Role.USER));
        // when then
        assertFalse(users.stream()
                .anyMatch(user -> user.getName().contains("6")));
        // when then
        assertTrue(users.stream()
                .anyMatch(user -> user.getId() % 2 == 0));
        // when then
        assertTrue(users.stream()
                .anyMatch(user -> user.getName().contains("User")));
    }
    // All match
    @Test
    public void testAllyMatch_27() {
        //given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when then
        assertFalse(users.stream()
                .allMatch(user -> user.getRole() == Role.USER));
        // when then
        assertFalse(users.stream()
                .allMatch(user -> user.getName().contains("6")));
        // when then
        assertFalse(users.stream()
                .allMatch(user -> user.getId() % 2 == 0));
        // when then
        assertTrue(users.stream()
                .allMatch(user -> user.getName().contains("User")));
    }
    // Stream of
    @Test
    public void testStreamOf_28() {
        //given
        Stream<User> stream = Stream.of(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );
        // when
        Optional<User> result = stream.findAny();
        // then
        assertTrue(result.toString().contains("User"));
    }
    // StreamBuilder of
    @Test
    public void testStreamBuilderOf_29() {
        //given
        Stream<User> stream = Stream.<User>builder()
                .add(new User(1, Role.ADMIN, "User 1"))
                .add(new User(2, Role.USER, "User 2"))
                .add(new User(3, Role.ADMIN, "User 3"))
                .add(new User(4, Role.USER, "User 4"))
                .add(new User(5, Role.GUEST, "User 5")).build();
        // when
        Optional<User> result = stream.findAny();
        // then
        assertTrue(result.toString().contains("User"));
    }
    //Бесконечный стрим
    // StreamIterate()
    @Test
    public void testStreamIterate_30() {
        //given
        int start = 1234;
        // when
        List<Integer> result = Stream
                //создаем сиракузная последовательность
                .iterate(start, n -> (n % 2 == 0) ? (n / 2) : (n * 3 + 1))
                // ограничиваем ряд
                .limit(150)
                // приводим в список
                .collect(Collectors.toList());
        // then
        assertEquals("[1234, 617, 1852, 926, 463, 1390, 695, 2086, " +
                "1043, 3130, 1565, 4696, 2348, 1174, 587, 1762, 881, 2644, " +
                "1322, 661, 1984, 992, 496, 248, 124, 62, 31, 94, 47, 142, " +
                "71, 214, 107, 322, 161, 484, 242, 121, 364, 182, 91, 274, " +
                "137, 412, 206, 103, 310, 155, 466, 233, 700, 350, 175, 526, " +
                "263, 790, 395, 1186, 593, 1780, 890, 445, 1336, 668, 334, 167, " +
                "502, 251, 754, 377, 1132, 566, 283, 850, 425, 1276, 638, 319, " +
                "958, 479, 1438, 719, 2158, 1079, 3238, 1619, 4858, 2429, 7288, " +
                "3644, 1822, 911, 2734, 1367, 4102, 2051, 6154, 3077, 9232, 4616, " +
                "2308, 1154, 577, 1732, 866, 433, 1300, 650, 325, 976, 488, 244, " +
                "122, 61, 184, 92, 46, 23, 70, 35, 106, 53, 160, 80, 40, 20, 10, 5, " +
                "16, 8, 4, 2, 1, 4, 2, 1, 4, 2, 1, 4, 2, 1, 4, 2, 1, 4, 2, 1, 4, 2]", result.toString());
    }
    //Бесконечный стрим
    // StreamGenerate()
    @Test
    public void testStreamGenerate_31() {
        //given
        List<Integer> result = Stream
                .generate(() -> new Random().nextInt())
                .limit(150)
                .collect(Collectors.toList());
        //then
        assertTrue(result.stream().anyMatch(n -> n != 0));
        //assertEquals("", result.toString());
    }
    //объединяем стрим
    // StreamConcat()
    @Test
    public void testStreamConcat_32() {
        //given
        Stream<Integer> stream1 = Stream.iterate(1, n -> n * 2).limit(5);
        Stream<Integer> stream2 = Stream.iterate(1, n -> n * 3).limit(5);
        // when
        Stream<Integer> stream3 = Stream.concat(stream1, stream2);
        List<Integer> result = stream3.collect(Collectors.toList());
        // then
        assertEquals("[1, 2, 4, 8, 16, 1, 3, 9, 27, 81]", result.toString());
    }

    // Optioanl
    @Test
    public void testOptional_33() {
        //given
        Optional<String> value = Stream.of("1", "2", "3").findFirst();

        assertTrue(value.isPresent());

        assertEquals("1", value.get());
        assertEquals("1", value.orElse("0"));
        assertEquals("1", value.orElseGet(() -> "0"));
        assertEquals("1", value.orElseThrow(() -> new RuntimeException()));
//        String memory = null;
//        value.isPresent(data -> memory = data);
        Optional<String> empty =
                Stream.of("1", "2", "3")
                .filter(a -> a.equals("5")).findAny();
        assertFalse(empty.isPresent());
        try {
            assertNull(empty.get());
            fail("create exception");
        } catch (NoSuchElementException e) {
            assertEquals("No value present", e.getMessage());
        }

        assertEquals("0", empty.orElse("0"));
        try {
            assertEquals("1", empty.orElseThrow(() -> new RuntimeException("Somthing wrong")));
            fail("create exception");
        } catch (RuntimeException e) {
            assertEquals("Somthing wrong", e.getMessage());
        }
    }
    // Fail Stream
    @Test
    public void testStreamFromFile_34() {
        //given when
        Stream<String> stream = null;
        try {
            stream = Files.lines(Paths.get("src/main/resources/test.txt"));
        } catch (IOException e) {

        }

        // then
//        assert stream != null;
//        assertEquals(null, stream.collect(Collectors.joining()).toString());
    }
}