package com.juja.junit.user.dao;

import com.juja.junit.user.model.User;

import java.util.List;

public interface UserDao {

    User getUserById(int id);
    int addUser(User user);
    List<User> getAllUsers();
    void updateUser(User user);
    void removeUser(int id);
}