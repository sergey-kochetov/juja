package com.juja.controller.command;

import com.juja.controller.UtilsCommand;
import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClearTest {

    private View view = Mockito.mock(View.class);
    private DatabaseManager manager;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void testCanProcessClearWithParametersValidString() {
        //when
        boolean canProcess = command.canProcess("clear|table");

        // then
        assertEquals(true, canProcess);
    }

    @Test
    public void testCanProcessClearWithParametersNovalidString() {
        //when
        boolean canProcess = command.canProcess("novalid|table");

        // then
        assertEquals(false, canProcess);
    }

    @Test
    public void testPrintEmptyTableData() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");
        when(manager.getTableColumns("customer"))
                .thenReturn(list);

        when(manager.getTableData("customer")).thenReturn(Collections.EMPTY_LIST);

        // when
        command.process("clear|customer");

        // then
        shouldPrint("[table 'customer' was successfully cleared]");
    }

    @Test
    public void testPrintClearTableData() throws SQLException {
        // given
        List<String> list = UtilsCommand.getDataList();
        list.add("c_id");
        list.add("c_name");
        list.add("c_password");

        when(manager.getTableColumns("customer"))
                .thenReturn(list);

        Map<String, Object> user1 = UtilsCommand.getDataMap();
        user1.put("c_id", 12);
        user1.put("c_name", "Stiven");
        user1.put("c_password", "*****");

        Map<String, Object> user2 = UtilsCommand.getDataMap();
        user2.put("c_id", 13);
        user2.put("c_name", "Eva");
        user2.put("c_password", "+++++");

        List<Map<String, Object>> data = UtilsCommand.getDataListMap();
        data.add(user1);
        data.add(user2);
        when(manager.getTableData("customer"))
                .thenReturn(data);

        // when
        command.process("clear|customer");

        // then
        shouldPrint("[table 'customer' was successfully cleared]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }


}