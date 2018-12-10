package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.mockito.ArgumentCaptor;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public abstract class CommandHelperTest {
    DatabaseManager manager = mock(DatabaseManager.class);
    View view = mock(View.class);
    Command command;

    private String getActualOutput() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        return captor.getAllValues()
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void shouldPrint(String expected) {
        String actual = getActualOutput();
        assertEquals(expected, actual);
    }

}
