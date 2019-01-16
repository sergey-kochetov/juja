package com.juja.patterns.state.classic.case2_implements;

// Класс - запускатор
public class Main {

    public static void main(String[] args) {
        // все просто, создаем компонент
        Context context = new Context();

        // делаем вызовы полезной логики
        // стейты будут семи себя заменять
        context.request1("data1");
        context.request1("data2");
        context.request1("data3");
        context.request1("data4");
        context.request1("data5");

        // вывод:
        // Set state: A
        // Handled by A: data1
        // Set state: B
        // Handled by B: data2
        // Set state: C
        // Handled by C: data3
        // Set state: A
        // Handled by A: data4
        // Set state: B
        // Handled by B: data5
        // Set state: C

        System.out.println(context.request2());
        System.out.println(context.request2());
        System.out.println(context.request2());
        System.out.println(context.request2());
        System.out.println(context.request2());

        // вывод:
        // Set state: B
        // Handled by C
        // Set state: A
        // Handled by B
        // Set state: C
        // Handled by A
        // Set state: B
        // Handled by C
        // Set state: A
        // Handled by B
    }
}
