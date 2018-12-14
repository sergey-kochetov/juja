package com.juja.patterns.command.list;

import com.juja.patterns.command.classic.ConsoleMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class ListTest {
    private ConsoleMock console = new ConsoleMock();

    protected abstract Command getAddCommand();
    protected abstract Command getToStringCommand();
    protected abstract Command getSizeCommand();
    protected abstract Command getIsEmptyCommand();
    protected abstract Command getGetCommand();
    protected abstract Command getRemoveCommand();

    @Test
    public final void test() {
        //given
        Command add = getAddCommand();
        Command toString = getToStringCommand();
        Command size = getSizeCommand();
        Command isEmpty = getIsEmptyCommand();
        Command get = getGetCommand();
        Command remove = getRemoveCommand();

        Invoker invoker = new Invoker();

        // when
        invoker.setCommand(isEmpty).doit();
        invoker.setCommand(add).doit("one");
        invoker.setCommand(add).doit("two");
        invoker.setCommand(add).doit("three");
        invoker.setCommand(toString).doit();
        invoker.setCommand(size).doit();
        invoker.setCommand(add).doit("four");
        invoker.setCommand(add).doit("five");
        invoker.setCommand(get).doit(1);
        invoker.setCommand(remove).doit(4);
        invoker.setCommand(remove).doit(0);
        invoker.setCommand(size).doit();
        invoker.setCommand(toString).doit();

        assertEquals("IsEmpty[] = Да\n" +
                "Add[one] = 1\n" +
                "Add[two] = 2\n" +
                "Add[three] = 3\n" +
                "ToString[] = [one, two, three]\n" +
                "Size[] = 3\n" +
                "Add[four] = 4\n" +
                "Add[five] = 5\n" +
                "Get[1] = two\n" +
                "Remove[4] = five\n" +
                "Remove[0] = one\n" +
                "Size[] = 3\n" +
                "ToString[] = [two, three, four]\n", console.getOut());
    }
}
