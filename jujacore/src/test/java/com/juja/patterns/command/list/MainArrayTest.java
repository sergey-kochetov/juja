package com.juja.patterns.command.list;

import com.juja.patterns.command.list.command.array.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainArrayTest extends ListTest {

    ArrayReceiver receiver = new ArrayReceiver();

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