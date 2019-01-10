package com.juja.patterns.immutable.classic;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    // сколько способов нарушить инкапсуляцию есть?
    public static void main(String[] args) {
        caseMutableConstructor();
        caseMutableSubclass();
        caseMutableAnonymousSubclass();
        caseMutableSetter();
        caseMutableGetter();
        caseMutableReflection();

        printBreak();

        caseImmutableConstructor();
        caseImmutableSubclass();
        caseImmutableAnonymousSubclass();
        caseImmutableSetter();
        caseImmutableGetter();
        caseImmutableReflection();
//        результат вывода
//        [fail1]
//        [fail2_0, fail2_1]
//        [fail3]
//        [fail4_0, fail4_1]
//        [fail5]
//        [fail6]
//        +++++++++++++++++++++
//        []
//        []
//        []
//        [fail6]
    }
    private static void printBreak() {
        System.out.println("+++++++++++++++++++++");
    }

    private static void caseMutableConstructor() {
        List<String> list = new LinkedList<>();
        // первая - создали объект, передали список через конструктор
        Mutable mutable = new Mutable(list);
        // но оставили себе ссылочку, на всякий
        list.add("fail1");
        System.out.println(mutable);

    }

    private static void caseMutableSubclass() {
        List<String> list = new LinkedList<>();
        // даольше пользоваться новым классом, под видом родителя
        NewMuttable mutable = new NewMuttable(list);
        mutable.doSmth();

        System.out.println(mutable);
    }

    static class NewMuttable extends Mutable {
        public NewMuttable(List<String> data) {
            super(data);
            // и в наследнике меняем protected поле
            data.add("fail2_0");
        }
        public void doSmth() {
            data.add("fail2_1");
        }
    }
    private static void caseMutableAnonymousSubclass() {
        List<String> list = new LinkedList<>();
        // третья - анонимная реализация с блоком инициализации
        // тоже самое что и наследованием, только красивее
        Mutable mutable = new Mutable(list) {{
            data.add("fail3");
        }};
        System.out.println(mutable);
    }
    private static void caseMutableSetter() {
        List<String> list = new LinkedList<>();
        Mutable mutable = new Mutable(list);
        // четвертая возпользуемся сеттером
        List<String> newList = new LinkedList<>();
        newList.add("fail4_0");

        mutable.setData(newList);
        // а потом еще и добовлять что угодно
        newList.add("fail4_1");
        System.out.println(mutable);
    }
    private static void caseMutableGetter() {
        List<String> list = new LinkedList<>();
        Mutable mutable = new Mutable(list);
        // пятая возпользуемся геттером
        mutable.getData().add("fail5");
        System.out.println(mutable);
    }
    // ну и рефлексия
    private static void caseMutableReflection() {
        List<String> list = new LinkedList<>();
        Mutable mutable = new Mutable(list);

        try {
            Field data = mutable.getClass().getDeclaredField("data");
            data.setAccessible(true);
            data.set(mutable, Arrays.asList("fail6"));
            data.setAccessible(false);
        } catch (Exception e) {
            // do nothing
        }
        System.out.println(mutable);
    }

    private static void caseImmutableConstructor() {
        List<String> list = new LinkedList<>();
        // первая - создали объект, передали список через контруктор
        Immutable immutable = new Immutable(list);

        // хоть мы и оставили себе ссылочку, но это безполезно
        list.add("fail1");
        System.out.println(immutable);
    }

    // носледоваться от Immutable не даст компилятьр
    // static class NewImmutable extends Immutable {}

    private static void caseImmutableSubclass() {
        List<String> list = new LinkedList<>();
        // ну и дальше пользоваться новым классом
        // под видом родителя не получится
        //Immutable immutable = new NewImmutable(list);

        //System.out.println(immutable);
    }

    private static void caseImmutableAnonymousSubclass() {
        List<String> list = new LinkedList<>();
        // третья - анонимная реализация с блок инициализации
        // тоже не получится - компилятор не даст
//        Immutable immutable = new Immutable(list) {{
//            // да и поле приватное
//            data.add("fail3");
//        }};
//        System.out.println(immutable);
    }

    private static void caseImmutableSetter() {
        List<String> list = new LinkedList<>();
        Immutable immutable = new Immutable(list);
        // четвертая - сеттера у нас нет
        List<String> newList = new LinkedList<>();
        newList.add("fail4_0");
        //immutable.setData(newList); - нет сеттера
        newList.add("fail4_1");
        System.out.println(immutable);
    }

    private static void caseImmutableGetter() {
        List<String> list = new LinkedList<>();
        Immutable immutable = new Immutable(list);
        // пятая -  воспользуемся геттером
        // но он - копия!!!
        immutable.getData().add("fail5");
        System.out.println(immutable);
    }

    // перед рефлексией ничто не устоит
    // даже Immutable
    private static void caseImmutableReflection() {
        List<String> list = new LinkedList<>();
        Immutable immutable = new Immutable(list);

        try {
            Field data = immutable.getClass().getDeclaredField("data");
            data.setAccessible(true);
            data.set(immutable, Arrays.asList("fail6"));
            data.setAccessible(false);
        } catch (Exception e) {
            // do nothing
        }
        System.out.println(immutable);

    }


}


