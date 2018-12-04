package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(manager, view);
    }

    @Test
    public void testCanProcessWithParametersStringValid() {
        // when
        boolean canProcess = command.canProcess("connect|sqlsmd|postgres|postgres");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWithParametersStringNovalid() {
        // when
        boolean canProcess = command.canProcess("connect2|sqlsmd|postgres|postgres");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void process() throws SQLException {
        // given
        String request = "connect|sqlsmd|postgres|postgres";

        // when
        command.process(request);

        // then
        Mockito.verify(view).write("Connect Successful");
    }

    @Test
    public void processWithShortCommand() throws SQLException {
        String request = "connect|anyDB|anyUser";
        try {
            command.process(request);

        } catch (IllegalArgumentException e) {
            assertEquals("No valid data '|' actual 4, but was: 3", e.getMessage());
        }
    }

    @Test
    public void processWithNormalWorkFromManager() throws SQLException {
        String com = "connect|anyDB|anyUser|anyPassword";
        String db = "anyDB";
        String user = "anyUser";
        String password = "anyPassword";
        //manager.connect ничего не вернет, если получит именно эти команды
        Mockito.doNothing().when(manager).connect(db,user,password);
        command.process(com);
        //Соль теста, - проверяем, выведет ли вьюха это сообщение при условии того, что
        //command у нас соответствует параметрам по к-ву элементов и тому, сто первый элемент   .equals"connect"?
        Mockito.verify(view).write("Connect Successful");
        //Да все гут), но смысла этого теста нет.
        //по умолчанию мок войда и так ничего не возвращает
    }


    @Test
    public void processWithSQLExceptionFromManager() throws SQLException {
        String com = "connect|anyDB|anyUser|anyPassword";
        String db = "anyDB";
        String user = "anyUser";
        String password = "anyPassword";
        //теперь при вызове метода он по любому выбросит SQLException
        Mockito.doThrow(new SQLException()).when(manager).connect(db, user, password);
        try {
            command.process(com);

        } catch (SQLException ex) {
            assertEquals("java.sql.SQLException", ex.toString());
        }
    }

    @Test
    public void processWithRuntimeExceptionFromManager() throws SQLException {
        String com = "connect|anyDB|anyUser|anyPassword";
        //теперь при вызове метода он по любому выбросит RuntimeException
        Mockito.doThrow(new RuntimeException()).when(manager).connect("", "", "");
        command.process(com);
        //Соль теста, - проверяем, выведет ли вьюха это сообщение при условии того, что
        //command у нас соответствует параметрам по к-ву элементов и тому, сто первый элемент.equals"connect"?
        Mockito.verify(view).write("Connect Successful");
    }

}