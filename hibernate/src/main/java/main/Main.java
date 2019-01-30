package main;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

public class Main {

    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.printConnectInfo();

        try {
            long userId = dbService.addUser("Serg");
            System.out.println("Added user id: " + userId);

            UsersDataSet dataSet = dbService.getUser(userId);
            System.out.println("User data set: " + dataSet);
        } catch (DBException e) {
            e.printStackTrace()
;        }
    }
}
