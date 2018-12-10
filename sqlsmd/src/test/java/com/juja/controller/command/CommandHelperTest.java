package com.juja.controller.command;

import com.juja.model.DatabaseManager;
import com.juja.view.View;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public abstract class CommandHelperTest {
    @Mock
    DatabaseManager manager = mock(DatabaseManager.class);
    @Mock
    View view = mock(View.class);
    @InjectMocks
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
