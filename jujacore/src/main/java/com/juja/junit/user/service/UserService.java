package com.juja.junit.user.service;

import com.juja.junit.user.dao.UserDao;
import com.juja.junit.user.model.Address;
import com.juja.junit.user.model.User;

import java.util.List;

public class UserService {

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int createNewUser(User user) {
        if (notNullNotEmpty(user.getName()) && notNullNotEmpty(user.getEmail())) {
            int id = userDao.addUser(user);
            System.out.println("id = " + id);
            sendEmail(user.getEmail());
            sendLetter(user.getAddress());
            return id;
        }
        return -1;
    }

    public User getUser(int id) {
        return userDao.getUserById(id);
    }

    public void update(User user) {
        userDao.updateUser(user);
    }


    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    private boolean notNullNotEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    private void sendEmail(String email) {
        System.out.println("Sending email to : " + email);
    }

    private void sendLetter(Address address) {
        System.out.println("Sending letter to : " + address);
    }
}