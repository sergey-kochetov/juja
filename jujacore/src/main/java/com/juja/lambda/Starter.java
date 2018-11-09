package com.juja.lambda;

import java.util.function.BiFunction;

public class Starter {
    public static void main(String[] args) {
        Starter s= new Starter();
        Person p = new Person();
        //...
        s.processHospital(p, 4, (g, d) -> 0.8 * 8 * d * g);

        s.processAddition(p, 8.5, (g, h) -> 2.0 * h * g);
    }

    public void processHospital(Person p, Integer days, BiFunction<Double, Integer, Double> ph) {
        Double sum = ph.apply(p.getGrade(), days);

    }
    public void processAddition(Person p, Double hours, BiFunction<Double, Double, Double> pa) {
        Double sum = pa.apply(p.getGrade(), hours);

    }
}