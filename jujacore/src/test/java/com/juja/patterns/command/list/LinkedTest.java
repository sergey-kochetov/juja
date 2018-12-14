package com.juja.patterns.command.list;

import com.juja.patterns.command.list.command.linkedlist.*;
import org.junit.Test;

public class LinkedTest extends ListTest {
    LinkedListReceiver receiver = new LinkedListReceiver();
    @Override
    protected Command getAddCommand() {
        return new Add(receiver);
    }

    @Override
    protected Command getToStringCommand() {
        return new ToString(receiver);
    }

    @Override
    protected Command getSizeCommand() {
        return new Size(receiver);
    }

    @Override
    protected Command getIsEmptyCommand() {
        return new IsEmpty(receiver);
    }

    @Override
    protected Command getGetCommand() {
        return new Get(receiver);
    }

    @Override
    protected Command getRemoveCommand() {
        return new Remove(receiver);
    }

    @Test
    public void test_1() {
        test();
    }
}
