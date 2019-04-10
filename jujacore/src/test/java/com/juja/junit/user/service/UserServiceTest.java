package com.juja.junit.user.service;

import com.juja.junit.user.dao.UserDao;
import com.juja.junit.user.model.Address;
import com.juja.junit.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserDao userDao = mock(UserDao.class);
    UserService userService;

    @Before
    public void setup() {
        userService = new UserService();
        userService.setUserDao(userDao);
    }

    @Test
    public void addUserTest() {
        Address address = new Address("Russia", "Saint-Petersburg", 192029);
        User user = new User(1, "Ivan Ivanov", "Ivanov@gmail.com", 25, address);

        when(userDao.addUser(user)).thenReturn(1);

        int result = userService.createNewUser(user);
        assertEquals(result, 1);
    }

    @Test
    public void getUserTest() {
        Address address = new Address("Russia", "Saint-Petersburg", 192029);
        User user = new User(1, "Ivan Ivanov", "Ivanov@gmail.com", 25, address);

        when(userDao.getUserById(1)).thenReturn(user);

        User result = userService.getUser(1);

        assertEquals(result.getAddress(), address);
        assertEquals(result, user);
        assertEquals("User(id=1, name=Ivan Ivanov, " +
                        "email=Ivanov@gmail.com, age=25, " +
                        "address=Address(country=Russia, " +
                        "city=Saint-Petersburg, zip=192029))",
                user.toString());
    }

    @Test
    public void updateUserTest() {
        Address address = new Address("Russia", "Saint-Petersburg", 192029);
        User user = new User(1, "Ivan Ivanov", "Ivanov@gmail.com", 25, address);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println(invocation.getArguments().toString());
                return null;
            }
        }).when(userDao).updateUser(any(User.class));

        userService.update(user);
    }

    @Test
    public void getAllUsersTest() {
        Address address = new Address("Russia", "Saint-Petersburg", 192029);
        User user = new User(1, "Ivan Ivanov", "Ivanov@gmail.com", 25, address);
        User user2 = new User(2, "Ivan Petrov", "Ivanov@yandex.ru", 15, address);
        User user3 = new User(3, "Ivan Sidorov", "Ivanov@mail.ru", 35, address);
        List<User> list = mock(List.class);

        list.add(user);
        list.add(user2);
        list.add(user3);

        when(userDao.getAllUsers()).thenReturn(list);

        List<User> users = userService.getAllUsers();

        verify(list).add(user);
        verify(list, times(3)).add(any(User.class));

        assertNotEquals(3, users.size());
    }
}