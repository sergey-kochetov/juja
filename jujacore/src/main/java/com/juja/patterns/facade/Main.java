package com.juja.patterns.facade;

public class Main {
    public static void main(String[] args) {
        // берем json'чик
        // оборачиваем в ScoreReader
        ScoreReader reader = new ScoreReaderWithFacade(
                "{'logs':[" +
                        "{'name':'adam', score:14}," +
                        "{'name':'eva', score:23}," +
                        "{'name':'kain', score:124}," +
                        "{'name':'adam', score:114}," +
                        "{'name':'eva', score:140}," +
                        "{'name':'kain', score:200}," +
                        "{'name':'eva', score:49}," +
                        "{'name':'adam', score:14}," +
                        "]}");
        for (String user : reader.getUsers()) {
            int scope = reader.getTotalScore(user);
            System.out.println(user + ":" + scope);
        }
        System.out.println("TOTAL:" + reader.getTotalScore());

    }
}
